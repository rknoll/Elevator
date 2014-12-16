package at.fhhagenberg.sqe.project.model;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.services.ElevatorInfoService;
import at.fhhagenberg.sqe.project.services.IElevatorInfoListener;
import at.fhhagenberg.sqe.project.services.IElevatorListener;
import at.fhhagenberg.sqe.project.services.IElevatorPositionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rknoll on 16/12/14.
 */
public class Building {

    private IElevatorAdapter mAdapter;

    private ElevatorInfoService mElevatorInfoService;

    private List<Elevator> mElevators;
    private List<Floor> mFloors;

    public Building(IElevatorAdapter adapter) {
        mAdapter = adapter;

        mElevators = new ArrayList<Elevator>();
        mFloors = new ArrayList<Floor>();

        // TODO: do this in a new Thread
        try {
            int elevatorCount = mAdapter.getElevatorNum();
            int floorCount = mAdapter.getFloorNum();

            for (int i = 0; i < floorCount; ++i) {
                mFloors.add(new Floor(i, "Flooor " + (i + 1)));
            }

            for (int i = 0; i < elevatorCount; ++i) {
                mElevators.add(new Elevator(i, "Elevator " + (i + 1), mFloors));
            }

        } catch (ElevatorConnectionLostException ignored) {
            // TODO: error handling :D
        }

        mElevatorInfoService = new ElevatorInfoService(mAdapter);
        mElevatorInfoService.start();
    }

    public Iterable<Elevator> getElevators() {
        return mElevators;
    }

    public Iterable<Floor> getFloors() {
        return mFloors;
    }

    public void addListener(IElevatorInfoListener listener) {
        mElevatorInfoService.addListener(listener);
    }

    public void addListener(IElevatorPositionListener listener) {

    }

}
