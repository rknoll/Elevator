package at.fhhagenberg.sqe.project.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas Zoechbauer on 18/12/14.
 */
public class FloorTest {

	private Floor floor;
	
	@Before
	public void setUp() {
		floor = new Floor(3, "F0", "0");
	}

	@Test
	public void testButtonUp() {
		floor.setButtonUp(false);
		assertFalse(floor.isButtonUp());
		floor.setButtonUp(true);
		assertTrue(floor.isButtonUp());
	}

	@Test
	public void testButtonDown() {
		floor.setButtonDown(false);
		assertFalse(floor.isButtonDown());
		floor.setButtonDown(true);
		assertTrue(floor.isButtonDown());
	}

	@Test
	public void testGetFloorNumber() {
		assertEquals(3, floor.getFloorNumber());
	}

	@Test
	public void testGetDescription() {
		assertEquals("F0", floor.getDescription());
	}

	@Test
	public void testGetShortDescription() {
		assertEquals("0", floor.getShortDescription());
	}

	@Test
	public void testNotifications() {
		List<PropertyChangeEvent> upEvents = new ArrayList<>();
		List<PropertyChangeEvent> downEvents = new ArrayList<>();
		floor.addPropertyChangeListener(Floor.PROP_BUTTON_UP, upEvents::add);
		floor.addPropertyChangeListener(Floor.PROP_BUTTON_DOWN, downEvents::add);

		floor.setButtonUp(true);
		assertEquals(1, upEvents.size());
		assertEquals(0, downEvents.size());
		assertEquals(Floor.PROP_BUTTON_UP, upEvents.get(0).getPropertyName());
		assertEquals(true, upEvents.get(0).getNewValue());
		assertEquals(false, upEvents.get(0).getOldValue());

		floor.setButtonDown(true);
		assertEquals(1, upEvents.size());
		assertEquals(1, downEvents.size());
		assertEquals(Floor.PROP_BUTTON_DOWN, downEvents.get(0).getPropertyName());
		assertEquals(true, downEvents.get(0).getNewValue());
		assertEquals(false, downEvents.get(0).getOldValue());
	}
}
