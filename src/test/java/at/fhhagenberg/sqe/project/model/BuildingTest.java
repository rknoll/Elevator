package at.fhhagenberg.sqe.project.model;

import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Thomas Zoechbauer on 18/12/14.
 */
public class BuildingTest {

    private Building building;

    private static final int NUM_ELEVATORS = 2;
    private static final int NUM_FLOORS = 5;

    @Before
    public void setUp() {
        building = new Building();
        building.setNumberOfFloorsAndElevators(NUM_FLOORS, NUM_ELEVATORS);
    }

    @Test
    public void testNumberOfFloorsAndElevators() {
        assertEquals(NUM_FLOORS, building.getNumberOfFloors());
        assertEquals(NUM_ELEVATORS, building.getNumberOfElevators());
        building.setNumberOfFloorsAndElevators(10, 20);
        assertEquals(10, building.getNumberOfFloors());
        assertEquals(20, building.getNumberOfElevators());
        building.setNumberOfFloorsAndElevators(30, 40);
        assertEquals(30, building.getNumberOfFloors());
        assertEquals(40, building.getNumberOfElevators());
    }

    @Test
    public void testHeight() {
        assertEquals(0, building.getHeight());
        building.setHeight(100);
        assertEquals(100, building.getHeight());
        building.setHeight(20);
        assertEquals(20, building.getHeight());
    }

    @Test
    public void testConnected() {
        assertFalse(building.isConnected());
        building.setConnected(true);
        assertTrue(building.isConnected());
        building.setConnected(false);
        assertFalse(building.isConnected());
    }

    @Test
    public void testElevators() {
        Iterable<Elevator> elevatorIterable = building.getElevators();
        Iterator<Elevator> elevatorIterator = elevatorIterable.iterator();
        int elevatorCount = 0;

        while (elevatorIterator.hasNext()) {
            elevatorIterator.next();
            elevatorCount++;
        }

        assertEquals(NUM_ELEVATORS, elevatorCount);
    }

    @Test
    public void testFloors() {
        Iterable<Floor> floorIterable = building.getFloors();
        Iterator<Floor> floorIterator = floorIterable.iterator();
        int floorCount = 0;

        while (floorIterator.hasNext()) {
            floorIterator.next();
            floorCount++;
        }

        assertEquals(NUM_FLOORS, floorCount);
    }

    @Test
    public void testFloorDescription() {
        assertEquals("Floor 1", building.getFloorDescription(0));
        assertEquals("Floor 2", building.getFloorDescription(1));
    }

    @Test
    public void testElevatorDescription() {
        assertEquals("Elevator 1", building.getElevatorDescription(0));
        assertEquals("Elevator 2", building.getElevatorDescription(1));
    }

    @Test
    public void testNotifications() {
        List<PropertyChangeEvent> numElevatorsEvents = new ArrayList<>();
        List<PropertyChangeEvent> numFloorsEvents = new ArrayList<>();
        List<PropertyChangeEvent> heightEvents = new ArrayList<>();
        List<PropertyChangeEvent> connectedEvents = new ArrayList<>();
        building.addPropertyChangeListener(Building.PROP_NUMBER_OF_ELEVATORS, numElevatorsEvents::add);
        building.addPropertyChangeListener(Building.PROP_NUMBER_OF_FLOORS, numFloorsEvents::add);
        building.addPropertyChangeListener(Building.PROP_HEIGHT, heightEvents::add);
        building.addPropertyChangeListener(Building.PROP_CONNECTED, connectedEvents::add);

        building.setNumberOfFloorsAndElevators(2, 1);
        assertEquals(0, heightEvents.size());
        assertEquals(0, connectedEvents.size());
        assertEquals(1, numElevatorsEvents.size());
        assertEquals(1, numFloorsEvents.size());
        assertEquals(Building.PROP_NUMBER_OF_ELEVATORS, numElevatorsEvents.get(0).getPropertyName());
        assertEquals(Building.PROP_NUMBER_OF_FLOORS, numFloorsEvents.get(0).getPropertyName());
        assertEquals(1, numElevatorsEvents.get(0).getNewValue());
        assertEquals(2, numFloorsEvents.get(0).getNewValue());
        assertEquals(NUM_ELEVATORS, numElevatorsEvents.get(0).getOldValue());
        assertEquals(NUM_FLOORS, numFloorsEvents.get(0).getOldValue());

        building.setConnected(true);
        assertEquals(0, heightEvents.size());
        assertEquals(1, connectedEvents.size());
        assertEquals(1, numElevatorsEvents.size());
        assertEquals(1, numFloorsEvents.size());
        assertEquals(true, connectedEvents.get(0).getNewValue());
        assertEquals(false, connectedEvents.get(0).getOldValue());

        // same value again, no event should be triggered
        building.setConnected(true);
        assertEquals(0, heightEvents.size());
        assertEquals(1, connectedEvents.size());
        assertEquals(1, numElevatorsEvents.size());
        assertEquals(1, numFloorsEvents.size());

        building.setHeight(1);
        assertEquals(1, heightEvents.size());
        assertEquals(1, connectedEvents.size());
        assertEquals(1, numElevatorsEvents.size());
        assertEquals(1, numFloorsEvents.size());
        assertEquals(1, heightEvents.get(0).getNewValue());
        assertEquals(0, heightEvents.get(0).getOldValue());
    }

}
