package at.fhhagenberg.sqe.project.services.automatic.simple;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.model.Elevator.Direction;
import at.fhhagenberg.sqe.project.model.Elevator.DoorStatus;

/**
 * Created by rknoll on 07/01/15.
 * Modified by tzoechbauer on 08/01/15
 */
public class SimpleAutomaticModeServiceTest {
	private SimpleAutomaticModeService mSimpleAutoModeService;
	private Building mBuilding;
    private Elevator mElevator;
    private List<Floor> mFloors;
    
    @Before
	public void setUp()
	{
		mFloors = new ArrayList<Floor>();
    	mFloors.add(new Floor(0, "Ground Floor", "0"));
    	mFloors.add(new Floor(1, "Floor 1", "1"));
    	mFloors.add(new Floor(2, "Floor 2", "2"));
    	
    	mElevator = new Elevator(1, "Elevator 1", mFloors);
    	mElevator.setCurrentFloor(mFloors.get(0));
    	mElevator.setDirection(Direction.UNCOMMITTED);
    	mElevator.setSpeed(0);
    	mElevator.setDoorStatus(DoorStatus.OPEN);
		
    	mBuilding = new Building();
    	mBuilding.setNumberOfFloorsAndElevators(3, 1);
    	
    	mSimpleAutoModeService = new SimpleAutomaticModeService(mBuilding, mElevator);
	}
 
    
    @Test 
	public void testServiceAllFloors()
	{		
		mElevator.setService(mFloors.get(0), true);
		mElevator.setService(mFloors.get(1), true);
		mElevator.setService(mFloors.get(2), true);
		
		mElevator.setAutomaticMode(true);		
		assertEquals(null, mElevator.getTarget());
		
		mSimpleAutoModeService.refresh();
		assertEquals(mFloors.get(1), mElevator.getTarget());
		mElevator.setCurrentFloor(mFloors.get(1));
		
		mSimpleAutoModeService.refresh();
		assertEquals(mFloors.get(2), mElevator.getTarget());
		mElevator.setCurrentFloor(mFloors.get(2));
		
		mSimpleAutoModeService.refresh();
		assertEquals(mFloors.get(1), mElevator.getTarget());
		mElevator.setCurrentFloor(mFloors.get(1));
		
		mSimpleAutoModeService.refresh();
		assertEquals(mFloors.get(0), mElevator.getTarget());
		mElevator.setCurrentFloor(mFloors.get(0));
		
		mSimpleAutoModeService.refresh();
		assertEquals(mFloors.get(1), mElevator.getTarget());
		mElevator.setCurrentFloor(mFloors.get(1));		
	}
    
    @Test 
	public void testServiceFloors0and2()
	{		
		mElevator.setService(mFloors.get(0), true);
		mElevator.setService(mFloors.get(1), false);
		mElevator.setService(mFloors.get(2), true);
		
		mElevator.setAutomaticMode(true);		
		assertEquals(null, mElevator.getTarget());
		
		mElevator.setDirection(Direction.UP);
		
		mSimpleAutoModeService.refresh();
		assertEquals(mFloors.get(2), mElevator.getTarget());
		mElevator.setCurrentFloor(mElevator.getTarget());
		
		mElevator.setDirection(Direction.DOWN);
		
		mSimpleAutoModeService.refresh();
		assertEquals(mFloors.get(0), mElevator.getTarget());
		mElevator.setCurrentFloor(mFloors.get(0));
	}
    
    @Test 
	public void testServiceFloor2()
	{		
		mElevator.setService(mFloors.get(0), false);
		mElevator.setService(mFloors.get(1), false);
		mElevator.setService(mFloors.get(2), true);
		
		mElevator.setAutomaticMode(true);		
		assertEquals(null, mElevator.getTarget());
		
		mElevator.setDirection(Direction.UP);
		
		mSimpleAutoModeService.refresh();
		assertEquals(mFloors.get(2), mElevator.getTarget());
		mElevator.setCurrentFloor(mElevator.getTarget());
		
		mElevator.setDirection(Direction.DOWN);
		
		mSimpleAutoModeService.refresh();
		assertEquals(mFloors.get(2), mElevator.getTarget());
		mElevator.setCurrentFloor(mElevator.getTarget());
	}

	@Test
	public void testServiceFloor3()
	{
		mElevator.setService(mFloors.get(0), true);
		mElevator.setService(mFloors.get(1), false);
		mElevator.setService(mFloors.get(2), false);

		mElevator.setAutomaticMode(true);
		assertEquals(null, mElevator.getTarget());

		mElevator.setDirection(Direction.UP);

		mSimpleAutoModeService.refresh();
		assertEquals(null, mElevator.getTarget());
		mElevator.setCurrentFloor(mElevator.getTarget());

		mElevator.setDirection(Direction.DOWN);

		mSimpleAutoModeService.refresh();
		assertEquals(null, mElevator.getTarget());
		mElevator.setCurrentFloor(mElevator.getTarget());
	}

	@Test
	public void testServiceNoFloors()
	{		
		mElevator.setService(mFloors.get(0), false);
		mElevator.setService(mFloors.get(1), false);
		mElevator.setService(mFloors.get(2), false);
		
		mElevator.setAutomaticMode(true);		
		assertEquals(null, mElevator.getTarget());
		
		mSimpleAutoModeService.refresh();
		assertEquals(null, mElevator.getTarget());
		mElevator.setCurrentFloor(mFloors.get(0));
		
		mSimpleAutoModeService.refresh();
		assertEquals(null, mElevator.getTarget());
		mElevator.setCurrentFloor(mFloors.get(0));	
	}
}
