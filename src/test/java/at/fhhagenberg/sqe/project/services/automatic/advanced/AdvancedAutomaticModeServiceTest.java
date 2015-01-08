package at.fhhagenberg.sqe.project.services.automatic.advanced;

import static org.junit.Assert.*;

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
public class AdvancedAutomaticModeServiceTest 
{
	private AdvancedAutomaticModeService mAdvAutoModeService;
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
    	
		mAdvAutoModeService = new AdvancedAutomaticModeService(mBuilding, mElevator);
	}
   
	@Test
	public void testServiceFloorFalse()
	{
		mElevator.setAutomaticMode(true);
		
		mElevator.setService(mFloors.get(1), false);
		mFloors.get(1).setButtonDown(true);
		mAdvAutoModeService.refresh();
		assertEquals(mFloors.get(0), mElevator.getTarget());
	}
	
	@Test
	public void testServiceFloorTrue()
	{
		mElevator.setAutomaticMode(true);
		
		mElevator.setService(mFloors.get(1), true);
		mFloors.get(1).setButtonDown(true);
		mAdvAutoModeService.refresh();
		assertEquals(mFloors.get(1), mElevator.getTarget());
	}
	
	@Test 
	public void testServiceAllFloorsAllButtonDown()
	{
		mFloors.get(0).setButtonDown(true);
		mFloors.get(1).setButtonDown(true);
		mFloors.get(2).setButtonDown(true);
		
		mElevator.setService(mFloors.get(0), true);
		mElevator.setService(mFloors.get(1), true);
		mElevator.setService(mFloors.get(2), true);
		
		mElevator.setAutomaticMode(true);		
		assertEquals(null, mElevator.getTarget());
		
		mAdvAutoModeService.refresh();
		assertEquals(mFloors.get(0), mElevator.getTarget());
		mFloors.get(0).setButtonDown(false);
		
		mAdvAutoModeService.refresh();
		assertEquals(mFloors.get(1), mElevator.getTarget());
		mFloors.get(1).setButtonDown(false);
		
		mAdvAutoModeService.refresh();
		assertEquals(mFloors.get(0), mElevator.getTarget());
		
		mAdvAutoModeService.refresh();
		assertEquals(mFloors.get(2), mElevator.getTarget());	
		mFloors.get(2).setButtonDown(false);
		
		mAdvAutoModeService.refresh();
		assertEquals(mFloors.get(0), mElevator.getTarget());
	}
	
	@Test 
	public void testServiceAllFloorsAllButtonUp()
	{
		mFloors.get(0).setButtonUp(true);
		mFloors.get(1).setButtonUp(true);
		mFloors.get(2).setButtonUp(true);
		
		mElevator.setService(mFloors.get(0), true);
		mElevator.setService(mFloors.get(1), true);
		mElevator.setService(mFloors.get(2), true);
		
		mElevator.setAutomaticMode(true);		
		assertEquals(null, mElevator.getTarget());
		
		mAdvAutoModeService.refresh();
		assertEquals(mFloors.get(0), mElevator.getTarget());
		mFloors.get(0).setButtonUp(false);
		
		mAdvAutoModeService.refresh();
		assertEquals(mFloors.get(1), mElevator.getTarget());
		mFloors.get(1).setButtonUp(false);
		
		mAdvAutoModeService.refresh();
		assertEquals(mFloors.get(0), mElevator.getTarget());
		
		mAdvAutoModeService.refresh();
		assertEquals(mFloors.get(2), mElevator.getTarget());	
		mFloors.get(2).setButtonUp(false);
		
		mAdvAutoModeService.refresh();
		assertEquals(mFloors.get(0), mElevator.getTarget());
	}
	
	@Test 
	public void testServiceAllFloorsAllButton()
	{
		mElevator.setButton(mFloors.get(0), true);
		mElevator.setButton(mFloors.get(1), true);
		mElevator.setButton(mFloors.get(2), true);
		
		mElevator.setService(mFloors.get(0), true);
		mElevator.setService(mFloors.get(1), true);
		mElevator.setService(mFloors.get(2), true);
		
		mElevator.setAutomaticMode(true);		
		assertEquals(null, mElevator.getTarget());
		
		mAdvAutoModeService.refresh();
		assertEquals(mFloors.get(0), mElevator.getTarget());
		mElevator.setButton(mFloors.get(0), false);
		
		mAdvAutoModeService.refresh();
		assertEquals(mFloors.get(1), mElevator.getTarget());
		mElevator.setButton(mFloors.get(1), false);
		
		mAdvAutoModeService.refresh();
		assertEquals(mFloors.get(0), mElevator.getTarget());
		
		mAdvAutoModeService.refresh();
		assertEquals(mFloors.get(2), mElevator.getTarget());	
		mElevator.setButton(mFloors.get(2), false);
		
		mAdvAutoModeService.refresh();
		assertEquals(mFloors.get(0), mElevator.getTarget());
	}
}