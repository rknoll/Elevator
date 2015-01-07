package at.fhhagenberg.sqe.project.connection.rmi;

import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import org.junit.Before;
import org.junit.Test;
import sqelevator.IElevator;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by rknoll on 07/01/15.
 */
public class RMIElevatorFactoryTest {
    private RMIElevatorFactory factory;
    private IElevator mock;

    @Before
    public void setUp() throws Exception {
        int port;
        Registry registry = null;
        mock = createMock(IElevator.class);
        for (port = 1024; port < 2000; ++port) {
            try {
                registry = LocateRegistry.createRegistry(port);
                break;
            } catch (RemoteException ignored) {
            }
        }
        if (registry == null) throw new Exception("Could not find any free port");
        registry.bind("ElevatorSim", mock);
        factory = new RMIElevatorFactory("rmi://localhost:" + port + "/ElevatorSim");
    }

    @Test
    public void testWorkingConnection() {
        IElevatorAdapter adapter = factory.create();
        assertTrue(adapter instanceof RMIElevator);
    }

    @Test
    public void testNoConnection() {
        factory = new RMIElevatorFactory("");
        IElevatorAdapter adapter = factory.create();
        assertNull(adapter);
    }
}
