package at.fhhagenberg.sqe.project.connection;

import at.fhhagenberg.sqe.project.model.Elevator;

/**
 * Created by rknoll on 16/12/14.
 */
public class TestElevatorAdapter implements IElevatorAdapter {
    @Override
    public Elevator.Direction getCommittedDirection(int elevatorNumber) throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public int getElevatorAccel(int elevatorNumber) throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public boolean getElevatorButton(int elevatorNumber, int floor) throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public Elevator.DoorStatus getElevatorDoorStatus(int elevatorNumber) throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public int getElevatorFloor(int elevatorNumber) throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public int getElevatorNum() throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public int getElevatorPosition(int elevatorNumber) throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public int getElevatorSpeed(int elevatorNumber) throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public int getElevatorWeight(int elevatorNumber) throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public int getElevatorCapacity(int elevatorNumber) throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public boolean getFloorButtonDown(int floor) throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public boolean getFloorButtonUp(int floor) throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public int getFloorHeight() throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public int getFloorNum() throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public boolean getServicesFloors(int elevatorNumber, int floor) throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public int getTarget(int elevatorNumber) throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public void setCommittedDirection(int elevatorNumber, Elevator.Direction direction) throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public void setServicesFloors(int elevatorNumber, int floor, boolean service) throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public void setTarget(int elevatorNumber, int target) throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }

    @Override
    public long getClockTick() throws ElevatorConnectionLostException {
        throw new ElevatorConnectionLostException();
    }
}
