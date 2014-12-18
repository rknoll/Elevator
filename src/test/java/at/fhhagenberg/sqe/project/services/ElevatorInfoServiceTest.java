package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.TestElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.listeners.IElevatorInfoListener;
import junit.framework.TestCase;

import java.util.ArrayList;

import static org.easymock.EasyMock.createMock;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorInfoServiceTest extends TestCase {

    ElevatorInfoService service;

    public void setUp() {
        service = new ElevatorInfoService(new TestElevatorAdapter(), 100);
        service.start();
    }

    public void testNotifications() {
        Elevator elevator = new Elevator(0, "Elevator 0", new ArrayList<Floor>());
        Thread t = Thread.currentThread();
        service.addListener(new IElevatorInfoListener() {
            @Override
            public Elevator getElevator() {
                return elevator;
            }
            @Override
            public void update() {
                t.interrupt();
            }
        });
        try {
            Thread.sleep(1000);
            fail("Timeout for Notification");
        } catch (InterruptedException ignored) {
        }
    }

}
