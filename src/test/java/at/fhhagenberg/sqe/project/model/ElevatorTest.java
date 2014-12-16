package at.fhhagenberg.sqe.project.model;

import junit.framework.TestCase;

import java.util.*;

/**
 * Created by rknoll on 15/12/14.
 */
public class ElevatorTest extends TestCase {

    private Elevator elevator;
    private List<Floor> floors;

    public void setUp() {
        floors = new ArrayList<Floor>();
        floors.add(new Floor(0, "Floor 1"));
        floors.add(new Floor(1, "Floor 2"));
        elevator = new Elevator(0, "elevator", floors);
    }

    public void testSetTarget() {
        elevator.setTarget(floors.get(0));
        assertEquals(floors.get(0), elevator.getTarget());
    }
}
