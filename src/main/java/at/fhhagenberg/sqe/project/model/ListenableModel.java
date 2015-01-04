package at.fhhagenberg.sqe.project.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rknoll on 04/01/15.
 */
public class ListenableModel {
    /* Support Class for Property Change Listeners */
    protected final PropertyChangeSupport pcs;

    private int mGlobalCount;
    private Map<String, Integer> mPropertyCounts;

    public ListenableModel() {
        pcs = new PropertyChangeSupport(this);
        mPropertyCounts = new HashMap<String, Integer>();
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(property, listener);
        updatePropertyCount(property);
    }

    public void removePropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(property, listener);
        updatePropertyCount(property);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
        updateGlobalCount();
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
        updateGlobalCount();
    }

    public int getPropertyChangeListenersCount(String property) {
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
