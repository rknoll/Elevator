package at.fhhagenberg.sqe.project.model;

import static org.junit.Assert.*;

import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;

import at.fhhagenberg.sqe.project.connection.DummyAdapter;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.services.listeners.IElevatorListener;

/**
 * Created by Thomas Z�chbauer on 18/12/14.
 */
public class BuildingTest {
	
	private Building building;
	
	@Before
	public void setUp() throws Exception {
		int numElevators = 2;
		int numFloors = 5;
		IElevatorAdapter adapter = new DummyAdapter(numElevators, numFloors);	
		
		building = new Building(adapter);
	}

	@Test
	public void testGetElevators() {
		Iterable<Elevator> elevatorIterable = building.getElevators();
		Iterator<Elevator> elevatorIter = elevatorIterable.iterator();
		
		int elevCount = 0;
		while (elevatorIter.hasNext())
		{
			Elevator e = elevatorIter.next();
			elevCount++;
		}
		assertEquals(2, elevCount);		
	}

	@Test
	public void testGetFloors() {
		Iterable<Floor> floorIterable = building.getFloors();
		Iterator<Floor> floorIter = floorIterable.iterator();
		
		int floorCount = 0;
		while (floorIter.hasNext())
		{
			Floor f = floorIter.next();
			floorCount++;
		}
		assertEquals(5, floorCount);	
	}

	@Test
	public void testAddAndRemoveListener() {
		IElevatorListener listener = new IElevatorListener() {
			@Override
			public void update() {
				// do nothing
			}
		};
		building.addListener(listener);
		building.removeListener(listener);
	}

	@Test
	public void testRemoveAllListeners() {
		IElevatorListener listener = new IElevatorListener() {
			@Override
			public void update() {
				// do nothing
			}
		};
		IElevatorListener listener2 = new IElevatorListener() {
			@Override
			public void update() {
				// do nothing
			}
		};
		
		building.addListener(listener);
		building.addListener(listener2);
		building.removeAllListeners();
	}

	@Test
	public void testGetNumberOfElevators() {
		assertEquals(2, building.getNumberOfElevators());
	}

	@Test
	public void testGetNumberOfFloors() {
		assertEquals(5, building.getNumberOfFloors());
	}

}
