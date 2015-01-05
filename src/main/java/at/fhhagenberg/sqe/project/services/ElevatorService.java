package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

/**
 * Helper Class to Refresh a Elevators Data
 */
public class ElevatorService implements IService, PropertyChangeListener {

    private final IElevatorAdapter mAdapter;
    private final Elevator mElevator;
    private final int mElevatorNumber;

    private boolean mIsUpdating;

    public ElevatorService(IElevatorAdapter adapter, Elevator elevator) {
        mAdapter = adapter;
        mElevator = elevator;
        mElevatorNumber = mElevator.getElevatorNumber();
        mElevator.addPropertyChangeListener(Elevator.PROP_SERVICE, this);
        mElevator.addPropertyChangeListener(Elevator.PROP_TARGET, this);
        mElevator.addPropertyChangeListener(Elevator.PROP_DIRECTION, this);
    }

    @Override
    public void refresh() throws ElevatorConnectionLostException {
        if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_BUTTON) > 0) {
            for (Floor floor : mElevator.getFloors()) {
                mElevator.setButton(floor, mAdapter.getElevatorButton(mElevatorNumber, floor.getFloorNumber()));
            }
        }
        if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_SERVICE) > 1) {
            for (Floor floor : mElevator.getFloors()) {
                boolean service = mAdapter.getServicesFloors(mElevatorNumber, floor.getFloorNumber());
                mIsUpdating = true;
                mElevator.setService(floor, service);
                mIsUpdating = false;
            }
        }
        if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_TARGET) > 1) {
            int target = mAdapter.getTarget(mElevatorNumber);
            Floor targetFloor = mElevator.getFloor(target);
            mIsUpdating = true;
            mElevator.setTarget(targetFloor);
            mIsUpdating = false;
        }
        if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_POSITION) > 0) {
            mElevator.setPosition(mAdapter.getElevatorPosition(mElevatorNumber));
        }
        if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_ACCELERATION) > 0) {
            mElevator.setAcceleration(mAdapter.getElevatorAccel(mElevatorNumber));
        }
        if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_CAPACITY) > 0) {
            mElevator.setCapacity(mAdapter.getElevatorCapacity(mElevatorNumber));
        }
        if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_CURRENT_FLOOR) > 0) {
            int current = mAdapter.getElevatorFloor(mElevatorNumber);
            Floor currentFloor = mElevator.getFloor(current);
            mElevator.setCurrentFloor(currentFloor);
        }
        if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_DIRECTION) > 1) {
            mIsUpdating = true;
            mElevator.setDirection(mAdapter.getCommittedDirection(mElevatorNumber));
            mIsUpdating = false;
        }
        if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_DOOR_STATUS) > 0) {
            mElevator.setDoorStatus(mAdapter.getElevatorDoorStatus(mElevatorNumber));
        }
        if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_SPEED) > 0) {
            mElevator.setSpeed(mAdapter.getElevatorSpeed(mElevatorNumber));
        }
        if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_WEIGHT) > 0) {
            mElevator.setWeight(mAdapter.getElevatorWeight(mElevatorNumber));
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (mIsUpdating) return;
        try {
            switch (evt.getPropertyName()) {
                case Elevator.PROP_SERVICE:
                    Object oldValue = evt.getOldValue();
                    Object newValue = evt.getNewValue();
                    if (!(oldValue instanceof Map) || !(newValue instanceof Map)) break;
                    Map oldMap = (Map) oldValue;
                    Map newMap = (Map) newValue;

                    for (Floor floor : mElevator.getFloors()) {
                        if (!(oldMap.get(floor) instanceof Boolean) || !(newMap.get(floor) instanceof Boolean)) break;
                        if (!oldMap.get(floor).equals(newMap.get(floor))) {
                            mAdapter.setServicesFloors(mElevatorNumber, floor.getFloorNumber(), (Boolean) newMap.get(floor));
                        }
                    }
                    break;
                case Elevator.PROP_TARGET:
                    mAdapter.setTarget(mElevatorNumber, mElevator.getTarget().getFloorNumber());
                    break;
                case Elevator.PROP_DIRECTION:
                    mAdapter.setCommittedDirection(mElevatorNumber, mElevator.getDirection());
                    break;
            }
        } catch (ElevatorConnectionLostException ignored) {
        }
    }
}
