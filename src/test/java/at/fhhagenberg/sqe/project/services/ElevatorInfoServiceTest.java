package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.TestElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.listeners.IElevatorInfoListener;
import at.fhhagenberg.sqe.project.services.listeners.IFloorStatusListener;
import junit.framework.TestCase;

import java.util.ArrayList;

import static org.easymock.EasyMock.createMock;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorInfoServiceTest extends TestCase {

    private ElevatorInfoService service;
    private Elevator elevator;
    private CustomElevatorAdapter customElevator;

    private class CustomElevatorAdapter extends TestElevatorAdapter {
        private Elevator mElevator;
        private int mCallCount;

        public CustomElevatorAdapter(Elevator elevator) {
            mElevator = elevator;
        }

        @Override
        public int getElevatorPosition(int elevatorNumber) {
            assertEquals(mElevator.getElevatorNumber(), elevatorNumber);
            mCallCount++;
            return 1;
        }

        public int getCallCount() {
            return mCallCount;
        }
    }

    public void setUp() {
        elevator = new Elevator(0, "Elevator 0", new ArrayList<Floor>());
        customElevator = new CustomElevatorAdapter(elevator);
        service = new ElevatorInfoService(customElevator, 100);
        service.start();
    }

    public void testNotifications() throws InterruptedException {
        final boolean[] gotCalled = {false};
        Object o = new Object();
        service.addListener(new IElevatorInfoListener() {
            @Override
            public Elevator getElevator() {
                return elevator;
            }
            @Override
            public void update() {
                assertEquals(1, elevator.getPosition());
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
