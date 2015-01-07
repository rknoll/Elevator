package at.fhhagenberg.sqe.project.services.model;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.IService;

/**
 * Service to Refresh a Floors Data
 */
public class FloorService implements IService {

    /**
     * The Elevator Adapter to retrieve Values
     */
    private final IElevatorAdapter mAdapter;
    /**
     * The Floor
     */
    private final Floor mFloor;
    /**
     * The cached Floor Number
     */
    private final int mFloorNumber;

    /**
     * Create a new FloorService.
     *
     * @param adapter The ElevatorAdapter Connection
     * @param floor   The Floor
     */
    public FloorService(IElevatorAdapter adapter, Floor floor) {
        mAdapter = adapter;
        mFloor = floor;

        // cache the Floor number as it will never change
        mFloorNumber = mFloor.getFloorNumber();
    }

    @Override
    public void refresh() throws ElevatorConnectionLostException {
        // only update Values if there are some Listeners besides ourselves

        if (mFloor.getPropertyChangeListenersCount(Floor.PROP_BUTTON_UP) > 0) {
            mFloor.setButtonUp(mAdapter.getFloorButtonUp(mFloorNumber));
        }
        if (mFloor.getPropertyChangeListenersCount(Floor.PROP_BUTTON_DOWN) > 0) {
            mFloor.setButtonDown(mAdapter.getFloorButtonDown(mFloorNumber));
        }
    }
}
