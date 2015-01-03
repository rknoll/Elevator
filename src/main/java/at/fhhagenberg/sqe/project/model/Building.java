package at.fhhagenberg.sqe.project.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * A representation of a whole Building with Floors and Elevators
 */
public class Building {
    /* Support Class for Property Change Listeners */
    private final PropertyChangeSupport pcs;

    /* All possible Properties */
    public static final String PROP_NUMBER_OF_ELEVATORS = "numberOfElevators";
    public static final String PROP_NUMBER_OF_FLOORS = "numberOfFloors";
    public static final String PROP_HEIGHT = "height";
    public static final String PROP_CONNECTED = "connected";

    private int mNumberOfFloors;
    private int mNumberOfElevators;
    private int mHeight;
    private boolean mConnected;

    private List<Elevator> mElevators;
    private List<Floor> mFloors;

    public Building() {
        pcs = new PropertyChangeSupport(this);
        mElevators = new ArrayList<Elevator>();
        mFloors = new ArrayList<Floor>();
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(property, listener);
    }

    public void removePropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(property, listener);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public int getPropertyChangeListenersCount(String property) {
        int totalNumber = 0;
        for (PropertyChangeListener listener : pcs.getPropertyChangeListeners()) {
            if (listener instanceof PropertyChangeListenerProxy) {
                PropertyChangeListenerProxy proxy = (PropertyChangeListenerProxy) listener;
                if (proxy.getPropertyName().equals(property)) ++totalNumber;
            } else {
                ++totalNumber;
            }
        }
        return totalNumber;
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

    public int getNumberOfFloors()
    {
        return mNumberOfFloors;
    }

    public int getNumberOfElevators()
    {
    	return mNumberOfElevators;
    }

    public int getHeight()
    {
        return mHeight;
    }

    public void setConnected(boolean connected) {
        boolean oldValue = mConnected;
        mConnected = connected;
        pcs.firePropertyChange(PROP_CONNECTED, oldValue, mConnected);
    }

    public boolean isConnected()
    {
        return mConnected;
    }

}
