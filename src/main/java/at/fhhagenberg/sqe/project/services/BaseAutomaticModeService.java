package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Base Class for Automatic Services
 */
public abstract class BaseAutomaticModeService implements IService, PropertyChangeListener {

    protected final Building mBuilding;
    protected final Elevator mElevator;

    private boolean mRegistered;

    public BaseAutomaticModeService(Building building, Elevator elevator) {
        mBuilding = building;
        mElevator = elevator;

        mElevator.addPropertyChangeListener(Elevator.PROP_AUTOMATIC_MODE, this);

        checkRegistries();

        setNextGoal();
    }

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

    protected abstract Floor getNextGoal();

    private void setNextGoal() {
        if (!mElevator.isAutomaticMode()) return;
        if (mElevator.getCurrentFloor() == null) return;
        if (mElevator.getSpeed() != 0) return;
        if (mElevator.getDoorStatus() != Elevator.DoorStatus.OPEN) return;

        Floor nextGoal = getNextGoal();
        if (nextGoal == null || nextGoal == mElevator.getCurrentFloor()) {
            mElevator.setDirection(Elevator.Direction.UNCOMMITTED);
        } else {
            if (nextGoal.getFloorNumber() > mElevator.getCurrentFloor().getFloorNumber()) {
                mElevator.setDirection(Elevator.Direction.UP);
            } else {
                mElevator.setDirection(Elevator.Direction.DOWN);
            }
        }
        if (nextGoal != null) mElevator.setTarget(nextGoal);
    }
}
