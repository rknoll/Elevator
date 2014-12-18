package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.DummyElevator;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.ElevatorInfoService;
import at.fhhagenberg.sqe.project.services.listeners.IElevatorInfoListener;
import at.fhhagenberg.sqe.project.services.listeners.IElevatorPositionListener;
import at.fhhagenberg.sqe.project.services.listeners.IFloorStatusListener;
import junit.framework.TestCase;

import java.util.ArrayList;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createMockBuilder;

/**
 * Created by rknoll on 16/12/14.
 */
public class FloorStatusServiceTest extends TestCase {

    FloorStatusService service;

    private class CustomAdapter extends DummyElevator {

    }

    public void setUp() {
        service = new FloorStatusService(new CustomAdapter(), 100);
        service.start();
    }

    public void testNotifications() {
        Floor floor = new Floor(0, "Floor 0");
        Thread t = Thread.currentThread();
        service.addListener(new IFloorStatusListener() {
            @Override
            public Floor getFloor() {
                return floor;
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
