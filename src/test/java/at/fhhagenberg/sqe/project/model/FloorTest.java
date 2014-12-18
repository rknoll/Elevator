package at.fhhagenberg.sqe.project.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Thomas Zöchbauer on 18/12/14.
 */
public class FloorTest {

	private Floor floor;
	
	@Before
	public void setUp() throws Exception {
		floor = new Floor(3, "Floor 3");
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
		assertEquals("Floor 3", floor.getDescription());
	}

}
