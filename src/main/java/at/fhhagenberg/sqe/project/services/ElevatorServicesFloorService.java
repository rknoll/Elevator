package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.listeners.IElevatorInfoListener;
import at.fhhagenberg.sqe.project.services.listeners.IElevatorPositionListener;
import at.fhhagenberg.sqe.project.services.listeners.IElevatorServicesFloorListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorServicesFloorService extends ElevatorService<IElevatorServicesFloorListener> {

    private IElevatorAdapter mAdapter;
    private Map<Elevator, Set<Floor>> elevatorFloorCache;

    public ElevatorServicesFloorService(IElevatorAdapter adapter) {
        this(adapter, 100);
    }

    public ElevatorServicesFloorService(IElevatorAdapter adapter, int sleepTimeMs) {
        super(IElevatorServicesFloorListener.class, sleepTimeMs);
        mAdapter = adapter;
        elevatorFloorCache = new HashMap<Elevator, Set<Floor>>();
    }

    @Override
    protected void beginUpdate() {
        elevatorFloorCache.clear();
    }

    @Override
    protected void update(IElevatorServicesFloorListener listener) {
        Elevator e = listener.getElevator();
        Floor f = listener.getFloor();

        if (elevatorFloorCache.containsKey(e) && elevatorFloorCache.get(e).contains(f)) {
            listener.update();
            return;
        }

        int elevatorNumber = e.getElevatorNumber();
        int floorNumber = f.getFloorNumber();

        try {
            e.setService(f, mAdapter.getServicesFloors(elevatorNumber, floorNumber));

            listener.update();
        } catch (ElevatorConnectionLostException ignored) {
            return;
        }

        if (!elevatorFloorCache.containsKey(e)) {
            elevatorFloorCache.put(e, new HashSet<Floor>());
        }
        elevatorFloorCache.get(e).add(f);
    }
}
