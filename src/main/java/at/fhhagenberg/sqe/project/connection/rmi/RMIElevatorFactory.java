package at.fhhagenberg.sqe.project.connection.rmi;

import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapterFactory;
import sqelevator.IElevator;

import java.rmi.Naming;

/**
 * A Factory to Create RMIElevator Adapters.
 */
public class RMIElevatorFactory implements IElevatorAdapterFactory {

    /**
     * The remote host url to connect to
     */
    private String mHostUrl;

    /**
     * Create a new RMIElevatorFactory that connects to the given Host URL.
     *
     * @param hostUrl The Host URL
     */
    public RMIElevatorFactory(String hostUrl) {
        mHostUrl = hostUrl;
    }

    @Override
    public IElevatorAdapter create() {
        try {
            // try to connect to the Server via RMI
            IElevator rmi = (IElevator) Naming.lookup(mHostUrl);
            return new RMIElevator(rmi);
        } catch (Exception ignored) {
            // connection failed
            return null;
        }
    }
}
