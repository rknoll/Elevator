package at.fhhagenberg.sqe.project.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A representation of a whole Building with Floors and Elevators
 */
public class Building extends ListenableModel {

    /* All possible Properties */
    public static final String PROP_NUMBER_OF_ELEVATORS = "numberOfElevators";
    public static final String PROP_NUMBER_OF_FLOORS = "numberOfFloors";
    public static final String PROP_HEIGHT = "height";
    public static final String PROP_CONNECTED = "connected";

    /* Constant Class Members */
    private final List<Elevator> mElevators;
    private final List<Floor> mFloors;

    /* Dynamic Members */
    private int mNumberOfFloors;
    private int mNumberOfElevators;
    private int mHeight;
    private boolean mConnected;

    public Building() {
        mElevators = new ArrayList<>();
        mFloors = new ArrayList<>();
    }

    public void setNumberOfFloorsAndElevators(int numberOfFloors, int numberOfElevators) {
        int oldNumberOfFloors = mNumberOfFloors;
        int oldNumberOfElevators = mNumberOfElevators;

        mNumberOfFloors = numberOfFloors;
        mNumberOfElevators = numberOfElevators;
        mElevators.clear();
        mFloors.clear();
        for (int i = 0; i < numberOfFloors; ++i) {
            mFloors.add(new Floor(i, getFloorDescription(i)));
        }
        for (int i = 0; i < numberOfElevators; ++i) {
            mElevators.add(new Elevator(i, getElevatorDescription(i), mFloors));
        }
        pcs.firePropertyChange(PROP_NUMBER_OF_FLOORS, oldNumberOfFloors, numberOfFloors);
        pcs.firePropertyChange(PROP_NUMBER_OF_ELEVATORS, oldNumberOfElevators, numberOfElevators);
    }

    public void setHeight(int height) {
        int oldValue = mHeight;
        mHeight = height;
        pcs.firePropertyChange(PROP_HEIGHT, oldValue, mHeight);
    }

    protected String getFloorDescription(int floorNumber) {
        return "Floor " + (floorNumber + 1);
    }

    protected String getElevatorDescription(int elevatorNumber) {
        return "Elevator " + (elevatorNumber + 1);
    }

    public Iterable<Floor> getFloors() {
        return mFloors;
    }

    public Iterable<Elevator> getElevators() {
        return mElevators;
    }

    public int getNumberOfFloors() {
        return mNumberOfFloors;
    }

    public int getNumberOfElevators() {
        return mNumberOfElevators;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setConnected(boolean connected) {
        boolean oldValue = mConnected;
        mConnected = connected;
        pcs.firePropertyChange(PROP_CONNECTED, oldValue, mConnected);
    }

    public boolean isConnected() {
        return mConnected;
    }

}
