package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.listeners.IElevatorInfoListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorInfoService extends ElevatorService<IElevatorInfoListener> {

    private IElevatorAdapter mAdapter;
    private Set<Elevator> elevatorCache;

    public ElevatorInfoService(IElevatorAdapter adapter) {
        this(adapter, 100);
    }

    public ElevatorInfoService(IElevatorAdapter adapter, int sleepTimeMs) {
        super(IElevatorInfoListener.class, sleepTimeMs);
        mAdapter = adapter;
        elevatorCache = new HashSet<Elevator>();
    }

    @Override
    protected void beginUpdate() {
        elevatorCache.clear();
    }

    @Override
    protected void update(IElevatorInfoListener listener) {
        Elevator e = listener.getElevator();

        if (elevatorCache.contains(e)) {
            listener.update();
            return;
        }

        int elevatorNumber = e.getElevatorNumber();
        HashMap<Integer, Floor> floors = new HashMap<Integer, Floor>();

        try {
            for (Floor f : e.getFloors()) {
                int floorNumber = f.getFloorNumber();
                e.setButton(f, mAdapter.getElevatorButton(elevatorNumber, floorNumber));
                e.setService(f, mAdapter.getServicesFloors(elevatorNumber, floorNumber));
                floors.put(floorNumber, f);
            }

            e.setCurrentFloor(floors.get(mAdapter.getElevatorFloor(elevatorNumber)));
            e.setAcceleration(mAdapter.getElevatorAccel(elevatorNumber));
            e.setCapacity(mAdapter.getElevatorCapacity(elevatorNumber));
            e.setDirection(mAdapter.getCommittedDirection(elevatorNumber));
            e.setDoorStatus(mAdapter.getElevatorDoorStatus(elevatorNumber));
            e.setPosition(mAdapter.getElevatorPosition(elevatorNumber));
            e.setSpeed(mAdapter.getElevatorSpeed(elevatorNumber));
            e.setWeight(mAdapter.getElevatorWeight(elevatorNumber));

            listener.update();
        } catch (ElevatorConnectionLostException ignored) {
            return;
        }

        elevatorCache.add(e);
    }
}
