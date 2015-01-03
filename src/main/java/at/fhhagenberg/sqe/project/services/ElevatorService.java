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

    private IElevatorAdapter mAdapter;
    private Elevator mElevator;
    private boolean mIsUpdating;

    public ElevatorService(IElevatorAdapter adapter, Elevator elevator) {
        mAdapter = adapter;
        mElevator = elevator;
        mElevator.addPropertyChangeListener(Elevator.PROP_SERVICE, this);
        mElevator.addPropertyChangeListener(Elevator.PROP_TARGET, this);
    }

    @Override
    public void refresh() {
        try {
            if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_BUTTON) > 0) {
                for (Floor floor : mElevator.getFloors()) {
                    mElevator.setButton(floor, mAdapter.getElevatorButton(mElevator.getElevatorNumber(), floor.getFloorNumber()));
                }
            }
            if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_SERVICE) > 1) {
                for (Floor floor : mElevator.getFloors()) {
                    boolean service = mAdapter.getServicesFloors(mElevator.getElevatorNumber(), floor.getFloorNumber());
                    mIsUpdating = true;
                    mElevator.setService(floor, service);
                    mIsUpdating = false;
                }
            }
            if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_TARGET) > 1) {
                int target = mAdapter.getTarget(mElevator.getElevatorNumber());
                Floor targetFloor = null;
                for (Floor floor : mElevator.getFloors()) {
                    targetFloor = floor;
                    if (floor.getFloorNumber() == target) break;
                }
                if (targetFloor != null && targetFloor.getFloorNumber() == target) {
                    mIsUpdating = true;
                    mElevator.setTarget(targetFloor);
                    mIsUpdating = false;
                }
            }
            if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_POSITION) > 0) {
                mElevator.setPosition(mAdapter.getElevatorPosition(mElevator.getElevatorNumber()));
            }
            if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_ACCELERATION) > 0) {
                mElevator.setAcceleration(mAdapter.getElevatorAccel(mElevator.getElevatorNumber()));
            }
            if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_CAPACITY) > 0) {
                mElevator.setCapacity(mAdapter.getElevatorCapacity(mElevator.getElevatorNumber()));
            }
            if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_CURRENT_FLOOR) > 0) {
                int current = mAdapter.getElevatorFloor(mElevator.getElevatorNumber());
                Floor currentFloor = null;
                for (Floor floor : mElevator.getFloors()) {
                    currentFloor = floor;
                    if (floor.getFloorNumber() == current) break;
                }
                if (currentFloor != null && currentFloor.getFloorNumber() == current) {
                    mElevator.setCurrentFloor(currentFloor);
                }
            }
            if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_DIRECTION) > 0) {
                mElevator.setDirection(mAdapter.getCommittedDirection(mElevator.getElevatorNumber()));
            }
            if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_DOOR_STATUS) > 0) {
                mElevator.setDoorStatus(mAdapter.getElevatorDoorStatus(mElevator.getElevatorNumber()));
            }
            if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_SPEED) > 0) {
                mElevator.setSpeed(mAdapter.getElevatorSpeed(mElevator.getElevatorNumber()));
            }
            if (mElevator.getPropertyChangeListenersCount(Elevator.PROP_WEIGHT) > 0) {
                mElevator.setWeight(mAdapter.getElevatorWeight(mElevator.getElevatorNumber()));
            }
        } catch (ElevatorConnectionLostException ignored) {
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (mIsUpdating) return;
        try {
            switch (evt.getPropertyName()) {
                case Elevator.PROP_SERVICE:
                    Map<Floor, Boolean> oldValue = (Map<Floor, Boolean>)(evt.getOldValue());
                    Map<Floor, Boolean> newValue = (Map<Floor, Boolean>)(evt.getNewValue());
                    for (Floor floor : mElevator.getFloors()) {
                        if (!oldValue.get(floor).equals(newValue.get(floor))) {
                            mAdapter.setServicesFloors(mElevator.getElevatorNumber(), floor.getFloorNumber(), newValue.get(floor));
                        }
                    }
                    break;
                case Elevator.PROP_TARGET:
                    mAdapter.setTarget(mElevator.getElevatorNumber(), mElevator.getTarget().getFloorNumber());
                    break;
            }
        } catch (ElevatorConnectionLostException ignored) {
        }
    }
}
