package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.listeners.IFloorStatusListener;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rknoll on 16/12/14.
 */
public class FloorStatusService extends ElevatorService<IFloorStatusListener> {

    private IElevatorAdapter mAdapter;
    private Set<Floor> floorCache;

    public FloorStatusService(IElevatorAdapter adapter) {
        this(adapter, 100);
    }

    public FloorStatusService(IElevatorAdapter adapter, int sleepTimeMs) {
        super(IFloorStatusListener.class, sleepTimeMs);
        mAdapter = adapter;
        floorCache = new HashSet<Floor>();
    }

    @Override
    protected void beginUpdate() {
        floorCache.clear();
    }

    @Override
    protected void update(IFloorStatusListener listener) {
        Floor f = listener.getFloor();

        if (floorCache.contains(f)) {
            listener.update();
            return;
        }

        int floorNumber = f.getFloorNumber();

        try {
            f.setButtonDown(mAdapter.getFloorButtonDown(floorNumber));
            f.setButtonUp(mAdapter.getFloorButtonUp(floorNumber));

            listener.update();
        } catch (ElevatorConnectionLostException ignored) {
            return;
        }

        floorCache.add(f);
    }
}
