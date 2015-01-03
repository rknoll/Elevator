package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Floor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

/**
 * Helper Class to Refresh a Floors Data
 */
public class FloorService implements IService {

    private IElevatorAdapter mAdapter;
    private Floor mFloor;

    public FloorService(IElevatorAdapter adapter, Floor floor) {
        mAdapter = adapter;
        mFloor = floor;
    }

    @Override
    public void refresh() throws ElevatorConnectionLostException{
        if (mFloor.getPropertyChangeListenersCount(Floor.PROP_BUTTON_UP) > 0) {
            mFloor.setButtonUp(mAdapter.getFloorButtonUp(mFloor.getFloorNumber()));
        }
        if (mFloor.getPropertyChangeListenersCount(Floor.PROP_BUTTON_DOWN) > 0) {
            mFloor.setButtonDown(mAdapter.getFloorButtonDown(mFloor.getFloorNumber()));
        }
    }
}
