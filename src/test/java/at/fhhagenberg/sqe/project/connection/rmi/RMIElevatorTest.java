package at.fhhagenberg.sqe.project.connection.rmi;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.model.Elevator;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import sqelevator.IElevator;

import java.rmi.RemoteException;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

public class RMIElevatorTest {
    private IElevator mMock;
    private RMIElevator mTestObj;

    // TODO: finish RMIElevator tests

    @Before
    public void setUp() throws Exception {
        mMock = createMock(IElevator.class);
        mTestObj = new RMIElevator(mMock);
    }

    @Test
    public void testGetCommittedDirection() {
        try {
            EasyMock.expect(mMock.getCommittedDirection(0)).andReturn(0);
            EasyMock.expect(mMock.getCommittedDirection(0)).andReturn(1);
            EasyMock.expect(mMock.getCommittedDirection(0)).andReturn(2);
            EasyMock.expect(mMock.getCommittedDirection(0)).andThrow(new RemoteException());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

        replay(mMock);

        try {
            assertEquals(Elevator.Direction.UP, mTestObj.getCommittedDirection(0));
            assertEquals(Elevator.Direction.DOWN, mTestObj.getCommittedDirection(0));
            assertEquals(Elevator.Direction.UNCOMMITTED, mTestObj.getCommittedDirection(0));
            mTestObj.getCommittedDirection(0);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }

    @Test
    public void testGetElevatorAccel() {
        try {
            EasyMock.expect(mMock.getElevatorAccel(0)).andReturn(0);
            EasyMock.expect(mMock.getElevatorAccel(0)).andThrow(new RemoteException());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

        replay(mMock);

        try {
        	mTestObj.getElevatorAccel(0);
            mTestObj.getElevatorAccel(0);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }

    @Test
    public void testGetElevatorButton() {
        try {
            EasyMock.expect(mMock.getElevatorButton(0, 0)).andReturn(false);
            EasyMock.expect(mMock.getElevatorButton(0, 0)).andThrow(new RemoteException());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

        replay(mMock);

        try {
            mTestObj.getElevatorButton(0, 0);
            mTestObj.getElevatorButton(0, 0);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }

    @Test
    public void testGetElevatorDoorStatus() {
        try {
            EasyMock.expect(mMock.getElevatorDoorStatus(0)).andReturn(1);
            EasyMock.expect(mMock.getElevatorDoorStatus(0)).andReturn(2);
            EasyMock.expect(mMock.getElevatorDoorStatus(0)).andReturn(3);
            EasyMock.expect(mMock.getElevatorDoorStatus(0)).andReturn(4);
            EasyMock.expect(mMock.getElevatorDoorStatus(0)).andThrow(new RemoteException());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

        replay(mMock);

        try {
        	mTestObj.getElevatorDoorStatus(0);
        	mTestObj.getElevatorDoorStatus(0);
        	mTestObj.getElevatorDoorStatus(0);
            mTestObj.getElevatorDoorStatus(0);
            mTestObj.getElevatorDoorStatus(0);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testGetElevatorFloor() {
    	try {
    		EasyMock.expect(mMock.getElevatorFloor(0)).andReturn(0);
    		EasyMock.expect(mMock.getElevatorFloor(0)).andThrow(new RemoteException());
    	} catch (RemoteException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	replay(mMock);

        try {
            mTestObj.getElevatorFloor(0);
            mTestObj.getElevatorFloor(0);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testGetElevatorNum() {
    	try {
    		EasyMock.expect(mMock.getElevatorNum()).andReturn(0);
    		EasyMock.expect(mMock.getElevatorNum()).andThrow(new RemoteException());
    	} catch (RemoteException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	replay(mMock);

        try {
            mTestObj.getElevatorNum();
            mTestObj.getElevatorNum();
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testGetElevatorPosition() {
    	try {
    		EasyMock.expect(mMock.getElevatorPosition(0)).andReturn(0);
    		EasyMock.expect(mMock.getElevatorPosition(0)).andThrow(new RemoteException());
    	} catch (RemoteException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	replay(mMock);

        try {
            mTestObj.getElevatorPosition(0);
            mTestObj.getElevatorPosition(0);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testGetElevatorSpeed() {
    	try {
    		EasyMock.expect(mMock.getElevatorSpeed(0)).andReturn(0);
    		EasyMock.expect(mMock.getElevatorSpeed(0)).andThrow(new RemoteException());
    	} catch (RemoteException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	replay(mMock);

        try {
            mTestObj.getElevatorSpeed(0);
            mTestObj.getElevatorSpeed(0);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testGetElevatorWeight() {
    	try {
    		EasyMock.expect(mMock.getElevatorWeight(0)).andReturn(0);
    		EasyMock.expect(mMock.getElevatorWeight(0)).andThrow(new RemoteException());
    	} catch (RemoteException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	replay(mMock);

        try {
            mTestObj.getElevatorWeight(0);
            mTestObj.getElevatorWeight(0);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testGetElevatorCapacity() {
    	try {
    		EasyMock.expect(mMock.getElevatorCapacity(0)).andReturn(0);
    		EasyMock.expect(mMock.getElevatorCapacity(0)).andThrow(new RemoteException());
    	} catch (RemoteException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	replay(mMock);

        try {
            mTestObj.getElevatorCapacity(0);
            mTestObj.getElevatorCapacity(0);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
}
