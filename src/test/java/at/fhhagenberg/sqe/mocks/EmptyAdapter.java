package at.fhhagenberg.sqe.mocks;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Elevator;

/**
 * Created by rknoll on 16/12/14.
 */
public class EmptyAdapter implements IElevatorAdapter {
    @Override
    public Elevator.Direction getCommittedDirection(int elevatorNumber) throws ElevatorConnectionLostException {
        return null;
    }

    @Override
    public int getElevatorAccel(int elevatorNumber) throws ElevatorConnectionLostException {
        return 0;
    }

    @Override
    public boolean getElevatorButton(int elevatorNumber, int floor) throws ElevatorConnectionLostException {
        return false;
    }

    @Override
    public Elevator.DoorStatus getElevatorDoorStatus(int elevatorNumber) throws ElevatorConnectionLostException {
        return null;
    }

    @Override
    public int getElevatorFloor(int elevatorNumber) throws ElevatorConnectionLostException {
        return 0;
    }

    @Override
    public int getElevatorNum() throws ElevatorConnectionLostException {
        return 2;
    }

    @Override
    public int getElevatorPosition(int elevatorNumber) throws ElevatorConnectionLostException {
        return 0;
    }

    @Override
    public int getElevatorSpeed(int elevatorNumber) throws ElevatorConnectionLostException {
        return 0;
    }

    @Override
    public int getElevatorWeight(int elevatorNumber) throws ElevatorConnectionLostException {
        return 0;
    }

    @Override
    public int getElevatorCapacity(int elevatorNumber) throws ElevatorConnectionLostException {
        return 0;
    }

    @Override
    public boolean getFloorButtonDown(int floor) throws ElevatorConnectionLostException {
        return false;
    }

    @Override
    public boolean getFloorButtonUp(int floor) throws ElevatorConnectionLostException {
        return false;
    }

    @Override
    public int getFloorHeight() throws ElevatorConnectionLostException {
        return 0;
    }

    @Override
    public int getFloorNum() throws ElevatorConnectionLostException {
        return 3;
    }

    @Override
    public boolean getServicesFloors(int elevatorNumber, int floor) throws ElevatorConnectionLostException {
        return false;
    }

    @Override
    public int getTarget(int elevatorNumber) throws ElevatorConnectionLostException {
        return 0;
    }

    @Override
    public void setCommittedDirection(int elevatorNumber, Elevator.Direction direction) throws ElevatorConnectionLostException {

    }

    @Override
    public void setServicesFloors(int elevatorNumber, int floor, boolean service) throws ElevatorConnectionLostException {

    }

    @Override
    public void setTarget(int elevatorNumber, int target) throws ElevatorConnectionLostException {

    }

    @Override
    public long getClockTick() throws ElevatorConnectionLostException {
        return 0;
    }
}
