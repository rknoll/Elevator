package at.fhhagenberg.sqe.project.model;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import at.fhhagenberg.sqe.project.model.Elevator.Direction;
import at.fhhagenberg.sqe.project.model.Elevator.DoorStatus;

/**
 * Created by rknoll on 15/12/14.
 * Modified by Thomas Zoechbauer on 18/12/14
 */
public class ElevatorTest {

	private Elevator elevator;
	private List<Floor> floors;

    @Before
    public void setUp() {
    	floors = new ArrayList<Floor>();
        floors.add(new Floor(0, "Floor 1"));
        floors.add(new Floor(1, "Floor 2"));
        elevator = new Elevator(32, "elevator under test", floors);
    }    

	@Test
	public void testGetFloors() {
		Iterable<Floor> floorList = elevator.getFloors();
		Iterator<Floor> floorIter = floorList.iterator();
		Floor floor1 = floorIter.next();
		Floor floor2 = floorIter.next();
		assertFalse(floorIter.hasNext());
		
		assertTrue(floors.contains(floor1));
		assertTrue(floors.contains(floor2));	
	}

	@Test
	public void testSetAndGetService() {
		Iterator<Floor> floorIter = elevator.getFloors().iterator();
		Floor floor1 = floorIter.next();
		Floor floor2 = floorIter.next();
		
		assertFalse(elevator.getService(floor1));	// Service must be false per default
		assertFalse(elevator.getService(floor2));
		
		elevator.setService(floor1, true);
		assertTrue(elevator.getService(floor1));
		assertFalse(elevator.getService(floor2));
		
		elevator.setService(floor2, true);
		assertTrue(elevator.getService(floor1));
		assertTrue(elevator.getService(floor2));
		
		elevator.setService(floor1, false);
		assertFalse(elevator.getService(floor1));
		assertTrue(elevator.getService(floor2));
		
		elevator.setService(floor2, false);
		assertFalse(elevator.getService(floor1));
		assertFalse(elevator.getService(floor2));		
	}

	@Test
	public void testSetAndGetButton() {
		Iterator<Floor> floorIter = elevator.getFloors().iterator();
		Floor floor1 = floorIter.next();
		Floor floor2 = floorIter.next();
		
		assertFalse(elevator.getButton(floor1));	// Button is not pressed by default
		assertFalse(elevator.getButton(floor2));
		
		elevator.setButton(floor1, true);
		assertTrue(elevator.getButton(floor1));
		assertFalse(elevator.getButton(floor2));
		
		elevator.setButton(floor2, true);
		assertTrue(elevator.getButton(floor1));
		assertTrue(elevator.getButton(floor2));
		
		elevator.setButton(floor1, false);
		assertFalse(elevator.getButton(floor1));
		assertTrue(elevator.getButton(floor2));
		
		elevator.setButton(floor2, false);
		assertFalse(elevator.getButton(floor1));
		assertFalse(elevator.getButton(floor2));		
	}


	@Test
	public void testGetElevatorNumber() {
		assertEquals(32, elevator.getElevatorNumber());
	}

	@Test
	public void testGetDescription() {
		assertEquals("elevator under test", elevator.getDescription());
	}

	@Test
	public void testSetAndGetCurrentFloor() {
		Iterator<Floor> floorIter = elevator.getFloors().iterator();
		Floor floor1 = floorIter.next();
		Floor floor2 = floorIter.next();
		
		elevator.setCurrentFloor(floor2);
		Floor currFloor = elevator.getCurrentFloor();
		assertEquals(floor2, currFloor);
		
		elevator.setCurrentFloor(floor1);
		currFloor = elevator.getCurrentFloor();
		assertEquals(floor1, currFloor);
	}

	@Test
	public void testSetAndGetPosition() {
		elevator.setPosition(0);
		assertEquals(0, elevator.getPosition());
		
		elevator.setPosition(-12);
		assertEquals(-12, elevator.getPosition());
		
		elevator.setPosition(34);
		assertEquals(34, elevator.getPosition());
	}


	@Test
	public void testSetAndGetTarget() {
		Iterator<Floor> floorIter = elevator.getFloors().iterator();
		Floor floor1 = floorIter.next();
		Floor floor2 = floorIter.next();
		
        elevator.setTarget(floor1);
        assertEquals(floor1, elevator.getTarget());
        
        elevator.setTarget(floor2);        
        assertEquals(floor2, elevator.getTarget());
    }

	@Test
	public void testSetAndGetDoorStatus() {
		elevator.setDoorStatus(DoorStatus.CLOSED);
		assertEquals(DoorStatus.CLOSED, elevator.getDoorStatus());
		
		elevator.setDoorStatus(DoorStatus.OPENING);
		assertEquals(DoorStatus.OPENING, elevator.getDoorStatus());
		
		elevator.setDoorStatus(DoorStatus.OPEN);
		assertEquals(DoorStatus.OPEN, elevator.getDoorStatus());
		
		elevator.setDoorStatus(DoorStatus.CLOSING);
		assertEquals(DoorStatus.CLOSING, elevator.getDoorStatus());
	}

	@Test
	public void testSetAndGetSpeed() {
		elevator.setSpeed(3);
		assertEquals(3, elevator.getSpeed());
	}

	@Test
	public void testSetAndGetAcceleration() {
		elevator.setAcceleration(2);
		assertEquals(2, elevator.getAcceleration());
	}	

	@Test
	public void testSetAndGetCapacity() {
		elevator.setCapacity(350);
		assertEquals(350, elevator.getCapacity());
		
		try {
			elevator.setCapacity(-1);
		}
		catch (IllegalArgumentException e) {
			// exception thrown as expected
		}
		
		assertEquals(350, elevator.getCapacity());
	}

	@Test
	public void testSetAndGetWeight() {
		elevator.setWeight(300);
		assertEquals(300, elevator.getWeight());
	}

	@Test
	public void testSetAndGetDirection() {
		elevator.setDirection(Direction.UNCOMMITTED);
		assertEquals(Direction.UNCOMMITTED, elevator.getDirection());
		
		elevator.setDirection(Direction.UP);
		assertEquals(Direction.UP, elevator.getDirection());
		
		elevator.setDirection(Direction.DOWN);
		assertEquals(Direction.DOWN, elevator.getDirection());
	}

	@Test
	public void testSetAndCheckAutomaticMode() {
		elevator.setAutomaticMode(true);
		assertTrue(elevator.isAutomaticMode());
		
		elevator.setAutomaticMode(false);
		assertFalse(elevator.isAutomaticMode());
	}


}