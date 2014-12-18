package at.fhhagenberg.sqe.project.connection;

import java.util.ArrayList;
import java.util.List;

import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Elevator.Direction;
import at.fhhagenberg.sqe.project.model.Elevator.DoorStatus;
import at.fhhagenberg.sqe.project.model.Floor;

/**
 * Created by Thomas Zöchbauer on 18/12/14.
 */
public class DummyAdapter implements IElevatorAdapter 
{	
	private List<Floor> mFloors;
	private List<Elevator> mElevators;
	
	private int mNumElevators;
	private int mNumFloors;
	private int mFloorHeight;
	
	public DummyAdapter(int numElevators, int numFloors)
	{
		mNumElevators = numElevators;
		mNumFloors = numFloors;
		mFloorHeight = 9;	// feet
		
		mFloors = new ArrayList<Floor>();
		mElevators = new ArrayList<Elevator>();
		
		for (int i = 0; i < mNumFloors; i++)
		{
			mFloors.add(new Floor(i, "Floor " + i ));
		}
		
		for (int i = 0; i < mNumElevators; i++)
		{
			mElevators.add(new Elevator(i, "E"+i, mFloors));
		}		
	}
	
	@Override
	public Direction getCommittedDirection(int elevatorNumber) throws ElevatorConnectionLostException {
		return mElevators.get(elevatorNumber).getDirection();
	}
	
	@Override
	public int getElevatorAccel(int elevatorNumber)	throws ElevatorConnectionLostException {
		return mElevators.get(elevatorNumber).getAcceleration();
	}

	@Override
	public boolean getElevatorButton(int elevatorNumber, int floor)	throws ElevatorConnectionLostException {
		return mElevators.get(elevatorNumber).getButton(mFloors.get(floor));
	}

	@Override
	public DoorStatus getElevatorDoorStatus(int elevatorNumber)	throws ElevatorConnectionLostException {
		return mElevators.get(elevatorNumber).getDoorStatus();
	}

	@Override
	public int getElevatorFloor(int elevatorNumber)	throws ElevatorConnectionLostException {
		return mElevators.get(elevatorNumber).getCurrentFloor().getFloorNumber();
	}

	@Override
	public int getElevatorNum() throws ElevatorConnectionLostException {
		return mNumElevators;
	}

	@Override
	public int getElevatorPosition(int elevatorNumber) throws ElevatorConnectionLostException {
		return mElevators.get(elevatorNumber).getPosition();
	}

	@Override
	public int getElevatorSpeed(int elevatorNumber)	throws ElevatorConnectionLostException {
		return mElevators.get(elevatorNumber).getSpeed();
	}

	@Override
	public int getElevatorWeight(int elevatorNumber) throws ElevatorConnectionLostException {
		return mElevators.get(elevatorNumber).getWeight();
	}

	@Override
	public int getElevatorCapacity(int elevatorNumber) throws ElevatorConnectionLostException {
		return mElevators.get(elevatorNumber).getCapacity();
	}

	@Override
	public boolean getFloorButtonDown(int floor) throws ElevatorConnectionLostException {
		return mFloors.get(floor).isButtonDown();
	}

	@Override
	public boolean getFloorButtonUp(int floor) throws ElevatorConnectionLostException {
		return mFloors.get(floor).isButtonUp();
	}

	@Override
	public int getFloorHeight() throws ElevatorConnectionLostException {
		return mFloorHeight;
	}

	@Override
	public int getFloorNum() throws ElevatorConnectionLostException {
		return mNumFloors;
	}

	@Override
	public boolean getServicesFloors(int elevatorNumber, int floor)	throws ElevatorConnectionLostException {
		return mElevators.get(elevatorNumber).getService(mFloors.get(floor));
	}

	@Override
	public int getTarget(int elevatorNumber) throws ElevatorConnectionLostException {
		return mElevators.get(elevatorNumber).getTarget().getFloorNumber();
	}

	@Override
	public void setServicesFloors(int elevatorNumber, int floor, boolean service) throws ElevatorConnectionLostException {
		mElevators.get(elevatorNumber).setService(mFloors.get(floor), service);		
	}

	@Override
	public void setTarget(int elevatorNumber, int target) throws ElevatorConnectionLostException {
		mElevators.get(elevatorNumber).setTarget(mFloors.get(target));		
	}

	@Override
	public long getClockTick() throws ElevatorConnectionLostException {
		return 0;
	}

	@Override
	public void setCommittedDirection(int elevatorNumber, Direction direction)	throws ElevatorConnectionLostException {
		mElevators.get(elevatorNumber).setDirection(direction);
		
	}

}
