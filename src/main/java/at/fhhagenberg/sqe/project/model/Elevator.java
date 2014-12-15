package at.fhhagenberg.sqe.project.model;

import java.rmi.RemoteException;

/**
 * Created by rknoll on 15/12/14.
 */
public class Elevator {

    private IElevator mConnection;
    private int mElevatorNumber;

    public Elevator(IElevator connection, int elevatorNumber) {
        mConnection = connection;
        mElevatorNumber = elevatorNumber;
    }

    public void setTarget(int target) throws RemoteException {
        mConnection.setTarget(mElevatorNumber, target);
    }
}
