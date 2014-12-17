package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.listeners.IElevatorPositionListener;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorPositionService extends ElevatorService<IElevatorPositionListener> {

    private IElevatorAdapter mAdapter;
    private Set<Elevator> elevatorCache;

    public ElevatorPositionService(IElevatorAdapter adapter) {
        super(IElevatorPositionListener.class);
        mAdapter = adapter;
        elevatorCache = new HashSet<Elevator>();
    }

    @Override
    protected void beginUpdate() {
        elevatorCache.clear();
    }

    @Override
    protected void update(IElevatorPositionListener listener) {
        Elevator e = listener.getElevator();

        if (elevatorCache.contains(e)) {
            listener.update();
            return;
        }

        int elevatorNumber = e.getElevatorNumber();

        try {
            e.setPosition(mAdapter.getElevatorPosition(elevatorNumber));

            listener.update();
        } catch (ElevatorConnectionLostException ignored) {
            return;
        }

        elevatorCache.add(e);
    }
}
