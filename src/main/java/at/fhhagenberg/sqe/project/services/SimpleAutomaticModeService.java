package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Helper Class for the Automatic Mode
 */
public class SimpleAutomaticModeService extends BaseAutomaticModeService {

    private boolean mUpwards;

    public SimpleAutomaticModeService(Building building, Elevator elevator) {
        super(building, elevator);

        mUpwards = true;
    }

    @Override
    protected Floor getNextGoal() {
        int currentFloor = mElevator.getCurrentFloor().getFloorNumber();
        int nextFloor = currentFloor;
        int floorCount = mBuilding.getNumberOfFloors();
        boolean prevDirection = mUpwards;

        do {
            nextFloor += mUpwards ? 1 : -1;
            if (nextFloor == floorCount) {
                mUpwards = false;
                nextFloor = currentFloor - 1;
                if (nextFloor == -1 || prevDirection == mUpwards) return null;
            } else if (nextFloor == -1) {
                mUpwards = true;
                nextFloor = currentFloor + 1;
                if (nextFloor == floorCount || prevDirection == mUpwards) return null;
            }
        } while (!mElevator.getService(mElevator.getFloor(nextFloor)));

        return mElevator.getFloor(nextFloor);
    }

}
