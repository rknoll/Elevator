package at.fhhagenberg.sqe.project.model;

import at.fhhagenberg.sqe.project.connection.DummyElevator;
import at.fhhagenberg.sqe.project.services.ElevatorInfoService;
import at.fhhagenberg.sqe.project.services.listeners.IElevatorInfoListener;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorInfoServiceTest extends TestCase {

    ElevatorInfoService service;

    private class CustomAdapter extends DummyElevator {

    }

    public void setUp() {
        service = new ElevatorInfoService(new CustomAdapter());
        service.start();
    }

    public void testNotifications() {
        Elevator elevator = new Elevator(0, "Elevator 0", new ArrayList<Floor>());
        final int[] updateCount = {0};
        service.addListener(new IElevatorInfoListener() {
            @Override
            public Elevator getElevator() {
                return elevator;
            }

            @Override
            public void update() {
                ++updateCount[0];
            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        assertTrue(updateCount[0] > 5);
    }

}
