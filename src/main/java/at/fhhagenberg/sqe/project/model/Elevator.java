package at.fhhagenberg.sqe.project.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an Elevator and all its Properties
 */
public class Elevator extends ListenableModel {

    /**
     * Property Name for the Button States
     */
    public static final String PROP_BUTTON = "button";
    /**
     * Property Name for the Service States
     */
    public static final String PROP_SERVICE = "service";
    /**
     * Property Name for the Current Floor
     */
    public static final String PROP_CURRENT_FLOOR = "currentFloor";
    /**
     * Property Name for the current Position
     */
    public static final String PROP_POSITION = "position";
    /**
     * Property Name for the Target
     */
    public static final String PROP_TARGET = "target";
    /**
     * Property Name for the Door Statue
     */
    public static final String PROP_DOOR_STATUS = "doorStatus";
    /**
     * Property Name for the current Speed
     */
    public static final String PROP_SPEED = "speed";
    /**
     * Property Name for the current Acceleration
     */
    public static final String PROP_ACCELERATION = "acceleration";
    /**
     * Property Name for the Capacity
     */
    public static final String PROP_CAPACITY = "capacity";
    /**
     * Property Name for the current Weight
     */
    public static final String PROP_WEIGHT = "weight";
    /**
     * Property Name for the Direction
     */
    public static final String PROP_DIRECTION = "direction";
    /**
     * Property Name for the current automatic mode state
     */
    public static final String PROP_AUTOMATIC_MODE = "automaticMode";

    /**
     * Represents the Committed Direction of an Elevator
     */
    public enum Direction {
        UP, DOWN, UNCOMMITTED
    }

    /**
     * Represents the Door Status of an Elevator
     */
    public enum DoorStatus {
        OPEN, CLOSED, OPENING, CLOSING
    }

    /**
     * The Elevator Number
     */
    private final int mElevatorNumber;
    /**
     * The human readable Description
     */
    private final String mDescription;
    /**
     * Flags that represent if this Elevator Services a specific Floor
     */
    private final Map<Floor, Boolean> mFloorServices;
    /**
     * Flags that represent if the Button to a Floor is pressed inside the Elevator
     */
    private final Map<Floor, Boolean> mFloorButtons;
    /**
     * A List of all Floors for this Elevator
     */
    private final List<Floor> mFloors;
    /**
     * The current Floor
     */
    private Floor mCurrentFloor;
    /**
     * The current Position from the Ground
     */
    private int mPosition;
    /**
     * The set Target
     */
    private Floor mTarget;
    /**
     * The Door Status
     */
    private DoorStatus mDoorStatus;
    /**
     * The current Speed
     */
    private int mSpeed;
    /**
     * The current Acceleration
     */
    private int mAcceleration;
    /**
     * The total Capacity
     */
    private int mCapacity;
    /**
     * The current Weight
     */
    private int mWeight;
    /**
     * The commited Direction
     */
    private Direction mDirection;
    /**
     * The state of the automatic mode, where true means active, and false means inactive
     */
    private boolean mAutomaticMode;

    /**
     * Creates a new Elevator.
     *
     * @param elevatorNumber The Elevator Number
     * @param description    The human readable Description
     * @param floors         All Floors for this Elevator (and usually in the Building)
     */
    public Elevator(int elevatorNumber, String description, Iterable<Floor> floors) {
        mElevatorNumber = elevatorNumber;
        mDescription = description;
        mFloors = new ArrayList<>();
        mFloorServices = new HashMap<>();
        mFloorButtons = new HashMap<>();
        mAutomaticMode = false; // set manual mode by default

        // initialize Button and Service States
        for (Floor f : floors) {
            mFloors.add(f);
            mFloorServices.put(f, false);
            mFloorButtons.put(f, false);
        }
    }

    /**
     * Get the Floors for this Elevator.
     *
     * @return The Floors for this Elevator
     */
    public Iterable<Floor> getFloors() {
        return mFloors;
    }

    /**
     * Get a Floor with the specified Floor Number.
     *
     * @param floorNumber The required Floor Number
     * @return The Floor with the specified Floor Number
     */
    public Floor getFloor(int floorNumber) {
        return mFloors.get(floorNumber);
    }

    /**
     * Get the Elevator Number.
     *
     * @return The Elevator Number
     */
    public int getElevatorNumber() {
        return mElevatorNumber;
    }

    /**
     * Gets the Human Readable Description of this Elevator.
     *
     * @return The Human Readable Description
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Get if this Elevator services a Floor.
     *
     * @param floor The Floor
     * @return true if this Elevator services that Floor, otherwise false
     */
    public boolean getService(Floor floor) {
        return mFloorServices.get(floor);
    }

    /**
     * Set if this Elevator services a Floor.
     *
     * @param floor   Floor to set the services Flag
     * @param service true if this Elevator should service the Floor, false otherwise
     */
    public void setService(Floor floor, boolean service) {
        // if the flag would be unchanged, exit to prevent an unnecessary copy of the Map
        if (mFloorServices.get(floor) == service) return;
        Map<Floor, Boolean> oldValue = new HashMap<>(mFloorServices);
        mFloorServices.replace(floor, service);
        pcs.firePropertyChange(PROP_SERVICE, oldValue, mFloorServices);
    }

    /**
     * Get if the Button for a Floor is pressed in this Elevator.
     *
     * @param floor The Floor
     * @return true if the Button for that Floor is pressed, false otherwise
     */
    public boolean getButton(Floor floor) {
        return mFloorButtons.get(floor);
    }

    /**
     * Set if the Button for a Floor is pressed in this Elevator.
     *
     * @param floor  The Floor
     * @param button true if the Button for that Floor is pressed, false otherwise
     */
    public void setButton(Floor floor, boolean button) {
        // if the flag would be unchanged, exit to prevent an unnecessary copy of the Map
        if (mFloorButtons.get(floor) == button) return;
        Map<Floor, Boolean> oldValue = new HashMap<>(mFloorButtons);
        mFloorButtons.replace(floor, button);
        pcs.firePropertyChange(PROP_BUTTON, oldValue, mFloorButtons);
    }

    /**
     * Get the current Floor.
     *
     * @return The current Floor
     */
    public Floor getCurrentFloor() {
        return mCurrentFloor;
    }

    /**
     * Set the current Floor.
     *
     * @param currentFloor The new current Floor
     */
    public void setCurrentFloor(Floor currentFloor) {
        Floor oldValue = mCurrentFloor;
        this.mCurrentFloor = currentFloor;
        pcs.firePropertyChange(PROP_CURRENT_FLOOR, oldValue, mCurrentFloor);
    }

    /**
     * Get the Position
     *
     * @return The Position
     */
    public int getPosition() {
        return mPosition;
    }

    /**
     * Set the Position.
     *
     * @param position The new Position
     */
    public void setPosition(int position) {
        int oldValue = mPosition;
        this.mPosition = position;
        pcs.firePropertyChange(PROP_POSITION, oldValue, mPosition);
    }

    /**
     * Get the current Target.
     *
     * @return The Target Floor
     */
    public Floor getTarget() {
        return mTarget;
    }

    /**
     * Set the new Target.
     *
     * @param target The new Target Floor
     */
    public void setTarget(Floor target) {
        Floor oldValue = mTarget;
        this.mTarget = target;
        pcs.firePropertyChange(PROP_TARGET, oldValue, mTarget);
    }

    /**
     * Get the Door Status.
     *
     * @return The Door Status
     */
    public DoorStatus getDoorStatus() {
        return mDoorStatus;
    }

    /**
     * Set the new Door Status
     *
     * @param doorStatus The new Door Status
     */
    public void setDoorStatus(DoorStatus doorStatus) {
        DoorStatus oldValue = mDoorStatus;
        this.mDoorStatus = doorStatus;
        pcs.firePropertyChange(PROP_DOOR_STATUS, oldValue, mDoorStatus);
    }

    /**
     * Get the Speed of this Elevator.
     *
     * @return The Speed.
     */
    public int getSpeed() {
        return mSpeed;
    }

    /**
     * Set the Speed of this Elevator.
     *
     * @param speed The new Speed
     */
    public void setSpeed(int speed) {
        int oldValue = mSpeed;
        this.mSpeed = speed;
        pcs.firePropertyChange(PROP_SPEED, oldValue, mSpeed);
    }

    /**
     * Get the Acceleration.
     *
     * @return The Acceleration
     */
    public int getAcceleration() {
        return mAcceleration;
    }

    /**
     * Set the new Acceleration.
     *
     * @param acceleration The new Acceleration
     */
    public void setAcceleration(int acceleration) {
        int oldValue = mAcceleration;
        this.mAcceleration = acceleration;
        pcs.firePropertyChange(PROP_ACCELERATION, oldValue, mAcceleration);
    }

    /**
     * Get the maximum Capacity.
     *
     * @return The maximum Capacity
     */
    public int getCapacity() {
        return mCapacity;
    }

    /**
     * Set the new maximum Capacity.
     *
     * @param capacity The new maximum Capacity
     */
    public void setCapacity(int capacity) {
        int oldValue = mCapacity;
        this.mCapacity = capacity;
        pcs.firePropertyChange(PROP_CAPACITY, oldValue, mCapacity);
    }

    /**
     * Get the current Weight.
     *
     * @return The current Weight
     */
    public int getWeight() {
        return mWeight;
    }

    /**
     * Set the current Weight.
     *
     * @param weight The new Weight.
     */
    public void setWeight(int weight) {
        int oldValue = mWeight;
        this.mWeight = weight;
        pcs.firePropertyChange(PROP_WEIGHT, oldValue, mWeight);
    }

    /**
     * Get the Committed Direction.
     *
     * @return The Committed Direction
     */
    public Direction getDirection() {
        return mDirection;
    }

    /**
     * Set the new Committed Direction.
     *
     * @param direction The new Committed Direction
     */
    public void setDirection(Direction direction) {
        Direction oldValue = mDirection;
        this.mDirection = direction;
        pcs.firePropertyChange(PROP_DIRECTION, oldValue, mDirection);
    }

    /**
     * Get if this Elevator is in Automatic Mode.
     *
     * @return true if it is in Automatic Mode, false otherwise
     */
    public boolean isAutomaticMode() {
        return mAutomaticMode;
    }

    /**
     * Set if this Elevator is in Automatic Mode.
     *
     * @param automaticMode true to set it to AutomaticMode, false otherwise
     */
    public void setAutomaticMode(boolean automaticMode) {
        boolean oldValue = mAutomaticMode;
        this.mAutomaticMode = automaticMode;
        pcs.firePropertyChange(PROP_AUTOMATIC_MODE, oldValue, mAutomaticMode);
    }

}
