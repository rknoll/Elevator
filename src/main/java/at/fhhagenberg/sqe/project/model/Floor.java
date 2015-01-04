package at.fhhagenberg.sqe.project.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.beans.PropertyChangeSupport;

/**
 * A representation for a Floor
 */
public class Floor {
    /* Support Class for Property Change Listeners */
    private final PropertyChangeSupport pcs;

    /* All possible Properties */
    public static final String PROP_BUTTON_UP = "buttonUp";
    public static final String PROP_BUTTON_DOWN = "buttonDown";

    /* Constant Class Members */
    private final int mFloorNumber;
    private final String mDescription;

    /* Dynamic Members */
    private boolean mButtonUp;
    private boolean mButtonDown;

    /**
     * Constructor for a Floor, identified by it's Floor Number and a Human readable representation
     *
     * @param floorNumber The Floor Number
     * @param description The Human readable representation, e.g. "Lobby"
     */
    public Floor(int floorNumber, String description) {
        pcs = new PropertyChangeSupport(this);
        mFloorNumber = floorNumber;
        mDescription = description;
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

    public void setButtonUp(boolean buttonUp) {
        boolean oldValue = mButtonUp;
        mButtonUp = buttonUp;
        pcs.firePropertyChange(PROP_BUTTON_UP, oldValue, mButtonUp);
    }

    public void setButtonDown(boolean buttonDown) {
        boolean oldValue = mButtonDown;
        mButtonDown = buttonDown;
        pcs.firePropertyChange(PROP_BUTTON_DOWN, oldValue, mButtonDown);
    }

    public boolean isButtonUp() {
        return mButtonUp;
    }

    public boolean isButtonDown() {
        return mButtonDown;
    }

    public int getFloorNumber() {
        return mFloorNumber;
    }

    public String getDescription() {
        return mDescription;
    }
}
