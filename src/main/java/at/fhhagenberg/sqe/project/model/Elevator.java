package at.fhhagenberg.sqe.project.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an Elevator and all its Properties
 */
public class Elevator extends ListenableModel {

    /* All possible Properties */
    public static final String PROP_BUTTON = "button";
    public static final String PROP_SERVICE = "service";
    public static final String PROP_CURRENT_FLOOR = "currentFloor";
    public static final String PROP_POSITION = "position";
    public static final String PROP_TARGET = "target";
    public static final String PROP_DOOR_STATUS = "doorStatus";
    public static final String PROP_SPEED = "speed";
    public static final String PROP_ACCELERATION = "acceleration";
    public static final String PROP_CAPACITY = "capacity";
    public static final String PROP_WEIGHT = "weight";
    public static final String PROP_DIRECTION = "direction";
    public static final String PROP_AUTOMATIC_MODE = "automaticMode";

    public enum Direction {
        UP, DOWN, UNCOMMITTED
    }

    public enum DoorStatus {
        OPEN, CLOSED, OPENING, CLOSING
    }

    private int mElevatorNumber;
    private String mDescription;

    private Floor mCurrentFloor;
    private int mPosition;
    private Floor mTarget;
    private DoorStatus mDoorStatus;
    private int mSpeed;
    private int mAcceleration;
    private int mCapacity;
    private int mWeight;
    private Direction mDirection;

    private boolean mAutomaticMode = false;        // set manual mode per default

    private Map<Floor, Boolean> mFloorServices;
    private Map<Floor, Boolean> mFloorButtons;
    private List<Floor> mFloors;

    public Elevator(int elevatorNumber, String description, Iterable<Floor> floors) {
        mElevatorNumber = elevatorNumber;
        mDescription = description;
        mFloors = new ArrayList<Floor>();
        mFloorServices = new HashMap<Floor, Boolean>();
        mFloorButtons = new HashMap<Floor, Boolean>();
        for (Floor f : floors) {
            mFloors.add(f);
            mFloorServices.put(f, false);
            mFloorButtons.put(f, false);
        }
    }

    public Iterable<Floor> getFloors() {
        return mFloorButtons.keySet();
    }

    public Floor getFloor(int floorNumber) {
        return mFloors.get(floorNumber);
    }

    public int getElevatorNumber() {
        return mElevatorNumber;
    }

    public String getDescription() {
        return mDescription;
    }


    public boolean getService(Floor floor) {
        return mFloorServices.get(floor);
    }

    public void setService(Floor floor, boolean service) {
        if (mFloorServices.get(floor) == service) return;
        Map<Floor, Boolean> oldValue = new HashMap<Floor, Boolean>(mFloorServices);
        mFloorServices.replace(floor, service);
        pcs.firePropertyChange(PROP_SERVICE, oldValue, mFloorServices);
    }

    public boolean getButton(Floor floor) {
        return mFloorButtons.get(floor);
    }

    public void setButton(Floor floor, boolean button) {
        if (mFloorButtons.get(floor) == button) return;
        Map<Floor, Boolean> oldValue = new HashMap<Floor, Boolean>(mFloorButtons);
        mFloorButtons.replace(floor, button);
        pcs.firePropertyChange(PROP_BUTTON, oldValue, mFloorButtons);
    }

    public Floor getCurrentFloor() {
        return mCurrentFloor;
    }

    public void setCurrentFloor(Floor currentFloor) {
        Floor oldValue = mCurrentFloor;
        this.mCurrentFloor = currentFloor;
        pcs.firePropertyChange(PROP_CURRENT_FLOOR, oldValue, mCurrentFloor);
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        int oldValue = mPosition;
        this.mPosition = position;
        pcs.firePropertyChange(PROP_POSITION, oldValue, mPosition);
    }

    public Floor getTarget() {
        return mTarget;
    }

    public void setTarget(Floor target) {
        Floor oldValue = mTarget;
        this.mTarget = target;
        pcs.firePropertyChange(PROP_TARGET, oldValue, mTarget);
    }

    public DoorStatus getDoorStatus() {
        return mDoorStatus;
    }

    public void setDoorStatus(DoorStatus doorStatus) {
        DoorStatus oldValue = mDoorStatus;
        this.mDoorStatus = doorStatus;
        pcs.firePropertyChange(PROP_DOOR_STATUS, oldValue, mDoorStatus);
    }

    public int getSpeed() {
        return mSpeed;
    }

    public void setSpeed(int speed) {
        int oldValue = mSpeed;
        this.mSpeed = speed;
        pcs.firePropertyChange(PROP_SPEED, oldValue, mSpeed);
    }

    public int getAcceleration() {
        return mAcceleration;
    }

    public void setAcceleration(int acceleration) {
        int oldValue = mAcceleration;
        this.mAcceleration = acceleration;
        pcs.firePropertyChange(PROP_ACCELERATION, oldValue, mAcceleration);
    }

    public int getCapacity() {
        return mCapacity;
    }

    public void setCapacity(int capacity) throws IllegalArgumentException {
        if (capacity < 0) {
            throw new IllegalArgumentException();
        }
        int oldValue = mCapacity;
        this.mCapacity = capacity;
        pcs.firePropertyChange(PROP_CAPACITY, oldValue, mCapacity);
    }

    public int getWeight() {
        return mWeight;
    }

    public void setWeight(int weight) {
        int oldValue = mWeight;
        this.mWeight = weight;
        pcs.firePropertyChange(PROP_WEIGHT, oldValue, mWeight);
    }

    public Direction getDirection() {
        return mDirection;
    }

    public void setDirection(Direction direction) {
        Direction oldValue = mDirection;
        this.mDirection = direction;
        pcs.firePropertyChange(PROP_DIRECTION, oldValue, mDirection);
    }

    /**
     * @return true if it is in AutomaticMode
     */
    public boolean isAutomaticMode() {
        return mAutomaticMode;
    }

    /**
     * @param automaticMode true to set it to AutomaticMode
     */
    public void setAutomaticMode(boolean automaticMode) {
        boolean oldValue = mAutomaticMode;
        this.mAutomaticMode = automaticMode;
        pcs.firePropertyChange(PROP_AUTOMATIC_MODE, oldValue, mAutomaticMode);
    }

}
