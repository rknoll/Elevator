package at.fhhagenberg.sqe.project.connection;

import at.fhhagenberg.sqe.project.model.Elevator;

/**
 * Created by rknoll on 16/12/14.
 */
public interface IElevatorAdapter {

    public Elevator.Direction getCommittedDirection(int elevatorNumber) throws ElevatorConnectionLostException;

    public int getElevatorAccel(int elevatorNumber) throws ElevatorConnectionLostException;

    public boolean getElevatorButton(int elevatorNumber, int floor) throws ElevatorConnectionLostException;

    public Elevator.DoorStatus getElevatorDoorStatus(int elevatorNumber) throws ElevatorConnectionLostException;

    public int getElevatorFloor(int elevatorNumber) throws ElevatorConnectionLostException;

    public int getElevatorNum() throws ElevatorConnectionLostException;

    public int getElevatorPosition(int elevatorNumber) throws ElevatorConnectionLostException;

    public int getElevatorSpeed(int elevatorNumber) throws ElevatorConnectionLostException;

    public int getElevatorWeight(int elevatorNumber) throws ElevatorConnectionLostException;

    public int getElevatorCapacity(int elevatorNumber) throws ElevatorConnectionLostException;

    public boolean getFloorButtonDown(int floor) throws ElevatorConnectionLostException;

    public boolean getFloorButtonUp(int floor) throws ElevatorConnectionLostException;

    public int getFloorHeight() throws ElevatorConnectionLostException;

    public int getFloorNum() throws ElevatorConnectionLostException;

    public boolean getServicesFloors(int elevatorNumber, int floor) throws ElevatorConnectionLostException;

    public int getTarget(int elevatorNumber) throws ElevatorConnectionLostException;

    public void setCommittedDirection(int elevatorNumber, int direction) throws ElevatorConnectionLostException;

    public void setServicesFloors(int elevatorNumber, int floor, boolean service) throws ElevatorConnectionLostException;

    public void setTarget(int elevatorNumber, int target) throws ElevatorConnectionLostException;

    public long getClockTick() throws ElevatorConnectionLostException;
}
