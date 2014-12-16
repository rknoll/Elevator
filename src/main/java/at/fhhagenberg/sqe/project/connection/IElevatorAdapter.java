package at.fhhagenberg.sqe.project.connection;

/**
 * Created by rknoll on 16/12/14.
 */
public interface IElevatorAdapter {

    public int getCommittedDirection(int elevatorNumber);

    public int getElevatorAccel(int elevatorNumber);

    public boolean getElevatorButton(int elevatorNumber, int floor);

    public int getElevatorDoorStatus(int elevatorNumber);

    public int getElevatorFloor(int elevatorNumber);

    public int getElevatorNum();

    public int getElevatorPosition(int elevatorNumber);

    public int getElevatorSpeed(int elevatorNumber);

    public int getElevatorWeight(int elevatorNumber);

    public int getElevatorCapacity(int elevatorNumber);

    public boolean getFloorButtonDown(int floor);

    public boolean getFloorButtonUp(int floor);

    public int getFloorHeight();

    public int getFloorNum();

    public boolean getServicesFloors(int elevatorNumber, int floor);

    public int getTarget(int elevatorNumber);

    public void setCommittedDirection(int elevatorNumber, int direction);

    public void setServicesFloors(int elevatorNumber, int floor, boolean service);

    public void setTarget(int elevatorNumber, int target);

    public long getClockTick();
}
