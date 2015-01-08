package at.fhhagenberg.sqe.project.services.automatic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Elevator.Direction;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.model.Elevator.DoorStatus;

/**
 * Created by rknoll on 07/01/15.
 * Modified by tzoechbauer on 08/01/15
 */
public class BaseAutomaticModeServiceTest 
{
    private MyBaseAutomaticModeService mMyBaseAutoModeService;
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
    	
    	mMyBaseAutoModeService = new MyBaseAutomaticModeService(mBuilding, mElevator);
    }    

	@Test 
	public void testAutomaticModeFalseWithOpenDoorAndNoSpeed()
	{			
		mElevator.setAutomaticMode(false);
		mElevator.setSpeed(0);
		mElevator.setDoorStatus(DoorStatus.OPEN);
		
		Floor nextFloor = mFloors.get(1);
		Floor elevatorTarget = mElevator.getTarget();		
		
		mMyBaseAutoModeService.setNextGoalForTest(nextFloor);
		
		mMyBaseAutoModeService.refresh();
		assertEquals(elevatorTarget, mElevator.getTarget());
		assertEquals(Direction.UNCOMMITTED, mElevator.getDirection());
    }
	
	@Test 
	public void testAutomaticModeFalseWithClosedDoorAndNoSpeed()
	{			
		mElevator.setAutomaticMode(false);
		mElevator.setSpeed(0);
		mElevator.setDoorStatus(DoorStatus.CLOSED);
		
		Floor nextFloor = mFloors.get(1);
		Floor elevatorTarget = mElevator.getTarget();		
		
		mMyBaseAutoModeService.setNextGoalForTest(nextFloor);
		
		mMyBaseAutoModeService.refresh();
		assertEquals(elevatorTarget, mElevator.getTarget());
		assertEquals(Direction.UNCOMMITTED, mElevator.getDirection());
    }
	
	@Test 
	public void testAutomaticModeTrueWithOpenDoorAndNoSpeedGoUp()
	{		
		mElevator.setAutomaticMode(true);
		mElevator.setSpeed(0);
		mElevator.setDoorStatus(DoorStatus.OPEN);
		
		Floor nextFloor = mFloors.get(1);		
		
		mMyBaseAutoModeService.setNextGoalForTest(nextFloor);
				
		mMyBaseAutoModeService.refresh();
		assertEquals(nextFloor, mElevator.getTarget());
		assertEquals(Direction.UP, mElevator.getDirection());
    }
	
	@Test 
	public void testAutomaticModeTrueWithOpenDoorAndNoSpeedGoDown()
	{		
		mElevator.setAutomaticMode(true);
		mElevator.setSpeed(0);
		mElevator.setDoorStatus(DoorStatus.OPEN);
		mElevator.setCurrentFloor(mFloors.get(2));
		
		Floor nextFloor = mFloors.get(0);		
		
		mMyBaseAutoModeService.setNextGoalForTest(nextFloor);
				
		mMyBaseAutoModeService.refresh();
		assertEquals(nextFloor, mElevator.getTarget());
		assertEquals(Direction.DOWN, mElevator.getDirection());
    }
	
	@Test 
	public void testAutomaticModeTrueWithOpenDoorAndNoSpeedStayInFloor()
	{		
		mElevator.setAutomaticMode(true);
		mElevator.setSpeed(0);
		mElevator.setDoorStatus(DoorStatus.OPEN);
		
		Floor currentFloor = mFloors.get(1);
		mElevator.setCurrentFloor(currentFloor);
		
		mMyBaseAutoModeService.setNextGoalForTest(currentFloor);
				
		mMyBaseAutoModeService.refresh();
		assertEquals(currentFloor, mElevator.getTarget());
		assertEquals(Direction.UNCOMMITTED, mElevator.getDirection());
    }
	
	@Test 
	public void testAutomaticModeTrueWithClosedDoorAndNoSpeed()
	{		
		mElevator.setAutomaticMode(true);
		mElevator.setSpeed(0);
		mElevator.setDoorStatus(DoorStatus.CLOSED);
		
		Floor nextFloor = mFloors.get(1);	
		Floor elevatorTarget = mElevator.getTarget();
		
		mMyBaseAutoModeService.setNextGoalForTest(nextFloor);
		
		mMyBaseAutoModeService.refresh();
		assertEquals(elevatorTarget, mElevator.getTarget());
		assertEquals(Direction.UNCOMMITTED, mElevator.getDirection());
    }    
}









