package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Elevator.Direction;
import at.fhhagenberg.sqe.project.model.Floor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

/**
 * Helper Class for the Automatic Mode
 */
public class AutomaticModeService implements IService, PropertyChangeListener {

    private Building mBuilding;
    private Elevator mElevator;
    private Floor mNextGoal;
    private boolean mUpwards;

    public AutomaticModeService(Building building, Elevator elevator) {
        mBuilding = building;
        mElevator = elevator;
        mNextGoal = null;
        mUpwards = true;
        if (mElevator.isAutomaticMode()) setNextGoal();
        mElevator.addPropertyChangeListener(Elevator.PROP_AUTOMATIC_MODE, this);
        mElevator.addPropertyChangeListener(Elevator.PROP_CURRENT_FLOOR, this);
        mElevator.addPropertyChangeListener(Elevator.PROP_SPEED, this);
        mElevator.addPropertyChangeListener(Elevator.PROP_DOOR_STATUS, this);
//        mElevator.addPropertyChangeListener(Elevator.PROP_DIRECTION, this);
        mElevator.addPropertyChangeListener(Elevator.PROP_SERVICE, this);
    }

    @Override
    public void refresh() {
        // TODO: does this have to be a IService?
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Elevator.PROP_AUTOMATIC_MODE:
                if (!mElevator.isAutomaticMode()) break;
                setNextGoal();
                break;
            case Elevator.PROP_CURRENT_FLOOR:
            case Elevator.PROP_SPEED:
            case Elevator.PROP_DOOR_STATUS:
//            case Elevator.PROP_DIRECTION:
            case Elevator.PROP_SERVICE:
                if (!mElevator.isAutomaticMode()) break;
                if (mNextGoal == mElevator.getCurrentFloor()) setNextGoal();
                break;
        }
    }

    private void setNextGoal() {
        if (mElevator.getSpeed() == 0 && mElevator.getDoorStatus() == Elevator.DoorStatus.OPEN) {
            int floor = mElevator.getCurrentFloor().getFloorNumber();
            int nextFloor = floor;

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
                        break;
                    }
                } else if (nextFloor == -1) {
                    mUpwards = true;
                    nextFloor = mElevator.getCurrentFloor().getFloorNumber() + 1;
                    if (nextFloor == mBuilding.getNumberOfFloors()) {
                        mNextGoal = mElevator.getCurrentFloor();
                        break;
                    }
                }
                
//                // set direction
//                if (nextFloor > floor)
//                {
//                	mElevator.setDirection(Direction.UP);
//                }
//                else if (nextFloor < floor)
//                {
//                	mElevator.setDirection(Direction.DOWN);
//                }
//                else
//                {
//                	mElevator.setDirection(Direction.UNCOMMITTED);
//                }
                
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
