package at.fhhagenberg.sqe.project.connection;

import sqlelevator.IElevator;

import java.rmi.RemoteException;

/**
 * Created by rknoll on 16/12/14.
 */
public class RMIElevator implements IElevatorAdapter {

    private IElevator mElevatorConnection;

    public RMIElevator(IElevator elevatorConnection) {
        mElevatorConnection = elevatorConnection;
    }

    @Override
    public int getCommittedDirection(int elevatorNumber) {
        int ret;
        try {
            ret = mElevatorConnection.getCommittedDirection(elevatorNumber);
        } catch (RemoteException e) {
            ret = 0;
        }
        return ret;
    }

    @Override
    public int getElevatorAccel(int elevatorNumber) {
        return 0;
    }

    @Override
    public boolean getElevatorButton(int elevatorNumber, int floor) {
        return false;
    }

    @Override
    public int getElevatorDoorStatus(int elevatorNumber) {
        return 0;
    }

    @Override
    public int getElevatorFloor(int elevatorNumber) {
        return 0;
    }

    @Override
    public int getElevatorNum() {
        return 0;
    }

    @Override
    public int getElevatorPosition(int elevatorNumber) {
        return 0;
    }

    @Override
    public int getElevatorSpeed(int elevatorNumber) {
        return 0;
    }

    @Override
    public int getElevatorWeight(int elevatorNumber) {
        return 0;
    }

    @Override
    public int getElevatorCapacity(int elevatorNumber) {
        return 0;
    }

    @Override
    public boolean getFloorButtonDown(int floor) {
        return false;
    }

    @Override
    public boolean getFloorButtonUp(int floor) {
        return false;
    }

    @Override
    public int getFloorHeight() {
        return 0;
    }

    @Override
    public int getFloorNum() {
        return 0;
    }

    @Override
    public boolean getServicesFloors(int elevatorNumber, int floor) {
        return false;
    }

    @Override
    public int getTarget(int elevatorNumber) {
        return 0;
    }

    @Override
    public void setCommittedDirection(int elevatorNumber, int direction) {

    }

    @Override
    public void setServicesFloors(int elevatorNumber, int floor, boolean service) {

    }

    @Override
    public void setTarget(int elevatorNumber, int target) {

    }

    @Override
    public long getClockTick() {
        return 0;
    }
}
