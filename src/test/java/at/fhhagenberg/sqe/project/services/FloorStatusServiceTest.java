package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.DummyElevator;
import at.fhhagenberg.sqe.project.connection.TestElevatorAdapter;
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

    private FloorStatusService service;
    private Floor floor;
    private CustomElevatorAdapter customElevator;

    private class CustomElevatorAdapter extends TestElevatorAdapter {
        private Floor mFloor;
        private int mCallCount;

        public CustomElevatorAdapter(Floor floor) {
            mFloor = floor;
        }

        @Override
        public boolean getFloorButtonUp(int floorNumber) {
            assertEquals(mFloor.getFloorNumber(), floorNumber);
            mCallCount++;
            return true;
        }

        public int getCallCount() {
            return mCallCount;
        }
    }

    public void setUp() {
        floor = new Floor(0, "Floor 0");
        customElevator = new CustomElevatorAdapter(floor);
        service = new FloorStatusService(customElevator, 100);
        service.start();
    }

    public void testNotifications() throws InterruptedException {
        final boolean[] gotCalled = {false};
        Object o = new Object();
        service.addListener(new IFloorStatusListener() {
            @Override
            public Floor getFloor() {
                return floor;
            }
            @Override
            public void update() {
                assertTrue(floor.isButtonUp());
                gotCalled[0] = true;
                synchronized(o) {
                    o.notify();
                }
            }
        });

        synchronized(o) {
            o.wait(1000);
        }

        if (!gotCalled[0]) {
            fail("Timeout for Notification");
        }

        assertEquals(1, customElevator.getCallCount());
    }

}
