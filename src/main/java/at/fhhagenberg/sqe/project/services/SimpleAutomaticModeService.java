package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Helper Class for the Automatic Mode
 */
public class SimpleAutomaticModeService implements IService, PropertyChangeListener {

    private Building mBuilding;
    private Elevator mElevator;
    private Floor mNextGoal;
    private boolean mUpwards;
    private boolean mRegistered;

    public SimpleAutomaticModeService(Building building, Elevator elevator) {
        mBuilding = building;
        mElevator = elevator;
        mNextGoal = null;
        mUpwards = true;

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
            mRegistered = true;
        } else if (!mElevator.isAutomaticMode() && mRegistered) {
            mElevator.removePropertyChangeListener(Elevator.PROP_CURRENT_FLOOR, this);
            mElevator.removePropertyChangeListener(Elevator.PROP_SPEED, this);
            mElevator.removePropertyChangeListener(Elevator.PROP_DOOR_STATUS, this);
            mElevator.removePropertyChangeListener(Elevator.PROP_SERVICE, this);
            mRegistered = false;
        }
    }

    @Override
    public void refresh() {
        // TODO: does this have to be a IService?
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        checkRegistries();
        setNextGoal();
    }

    private void setNextGoal() {
        if (!mElevator.isAutomaticMode()) return;
        if (mElevator.getSpeed() == 0 && mElevator.getDoorStatus() == Elevator.DoorStatus.OPEN) {
            int nextFloor = mElevator.getCurrentFloor().getFloorNumber();

            do {
                if (mUpwards) {
                    ++nextFloor;
                } else {
                    --nextFloor;
                }
                if (nextFloor == mBuilding.getNumberOfFloors()) {
                    mUpwards = false;
                    nextFloor = mElevator.getCurrentFloor().getFloorNumber() - 1;
                    if (nextFloor == -1) {
                        mNextGoal = mElevator.getCurrentFloor();
                        mElevator.setDirection(Elevator.Direction.UNCOMMITTED);
                        break;
                    }
                } else if (nextFloor == -1) {
                    mUpwards = true;
                    nextFloor = mElevator.getCurrentFloor().getFloorNumber() + 1;
                    if (nextFloor == mBuilding.getNumberOfFloors()) {
                        mNextGoal = mElevator.getCurrentFloor();
                        mElevator.setDirection(Elevator.Direction.UNCOMMITTED);
                        break;
                    }
                }
                mElevator.setDirection(mUpwards ? Elevator.Direction.UP : Elevator.Direction.DOWN);

                // set next goal
                setNextGoal(nextFloor);
            } while (!mElevator.getService(mNextGoal));
            mElevator.setTarget(mNextGoal);
        }
    }

    private void setNextGoal(int floorNumber) {
        for (Floor floor : mElevator.getFloors()) {
            if (floor.getFloorNumber() == floorNumber) {
                mNextGoal = floor;
                break;
            }
        }
    }
}
