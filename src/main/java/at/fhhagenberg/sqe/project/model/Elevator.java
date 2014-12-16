package at.fhhagenberg.sqe.project.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rknoll on 15/12/14.
 */
public class Elevator {

    public enum Direction {
        UP, DOWN, UNCOMMITTED
    }

    private int mElevatorNumber;
    private String mDescription;

    private Floor mCurrentFloor;
    private int mPosition;
    private Floor mTarget;
    private boolean mDoorStatus;
    private int mSpeed;
    private int mAcceleration;
    private int mCapacity;
    private int mWeight;
    private Direction mDirection;

    private Map<Floor, Boolean> mFloorServices;
    private Map<Floor, Boolean> mFloorButtons;

    public Elevator(int elevatorNumber, String description, Iterable<Floor> floors) {
        mElevatorNumber = elevatorNumber;
        mDescription = description;
        mFloorServices = new HashMap<Floor, Boolean>();
        mFloorButtons = new HashMap<Floor, Boolean>();
        for (Floor f : floors) {
            mFloorServices.put(f, false);
            mFloorButtons.put(f, false);
        }
    }

    public void setService(Floor floor, boolean service) {
        mFloorServices.replace(floor, service);
    }

    public boolean getService(Floor floor) {
        return mFloorServices.get(floor);
    }

    public void setButton(Floor floor, boolean button) {
        mFloorButtons.replace(floor, button);
    }

    public boolean getButton(Floor floor) {
        return mFloorButtons.get(floor);
    }

    public int getElevatorNumber() {
        return mElevatorNumber;
    }

    public String getDescription() {
        return mDescription;
    }

    public Floor getCurrentFloor() {
        return mCurrentFloor;
    }

    public void setCurrentFloor(Floor currentFloor) {
        this.mCurrentFloor = currentFloor;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    public Floor getTarget() {
        return mTarget;
    }

    public void setTarget(Floor target) {
        this.mTarget = target;
    }

    public boolean getDoorStatus() {
        return mDoorStatus;
    }

    public void setDoorStatus(boolean doorStatus) {
        this.mDoorStatus = doorStatus;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public void setSpeed(int speed) {
        this.mSpeed = speed;
    }

    public int getAcceleration() {
        return mAcceleration;
    }

    public void setAcceleration(int acceleration) {
        this.mAcceleration = acceleration;
    }

    public int getCapacity() {
        return mCapacity;
    }

    public void setCapacity(int capacity) {
        this.mCapacity = capacity;
    }

    public int getWeight() {
        return mWeight;
    }

    public void setWeight(int weight) {
        this.mWeight = weight;
    }

    public Direction getDirection() {
        return mDirection;
    }

    public void setDirection(Direction direction) {
        this.mDirection = direction;
    }

}
