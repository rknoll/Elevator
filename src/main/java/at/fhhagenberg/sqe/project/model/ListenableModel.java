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
    /* Support Class for Property Change Listeners */
    protected final PropertyChangeSupport pcs;

    /* Local Caches to Optimize getting the Count of registered Listeners */
    private final Map<String, Integer> mPropertyCounts;
    private int mGlobalCount;

    public ListenableModel() {
        pcs = new PropertyChangeSupport(this);
        mPropertyCounts = new HashMap<>();
    }

    public final void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(property, listener);
        updatePropertyCount(property);
    }

    public final void removePropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(property, listener);
        updatePropertyCount(property);
    }

    public final void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
        updateGlobalCount();
    }

    public final void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
        updateGlobalCount();
    }

    public final int getPropertyChangeListenersCount(String property) {
        return mPropertyCounts.getOrDefault(property, 0) + mGlobalCount;
    }

    private void updateGlobalCount() {
        mGlobalCount = 0;
        for (PropertyChangeListener listener : pcs.getPropertyChangeListeners()) {
            if (!(listener instanceof PropertyChangeListenerProxy)) ++mGlobalCount;
        }
    }

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
