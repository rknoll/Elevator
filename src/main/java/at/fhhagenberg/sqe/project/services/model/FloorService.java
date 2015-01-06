package at.fhhagenberg.sqe.project.services.model;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.IService;

/**
 * Helper Class to Refresh a Floors Data
 */
public class FloorService implements IService {

    private final IElevatorAdapter mAdapter;
    private final Floor mFloor;
    private final int mFloorNumber;

    public FloorService(IElevatorAdapter adapter, Floor floor) {
        mAdapter = adapter;
        mFloor = floor;
        mFloorNumber = mFloor.getFloorNumber();
    }

    @Override
    public void refresh() throws ElevatorConnectionLostException {
        if (mFloor.getPropertyChangeListenersCount(Floor.PROP_BUTTON_UP) > 0) {
            mFloor.setButtonUp(mAdapter.getFloorButtonUp(mFloorNumber));
        }
        if (mFloor.getPropertyChangeListenersCount(Floor.PROP_BUTTON_DOWN) > 0) {
            mFloor.setButtonDown(mAdapter.getFloorButtonDown(mFloorNumber));
        }
    }
}