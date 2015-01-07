package at.fhhagenberg.sqe.project.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A representation of a whole Building with Floors and Elevators
 */
public class Building extends ListenableModel {

    /**
     * Property Name for the NumberOfElevators
     */
    public static final String PROP_NUMBER_OF_ELEVATORS = "numberOfElevators";
    /**
     * Property Name for the NumberOfFloors
     */
    public static final String PROP_NUMBER_OF_FLOORS = "numberOfFloors";
    /**
     * Property Name for the Buildings Height
     */
    public static final String PROP_HEIGHT = "height";
    /**
     * Property Name for the Connection State
     */
    public static final String PROP_CONNECTED = "connected";

    /**
     * All Elevators in this Building
     */
    private final List<Elevator> mElevators;
    /**
     * All Floors in this Building
     */
    private final List<Floor> mFloors;
    /**
     * Building Height
     */
    private int mHeight;
    /**
     * Connection State
     */
    private boolean mConnected;

    /**
     * Creates a new Building.
     * Usually this Building will stay the same, even after loosing the Connection.
     * In that case, only its members will be recreated after restoring the Connection.
     */
    public Building() {
        mElevators = new ArrayList<>();
        mFloors = new ArrayList<>();
    }

    /**
     * Set the new Number of Floors and Elevators.
     * This will remove all current Elevators and Floors and makes them invalid.
     * All users of them should Listen to the property change events and use the new Objects.
     *
     * @param numberOfFloors    The number of Floors to be set
     * @param numberOfElevators The number of Elevators to be set
     */
    public void setNumberOfFloorsAndElevators(int numberOfFloors, int numberOfElevators) {
        int oldNumberOfFloors = mFloors.size();
        int oldNumberOfElevators = mElevators.size();

        mElevators.clear();
        mFloors.clear();
        for (int i = 0; i < numberOfFloors; ++i) {
            mFloors.add(new Floor(i, getFloorDescription(i), getFloorShortDescription(i)));
        }
        for (int i = 0; i < numberOfElevators; ++i) {
            mElevators.add(new Elevator(i, getElevatorDescription(i), mFloors));
        }

        pcs.firePropertyChange(PROP_NUMBER_OF_FLOORS, oldNumberOfFloors, numberOfFloors);
        pcs.firePropertyChange(PROP_NUMBER_OF_ELEVATORS, oldNumberOfElevators, numberOfElevators);
    }

    /**
     * Set the new Height of the Building.
     *
     * @param height The new Height to be set
     */
    public void setHeight(int height) {
        int oldValue = mHeight;
        mHeight = height;
        pcs.firePropertyChange(PROP_HEIGHT, oldValue, mHeight);
    }

    /**
     * Get the Description of a Floor.
     * This depends on the Building and is therefore created here.
     *
     * @param floorNumber The zero based floorNumber
     * @return A Human readable Representation of this Floor
     */
    protected String getFloorDescription(int floorNumber) {
        return "Floor " + (floorNumber + 1);
    }

    /**
     * Get the Short Description of a Floor.
     * This depends on the Building and is therefore created here.
     *
     * @param floorNumber The zero based floorNumber
     * @return A Human readable Short Representation of this Floor
     */
    protected String getFloorShortDescription(int floorNumber) {
        return "" + (floorNumber + 1);
    }

    /**
     * Get the Description of an Elevator.
     * This depends on the Building and is therefore created here.
     *
     * @param elevatorNumber The zero based elevatorNumber
     * @return A Human readable Representation of this Elevator
     */
    protected String getElevatorDescription(int elevatorNumber) {
        return "Elevator " + (elevatorNumber + 1);
    }

    /**
     * Get the Floors in this Building.
     *
     * @return The Floors in this Building
     */
    public Iterable<Floor> getFloors() {
        return mFloors;
    }

    /**
     * Get the Elevators in this Building.
     *
     * @return The Elevators in this Building
     */
    public Iterable<Elevator> getElevators() {
        return mElevators;
    }

    /**
     * Get the Number of Floors in this Building.
     *
     * @return The Number of Floors in this Building
     */
    public int getNumberOfFloors() {
        return mFloors.size();
    }

    /**
     * Get the Number of Elevators in this Building.
     *
     * @return The Number of Elevators in this Building
     */
    public int getNumberOfElevators() {
        return mElevators.size();
    }

    /**
     * Get the Height of this Building.
     *
     * @return The Height of this Building
     */
    public int getHeight() {
        return mHeight;
    }

    /**
     * Set the Connection State.
     *
     * @param connected The new Connection State, where true means Connected and false means Disconnected
     */
    public void setConnected(boolean connected) {
        boolean oldValue = mConnected;
        mConnected = connected;
        pcs.firePropertyChange(PROP_CONNECTED, oldValue, mConnected);
    }

    /**
     * Get the Connection State.
     *
     * @return The Connection State, where true means Connected and false means Disconnected
     */
    public boolean isConnected() {
        return mConnected;
    }

}
