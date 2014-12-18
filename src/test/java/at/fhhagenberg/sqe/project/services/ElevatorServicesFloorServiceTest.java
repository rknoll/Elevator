package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.TestElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.listeners.IElevatorInfoListener;
import at.fhhagenberg.sqe.project.services.listeners.IElevatorServicesFloorListener;
import at.fhhagenberg.sqe.project.services.listeners.IFloorStatusListener;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.createMock;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorServicesFloorServiceTest extends TestCase {

    private ElevatorServicesFloorService service;
    private Elevator elevator;
    private Floor floor;
    private CustomElevatorAdapter customElevator;

    private class CustomElevatorAdapter extends TestElevatorAdapter {
        private Elevator mElevator;
        private Floor mFloor;
        private int mCallCount;

        public CustomElevatorAdapter(Elevator elevator, Floor floor) {
            mElevator = elevator;
            mFloor = floor;
        }

        @Override
        public boolean getServicesFloors(int elevatorNumber, int floorNumber) {
            assertEquals(mElevator.getElevatorNumber(), elevatorNumber);
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
        List<Floor> list = new ArrayList<Floor>();
        list.add(floor);
        elevator = new Elevator(0, "Elevator 0", list);
        customElevator = new CustomElevatorAdapter(elevator, floor);
        service = new ElevatorServicesFloorService(customElevator, 100);
        service.start();
    }

    public void testNotifications() throws InterruptedException {
        final boolean[] gotCalled = {false};
        Object o = new Object();
        service.addListener(new IElevatorServicesFloorListener() {
            @Override
            public Elevator getElevator() {
                return elevator;
            }
            @Override
            public Floor getFloor() {
                return floor;
            }
            @Override
            public void update() {
                assertTrue(elevator.getService(floor));
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
