package at.fhhagenberg.sqe.project.connection.rmi;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Elevator;
import sqelevator.IElevator;

import java.rmi.RemoteException;

/**
 * Connection to an Elevator using RMI
 */
public class RMIElevator implements IElevatorAdapter {

    private final IElevator mElevatorConnection;

    public RMIElevator(IElevator elevatorConnection) {
        mElevatorConnection = elevatorConnection;
    }

    @Override
    public Elevator.Direction getCommittedDirection(int elevatorNumber) throws ElevatorConnectionLostException {
        int ret;
        try {
            ret = mElevatorConnection.getCommittedDirection(elevatorNumber);
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
        switch (ret) {
            case IElevator.ELEVATOR_DIRECTION_UP:
                return Elevator.Direction.UP;
            case IElevator.ELEVATOR_DIRECTION_DOWN:
                return Elevator.Direction.DOWN;
            default:
                return Elevator.Direction.UNCOMMITTED;
        }
    }

    @Override
    public int getElevatorAccel(int elevatorNumber) throws ElevatorConnectionLostException {
        int ret;
        try {
            ret = mElevatorConnection.getElevatorAccel(elevatorNumber);
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
        return ret;
    }

    @Override
    public boolean getElevatorButton(int elevatorNumber, int floor) throws ElevatorConnectionLostException {
        boolean ret;
        try {
            ret = mElevatorConnection.getElevatorButton(elevatorNumber, floor);
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
        return ret;
    }

    @Override
    public Elevator.DoorStatus getElevatorDoorStatus(int elevatorNumber) throws ElevatorConnectionLostException {
        int ret;
        try {
            ret = mElevatorConnection.getElevatorDoorStatus(elevatorNumber);
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
        switch (ret) {
            case IElevator.ELEVATOR_DOORS_OPEN:
                return Elevator.DoorStatus.OPEN;
            case IElevator.ELEVATOR_DOORS_OPENING:
                return Elevator.DoorStatus.OPENING;
            case IElevator.ELEVATOR_DOORS_CLOSING:
                return Elevator.DoorStatus.CLOSING;
            default:
                return Elevator.DoorStatus.CLOSED;
        }
    }

    @Override
    public int getElevatorFloor(int elevatorNumber) throws ElevatorConnectionLostException {
        int ret;
        try {
            ret = mElevatorConnection.getElevatorFloor(elevatorNumber);
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
        return ret;
    }

    @Override
    public int getElevatorNum() throws ElevatorConnectionLostException {
        int ret;
        try {
            ret = mElevatorConnection.getElevatorNum();
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
        return ret;
    }

    @Override
    public int getElevatorPosition(int elevatorNumber) throws ElevatorConnectionLostException {
        int ret;
        try {
            ret = mElevatorConnection.getElevatorPosition(elevatorNumber);
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
        return ret;
    }

    @Override
    public int getElevatorSpeed(int elevatorNumber) throws ElevatorConnectionLostException {
        int ret;
        try {
            ret = mElevatorConnection.getElevatorSpeed(elevatorNumber);
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
        return ret;
    }

    @Override
    public int getElevatorWeight(int elevatorNumber) throws ElevatorConnectionLostException {
        int ret;
        try {
            ret = mElevatorConnection.getElevatorWeight(elevatorNumber);
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
        return ret;
    }

    @Override
    public int getElevatorCapacity(int elevatorNumber) throws ElevatorConnectionLostException {
        int ret;
        try {
            ret = mElevatorConnection.getElevatorCapacity(elevatorNumber);
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
        return ret;
    }

    @Override
    public boolean getFloorButtonDown(int floor) throws ElevatorConnectionLostException {
        boolean ret;
        try {
            ret = mElevatorConnection.getFloorButtonDown(floor);
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
        return ret;
    }

    @Override
    public boolean getFloorButtonUp(int floor) throws ElevatorConnectionLostException {
        boolean ret;
        try {
            ret = mElevatorConnection.getFloorButtonUp(floor);
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
        return ret;
    }

    @Override
    public int getFloorHeight() throws ElevatorConnectionLostException {
        int ret;
        try {
            ret = mElevatorConnection.getFloorHeight();
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
        return ret;
    }

    @Override
    public int getFloorNum() throws ElevatorConnectionLostException {
        int ret;
        try {
            ret = mElevatorConnection.getFloorNum();
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
        return ret;
    }

    @Override
    public boolean getServicesFloors(int elevatorNumber, int floor) throws ElevatorConnectionLostException {
        boolean ret;
        try {
            ret = mElevatorConnection.getServicesFloors(elevatorNumber, floor);
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
        return ret;
    }

    @Override
    public int getTarget(int elevatorNumber) throws ElevatorConnectionLostException {
        int ret;
        try {
            ret = mElevatorConnection.getTarget(elevatorNumber);
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
        return ret;
    }

    @Override
    public void setCommittedDirection(int elevatorNumber, Elevator.Direction direction) throws ElevatorConnectionLostException {
        try {
            int dir = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
            switch (direction) {
                case UP:
                    dir = IElevator.ELEVATOR_DIRECTION_UP;
                    break;
                case DOWN:
                    dir = IElevator.ELEVATOR_DIRECTION_DOWN;
                    break;
                default:
                    break;
            }
            mElevatorConnection.setCommittedDirection(elevatorNumber, dir);
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
    }

    @Override
    public void setServicesFloors(int elevatorNumber, int floor, boolean service) throws ElevatorConnectionLostException {
        try {
            mElevatorConnection.setServicesFloors(elevatorNumber, floor, service);
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
    }

    @Override
    public void setTarget(int elevatorNumber, int target) throws ElevatorConnectionLostException {
        try {
            mElevatorConnection.setTarget(elevatorNumber, target);
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
    }

    @Override
    public long getClockTick() throws ElevatorConnectionLostException {
        long ret;
        try {
            ret = mElevatorConnection.getClockTick();
        } catch (RemoteException e) {
            throw new ElevatorConnectionLostException(e);
        }
        return ret;
    }

}
