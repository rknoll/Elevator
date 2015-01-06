package at.fhhagenberg.sqe.project.connection.dummy;

import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapterFactory;

/**
 * Created by rknoll on 05/01/15.
 */
public class DummyElevatorFactory implements IElevatorAdapterFactory {
    @Override
    public IElevatorAdapter create() {
        return new DummyElevator();
    }
}
