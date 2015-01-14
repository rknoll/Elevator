package at.fhhagenberg.sqe.project.model;

import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by rknoll on 05/01/15.
 */
public class ListenableModelTest {

    private ListenableModelMock model;

    private class ListenableModelMock extends ListenableModel {
        public void fire(String property) {
            pcs.firePropertyChange(property, null, null);
        }
    }

    @Before
    public void setUp() {
        model = new ListenableModelMock();
    }

    @Test
    public void testAddGlobalListener() {
        model.addPropertyChangeListener(evt -> {
        });
        assertEquals(1, model.getPropertyChangeListenersCount(""));
    }

    @Test
    public void testRemoveGlobalListener() {
        PropertyChangeListener listener = evt -> {
        };
        model.addPropertyChangeListener(listener);
        assertEquals(1, model.getPropertyChangeListenersCount(""));
        model.addPropertyChangeListener(listener);
        assertEquals(2, model.getPropertyChangeListenersCount(""));
        model.removePropertyChangeListener(listener);
        assertEquals(1, model.getPropertyChangeListenersCount(""));
        model.removePropertyChangeListener(listener);
        assertEquals(0, model.getPropertyChangeListenersCount(""));
    }

    @Test
    public void testFireGlobalListener() {
        List<PropertyChangeEvent> events = new ArrayList<>();
        model.addPropertyChangeListener(events::add);
        model.fire("property");
        assertEquals(1, events.size());
        assertEquals("property", events.get(0).getPropertyName());
    }

    @Test
    public void testAddSpecificListener() {
        model.addPropertyChangeListener("property", evt -> {
        });
        assertEquals(0, model.getPropertyChangeListenersCount(""));
        assertEquals(1, model.getPropertyChangeListenersCount("property"));
    }

    @Test
    public void testRemoveSpecificListener() {
        PropertyChangeListener listener = evt -> {
        };
        model.addPropertyChangeListener("property", listener);
        assertEquals(1, model.getPropertyChangeListenersCount("property"));
        model.addPropertyChangeListener("property", listener);
        assertEquals(2, model.getPropertyChangeListenersCount("property"));
        model.removePropertyChangeListener("property", listener);
        assertEquals(1, model.getPropertyChangeListenersCount("property"));
        model.removePropertyChangeListener("property", listener);
        assertEquals(0, model.getPropertyChangeListenersCount("property"));
    }

    @Test
    public void testFireSpecificListener() {
        List<PropertyChangeEvent> events = new ArrayList<>();
        model.addPropertyChangeListener("property", events::add);
        model.fire("");
        assertEquals(0, events.size());
        model.fire("property");
        assertEquals(1, events.size());
        assertEquals("property", events.get(0).getPropertyName());
    }

    @Test
    public void testMixedListeners() {
        List<PropertyChangeEvent> specificEvents1 = new ArrayList<>();
        List<PropertyChangeEvent> specificEvents2 = new ArrayList<>();
        List<PropertyChangeEvent> globalEvents = new ArrayList<>();
        model.addPropertyChangeListener("property1", specificEvents1::add);
        model.addPropertyChangeListener(globalEvents::add);
        model.addPropertyChangeListener("property2", specificEvents2::add);
        assertEquals(1, model.getPropertyChangeListenersCount(""));
        assertEquals(2, model.getPropertyChangeListenersCount("property1"));
        assertEquals(2, model.getPropertyChangeListenersCount("property2"));
        model.fire("");
        assertEquals(1, globalEvents.size());
        assertEquals(0, specificEvents1.size());
        assertEquals(0, specificEvents2.size());
        model.fire("property1");
        assertEquals(2, globalEvents.size());
        assertEquals(1, specificEvents1.size());
        assertEquals(0, specificEvents2.size());
        model.fire("property2");
        assertEquals(3, globalEvents.size());
        assertEquals(1, specificEvents1.size());
        assertEquals(1, specificEvents2.size());
    }

}
