package at.fhhagenberg.sqe.project.services.automatic;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.IService;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Base Class for Automatic Services
 */
public abstract class BaseAutomaticModeService implements IService, PropertyChangeListener {

    /**
     * The Building
     */
    protected final Building mBuilding;
    /**
     * The Elevator
     */
    protected final Elevator mElevator;
    /**
     * Flag if we are registered as Listener in the Elevator
     */
    private boolean mRegistered;

    /**
     * Create a new Base Automatic Mode for an Elevator in a Building.
     *
     * @param building The Building
     * @param elevator The Elevator
     */
    public BaseAutomaticModeService(Building building, Elevator elevator) {
        mBuilding = building;
        mElevator = elevator;

        // always listen to Automatic Mode changed
        mElevator.addPropertyChangeListener(Elevator.PROP_AUTOMATIC_MODE, this);

        checkRegistries();

        setNextGoal();
    }

    /**
     * If the Elevator is in Automatic mode, register all needed Listeners.
     * If it changes back to Manual mode, remove them.
     */
    private void checkRegistries() {
        if (mElevator.isAutomaticMode() && !mRegistered) {
            mElevator.addPropertyChangeListener(Elevator.PROP_CURRENT_FLOOR, this);
            mElevator.addPropertyChangeListener(Elevator.PROP_SPEED, this);
            mElevator.addPropertyChangeListener(Elevator.PROP_DOOR_STATUS, this);
            mElevator.addPropertyChangeListener(Elevator.PROP_SERVICE, this);
            mElevator.addPropertyChangeListener(Elevator.PROP_BUTTON, this);
            for (Floor f : mElevator.getFloors()) {
                f.addPropertyChangeListener(this);
            }
            mRegistered = true;
        } else if (!mElevator.isAutomaticMode() && mRegistered) {
            mElevator.removePropertyChangeListener(Elevator.PROP_CURRENT_FLOOR, this);
            mElevator.removePropertyChangeListener(Elevator.PROP_SPEED, this);
            mElevator.removePropertyChangeListener(Elevator.PROP_DOOR_STATUS, this);
            mElevator.removePropertyChangeListener(Elevator.PROP_SERVICE, this);
            mElevator.removePropertyChangeListener(Elevator.PROP_BUTTON, this);
            for (Floor f : mElevator.getFloors()) {
                f.removePropertyChangeListener(this);
            }
            mRegistered = false;
        }
    }

    @Override
    public final void refresh() {
        setNextGoal();
    }

    @Override
    public final void propertyChange(PropertyChangeEvent evt) {
        checkRegistries();
    }

    /**
     * Get the next Goal.
     *
     * @return The next Target Floor, or null of no new Goal should be set
     */
    protected abstract Floor getNextGoal();

    /**
     * Set the next Goal and the Committed Direction, based on the new Goal.
     */
    private void setNextGoal() {
        if (!mElevator.isAutomaticMode()) return;
        if (mElevator.getCurrentFloor() == null) return;

        // only set the new Goal if the Elevator is stopped and the Doors are open
        if (mElevator.getSpeed() != 0) return;
        if (mElevator.getDoorStatus() != Elevator.DoorStatus.OPEN) return;

        Floor nextGoal = getNextGoal();
        if (nextGoal == null || nextGoal == mElevator.getCurrentFloor()) {
            // stay in the current Floor
            mElevator.setDirection(Elevator.Direction.UNCOMMITTED);
        } else {
            // set the Committed Direction, based on the new Goal
            if (nextGoal.getFloorNumber() > mElevator.getCurrentFloor().getFloorNumber()) {
                mElevator.setDirection(Elevator.Direction.UP);
            } else {
                mElevator.setDirection(Elevator.Direction.DOWN);
            }
        }
        // Set the new Target Floor
        if (nextGoal != null) mElevator.setTarget(nextGoal);
    }
}
