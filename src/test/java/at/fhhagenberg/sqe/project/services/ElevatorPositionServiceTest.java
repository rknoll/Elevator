package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.TestElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.listeners.IElevatorPositionListener;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorPositionServiceTest extends TestCase {

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

    private Elevator elevator;
    private ElevatorPositionService service;
    private CustomElevatorAdapter customElevator;

    public void setUp() {
        elevator = new Elevator(0, "Elevator 0", new ArrayList<Floor>());
        customElevator = new CustomElevatorAdapter(elevator);
        service = new ElevatorPositionService(customElevator, 100);
        service.start();
    }

    public void testNotifications() throws InterruptedException {
        final boolean[] gotCalled = {false};
        Object o = new Object();
        service.addListener(new IElevatorPositionListener() {
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
    }

    public void testCachedNotifications() throws InterruptedException {
        final boolean[] gotCalled = {false, false};
        Object o = new Object();
        service.addListener(new IElevatorPositionListener() {
            @Override
            public Elevator getElevator() {
                return elevator;
            }
            @Override
            public void update() {
                gotCalled[0] = true;
                if (gotCalled[1]) {
                    synchronized(o) {
                        o.notify();
                    }
                }
            }
        });
        service.addListener(new IElevatorPositionListener() {
            @Override
            public Elevator getElevator() {
                return elevator;
            }
            @Override
            public void update() {
                gotCalled[1] = true;
                if (gotCalled[0]) {
                    synchronized(o) {
                        o.notify();
                    }
                }
            }
        });

        synchronized(o) {
            o.wait(1000);
        }

        if (!gotCalled[0] || !gotCalled[1]) {
            fail("Timeout for Notification");
        }

        assertEquals(1, elevator.getPosition());
        assertEquals(1, customElevator.getCallCount());
    }

    public void testStopNotifications() throws Exception {
        final boolean[] gotCalled = {false};
        Object o = new Object();
        IElevatorPositionListener l = new IElevatorPositionListener() {
            @Override
            public Elevator getElevator() {
                return elevator;
            }
            @Override
            public void update() {
                gotCalled[0] = true;
                synchronized(o) {
                    o.notify();
                }
            }
        };
        service.addListener(l);

        synchronized(o) {
            o.wait(1000);
        }

        if (!gotCalled[0]) {
            fail("Timeout for Notification");
        }

        gotCalled[0] = false;
        service.removeListener(l);

        synchronized(o) {
            o.wait(1000);
        }
        if (gotCalled[0]) {
            fail("Got Notification after RemoveListener");
        }
    }
}
