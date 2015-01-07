package at.fhhagenberg.sqe.project.connection.dummy;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Elevator;

/**
 * Example Elevator to Test UI Components without an active Connection.
 * To use it, simply change the Spring Configuration to return DummyElevator::new
 */
public class DummyElevator implements IElevatorAdapter {

    /**
     * The current Button State
     */
    private boolean mBtnState;

    public DummyElevator() {
        // create a thread to periodically switch the floor button states
        Thread updateThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
                mBtnState = !mBtnState;
            }
        });
        updateThread.setDaemon(true); // has to be a daemon thread as the program would not stop otherwise
        updateThread.start();
    }

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
        return 3;
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
        return mBtnState;
    }

    @Override
    public boolean getFloorButtonUp(int floor) throws ElevatorConnectionLostException {
        return !mBtnState;
    }

    @Override
    public int getFloorHeight() throws ElevatorConnectionLostException {
        return 10;
    }

    @Override
    public int getFloorNum() throws ElevatorConnectionLostException {
        return 5;
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
