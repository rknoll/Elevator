package at.fhhagenberg.sqe.project.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

/**
 * The Base Class for all Models that support Property Listeners
 */
public class ListenableModel {
    /**
     * Support Class for Property Change Listeners
     */
    protected final PropertyChangeSupport pcs;
    /**
     * Property Count Cache, to speed up Listener Count queries
     */
    private final Map<String, Integer> mPropertyCounts;
    /**
     * Global Listener Count, to speed up Listener Count queries
     */
    private int mGlobalCount;

    /**
     * Create a new Listenable Model
     */
    public ListenableModel() {
        pcs = new PropertyChangeSupport(this);
        mPropertyCounts = new HashMap<>();
    }

    /**
     * Add a new PropertyChangeListener for a specific Property
     *
     * @param property The Property
     * @param listener The Listener
     */
    public final void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(property, listener);
        updatePropertyCount(property);
    }

    /**
     * Remove a PropertyChangeListener for a specific Property
     *
     * @param property The Property
     * @param listener The Listener
     */
    public final void removePropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(property, listener);
        updatePropertyCount(property);
    }

    /**
     * Add a new PropertyChangeListener for all Properties
     *
     * @param listener The Listener
     */
    public final void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
        updateGlobalCount();
    }

    /**
     * Remove a PropertyChangeListener for all Properties
     *
     * @param listener The Listener
     */
    public final void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
        updateGlobalCount();
    }

    /**
     * Get the Number of Listeners for a specific Property, including Listeners for all Properties
     *
     * @param property The Property
     * @return The Number of Listeners for that Property
     */
    public final int getPropertyChangeListenersCount(String property) {
        return mPropertyCounts.getOrDefault(property, 0) + mGlobalCount;
    }

    /**
     * Update the Number of Global Listeners
     */
    private void updateGlobalCount() {
        mGlobalCount = 0;
        for (PropertyChangeListener listener : pcs.getPropertyChangeListeners()) {
            if (!(listener instanceof PropertyChangeListenerProxy)) ++mGlobalCount;
        }
    }

    /**
     * Update the Number of Listeners for a Property
     *
     * @param property The Property
     */
    private void updatePropertyCount(String property) {
        int propertyCount = 0;
        for (PropertyChangeListener listener : pcs.getPropertyChangeListeners()) {
            if (listener instanceof PropertyChangeListenerProxy) {
                PropertyChangeListenerProxy proxy = (PropertyChangeListenerProxy) listener;
                if (proxy.getPropertyName().equals(property)) ++propertyCount;
            }
        }
        if (propertyCount == 0) {
            mPropertyCounts.remove(property);
        } else {
            mPropertyCounts.put(property, propertyCount);
        }
    }

}
