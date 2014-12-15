package at.fhhagenberg.sqe.project.model;

import junit.framework.TestCase;

import java.rmi.RemoteException;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

/**
 * Created by rknoll on 15/12/14.
 */
public class ElevatorTest extends TestCase {

    private Elevator elevator;
    IElevator mockConn;

    public void setUp() {
        mockConn = createMock(IElevator.class);
        elevator = new Elevator(mockConn, 0);
    }

    public void testSetTarget() throws RemoteException {
        mockConn.setTarget(0, 1);
        replay(mockConn);

        elevator.setTarget(1);

        verify(mockConn);
    }
}
