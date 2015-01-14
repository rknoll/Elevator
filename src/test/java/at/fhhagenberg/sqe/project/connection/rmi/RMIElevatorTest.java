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
            EasyMock.expect(mMock.getElevatorAccel(0)).andReturn(14);
            EasyMock.expect(mMock.getElevatorAccel(0)).andThrow(new RemoteException());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

        replay(mMock);

        try {
        	assertEquals(14, mTestObj.getElevatorAccel(0));
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
            EasyMock.expect(mMock.getElevatorButton(0, 0)).andReturn(true);
            EasyMock.expect(mMock.getElevatorButton(0, 0)).andThrow(new RemoteException());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

        replay(mMock);

        try {
            assertFalse(mTestObj.getElevatorButton(0, 0));
            assertTrue(mTestObj.getElevatorButton(0, 0));
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
        	assertEquals(Elevator.DoorStatus.OPEN, mTestObj.getElevatorDoorStatus(0));
        	assertEquals(Elevator.DoorStatus.CLOSED, mTestObj.getElevatorDoorStatus(0));
        	assertEquals(Elevator.DoorStatus.OPENING, mTestObj.getElevatorDoorStatus(0));
        	assertEquals(Elevator.DoorStatus.CLOSING, mTestObj.getElevatorDoorStatus(0));
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
            assertEquals(0, mTestObj.getElevatorFloor(0));
            mTestObj.getElevatorFloor(0);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testGetElevatorNum() {
    	try {
    		EasyMock.expect(mMock.getElevatorNum()).andReturn(8);
    		EasyMock.expect(mMock.getElevatorNum()).andThrow(new RemoteException());
    	} catch (RemoteException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	replay(mMock);

        try {
            assertEquals(8, mTestObj.getElevatorNum());
            mTestObj.getElevatorNum();
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testGetElevatorPosition() {
    	try {
    		EasyMock.expect(mMock.getElevatorPosition(0)).andReturn(4);
    		EasyMock.expect(mMock.getElevatorPosition(0)).andReturn(12);
    		EasyMock.expect(mMock.getElevatorPosition(0)).andThrow(new RemoteException());
    	} catch (RemoteException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	replay(mMock);

        try {
            assertEquals(4, mTestObj.getElevatorPosition(0));
            assertEquals(12, mTestObj.getElevatorPosition(0));
            mTestObj.getElevatorPosition(0);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testGetElevatorSpeed() {
    	try {
    		EasyMock.expect(mMock.getElevatorSpeed(0)).andReturn(45);
    		EasyMock.expect(mMock.getElevatorSpeed(0)).andThrow(new RemoteException());
    	} catch (RemoteException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	replay(mMock);

        try {
            assertEquals(45, mTestObj.getElevatorSpeed(0));
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
            assertEquals(0, mTestObj.getElevatorWeight(0));
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
        	assertEquals(0, mTestObj.getElevatorCapacity(0));
            mTestObj.getElevatorCapacity(0);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testGetFloorButtonDown() {
    	try {
    		EasyMock.expect(mMock.getFloorButtonDown(0)).andReturn(false);
    		EasyMock.expect(mMock.getFloorButtonDown(0)).andReturn(true);
    		EasyMock.expect(mMock.getFloorButtonDown(0)).andThrow(new RemoteException());
    	} catch (RemoteException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	replay(mMock);

        try {
            assertFalse(mTestObj.getFloorButtonDown(0));
            assertTrue(mTestObj.getFloorButtonDown(0));
            mTestObj.getFloorButtonDown(0);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testGetFloorButtonUp() {
    	try {
    		EasyMock.expect(mMock.getFloorButtonUp(0)).andReturn(false);
    		EasyMock.expect(mMock.getFloorButtonUp(0)).andReturn(true);
    		EasyMock.expect(mMock.getFloorButtonUp(0)).andThrow(new RemoteException());
    	} catch (RemoteException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	replay(mMock);

        try {
            assertFalse(mTestObj.getFloorButtonUp(0));
            assertTrue(mTestObj.getFloorButtonUp(0));
            mTestObj.getFloorButtonUp(0);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testGetFloorHeight() {
    	try {
    		EasyMock.expect(mMock.getFloorHeight()).andReturn(10);
    		EasyMock.expect(mMock.getFloorHeight()).andThrow(new RemoteException());
    	} catch (RemoteException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	replay(mMock);

        try {
            assertEquals(10, mTestObj.getFloorHeight());
            mTestObj.getFloorHeight();
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testGetFloorNum() {
    	try {
    		EasyMock.expect(mMock.getFloorNum()).andReturn(10);
    		EasyMock.expect(mMock.getFloorNum()).andReturn(5);
    		EasyMock.expect(mMock.getFloorNum()).andReturn(234);
    		EasyMock.expect(mMock.getFloorNum()).andThrow(new RemoteException());
    	} catch (RemoteException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	replay(mMock);

        try {
            assertEquals(10, mTestObj.getFloorNum());
            assertEquals(5, mTestObj.getFloorNum());
            assertEquals(234, mTestObj.getFloorNum());
            mTestObj.getFloorNum();
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testGetServicesFloor() {
        try {
            EasyMock.expect(mMock.getServicesFloors(0, 0)).andReturn(false);
            EasyMock.expect(mMock.getServicesFloors(0, 0)).andReturn(true);
            EasyMock.expect(mMock.getServicesFloors(0, 0)).andThrow(new RemoteException());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

        replay(mMock);

        try {
            assertFalse(mTestObj.getServicesFloors(0, 0));
            assertTrue(mTestObj.getServicesFloors(0, 0));
            mTestObj.getServicesFloors(0, 0);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testGetTarget() {
    	try {
    		EasyMock.expect(mMock.getTarget(0)).andReturn(4);
    		EasyMock.expect(mMock.getTarget(0)).andThrow(new RemoteException());
    	} catch (RemoteException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	replay(mMock);

        try {
        	assertEquals(4, mTestObj.getTarget(0));
            mTestObj.getTarget(0);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testSetCommittedDirection() {
        try{
        	mMock.setCommittedDirection(0, 0);
        	mMock.setCommittedDirection(0, 1);
        	mMock.setCommittedDirection(0, 2);
        	EasyMock.expectLastCall().andThrow(new RemoteException());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

        replay(mMock);

        try {
            mTestObj.setCommittedDirection(0, Elevator.Direction.UP);
            mTestObj.setCommittedDirection(0, Elevator.Direction.DOWN);
            mTestObj.setCommittedDirection(0, Elevator.Direction.UNCOMMITTED);
            mTestObj.setCommittedDirection(0, Elevator.Direction.UNCOMMITTED);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testSetServicesFloor() {
        try{
        	mMock.setServicesFloors(0, 3, true);
        	mMock.setServicesFloors(3, 1, false);
        	mMock.setServicesFloors(0, 0, false);
        	EasyMock.expectLastCall().andThrow(new RemoteException());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

        replay(mMock);

        try {
            mTestObj.setServicesFloors(0, 3, true);
            mTestObj.setServicesFloors(3, 1, false);
            mTestObj.setServicesFloors(0, 0, false);
            mTestObj.setServicesFloors(0, 0, false);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testSetTarget() {
        try{
        	mMock.setTarget(0, 3);
        	mMock.setTarget(8, 11);
        	EasyMock.expectLastCall().andThrow(new RemoteException());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

        replay(mMock);

        try {
            mTestObj.setTarget(0, 3);
            mTestObj.setTarget(8, 11);
            mTestObj.setTarget(8, 11);
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
    
    @Test
    public void testGetClockTick() {
    	try {
    		EasyMock.expect(mMock.getClockTick()).andReturn((long) 10);
    		EasyMock.expect(mMock.getClockTick()).andReturn((long) 5);
    		EasyMock.expect(mMock.getClockTick()).andThrow(new RemoteException());
    	} catch (RemoteException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	replay(mMock);

        try {
            assertEquals(10, mTestObj.getClockTick());
            assertEquals(5, mTestObj.getClockTick());
            mTestObj.getClockTick();
        } catch (ElevatorConnectionLostException e) {
            System.out.println(e.getMessage());
        }

        verify(mMock);
    }
}
