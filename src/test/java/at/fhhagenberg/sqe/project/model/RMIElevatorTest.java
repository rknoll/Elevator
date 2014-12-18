package at.fhhagenberg.sqe.project.model;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import at.fhhagenberg.sqe.project.model.Elevator.Direction;
import at.fhhagenberg.sqe.project.model.Elevator.DoorStatus;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.RMIElevator;

import sqlelevator.IElevator;

public class RMIElevatorTest {
	IElevator mMock;
	RMIElevator mTestObj;
	DoorStatus status;

	@Before
	public void setUp() throws Exception {
		mMock = createMock(IElevator.class);
		mTestObj = new RMIElevator(mMock);
	}

	@Test
	public void testGetCommittedDirection() {
		try{
			mMock.getCommittedDirection(0);
		}
		catch (RemoteException e){
			System.out.println(e.getMessage());
		}
		
		replay(mMock);
		
		try{
			mTestObj.getCommittedDirection(0);
		}
		catch (ElevatorConnectionLostException e){
			System.out.println(e.getMessage());
		}
		
		verify(mMock);
	}
	
	@Test
	public void testGetElevatorAccel() {
		try{
			mMock.getElevatorAccel(0);
		}
		catch (RemoteException e){
			System.out.println(e.getMessage());
		}
		
		replay(mMock);
		
		try{
			mTestObj.getElevatorAccel(0);
		}
		catch (ElevatorConnectionLostException e){
			System.out.println(e.getMessage());
		}
		
		verify(mMock);
	}
	
	@Test
	public void testGetElevatorButton() {
		try{
			mMock.getElevatorButton(0,0);
		}
		catch (RemoteException e){
			System.out.println(e.getMessage());
		}
		
		replay(mMock);
		
		try{
			mTestObj.getElevatorButton(0,0);
		}
		catch (ElevatorConnectionLostException e){
			System.out.println(e.getMessage());
		}
		
		verify(mMock);
	}
	
	@Test
	public void testGetElevatorDoorStatus() {
		try{
			mMock.getElevatorDoorStatus(0);
		}
		catch (RemoteException e){
			System.out.println(e.getMessage());
		}
		
		replay(mMock);
		
		try{
			mTestObj.getElevatorDoorStatus(0);
		}
		catch (ElevatorConnectionLostException e){
			System.out.println(e.getMessage());
		}
		
		verify(mMock);
	}
}
