package at.fhhagenberg.sqe.project.connection.rmi;

import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapterFactory;
import sqelevator.IElevator;

import java.rmi.Naming;

/**
 * Created by rknoll on 05/01/15.
 */
public class RMIElevatorFactory implements IElevatorAdapterFactory {
    @Override
    public IElevatorAdapter create() {
        try {
            IElevator rmi = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
            return new RMIElevator(rmi);
        } catch (Exception e) {
            return null;
        }
    }
}
