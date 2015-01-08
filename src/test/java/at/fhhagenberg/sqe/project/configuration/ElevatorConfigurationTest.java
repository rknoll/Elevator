package at.fhhagenberg.sqe.project.configuration;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapterFactory;
import at.fhhagenberg.sqe.project.connection.rmi.RMIElevator;
import at.fhhagenberg.sqe.project.connection.rmi.RMIElevatorFactory;
import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.IService;
import at.fhhagenberg.sqe.project.services.automatic.IAutomaticModeServiceFactory;
import at.fhhagenberg.sqe.project.services.automatic.advanced.AdvancedAutomaticModeService;

/**
 * Created by rknoll on 07/01/15.
 * Modified by tzoechbauer on 08/01/15
 */
public class ElevatorConfigurationTest 
{
    private ElevatorConfiguration mElevatorConfig;
    
    @Before
    public void setUp()
    {
    	mElevatorConfig = new ElevatorConfiguration();
    }
    
    @Test
    public void testGetIElevatorAdapterFactory()
    {
    	IElevatorAdapterFactory adapterFactory = mElevatorConfig.getIElevatorAdapterFactory();
    	assertEquals(RMIElevatorFactory.class, adapterFactory.getClass());
    	
    	IElevatorAdapter elevatorAdapter = adapterFactory.create();
    	
    	try
    	{
    		// connection established
        	assertEquals(RMIElevator.class, elevatorAdapter.getClass());
    	}
    	catch (NullPointerException e)
    	{
    		// connection not established
    		assertEquals(null, elevatorAdapter);
    	}
    }
    
    @Test
    public void testGetIAutomaticModeServiceFactory()
    {
    	IAutomaticModeServiceFactory automaticModeServiceFactory = mElevatorConfig.getIAutomaticModeServiceFactory();
    	
    	Building building = new Building();
    	List<Floor> floors = new ArrayList<Floor>();
    	floors.add(new Floor(0, "Floor 0", "0"));
    	Elevator elevator = new Elevator(3, "Elevator 3", floors);
    	IService service = automaticModeServiceFactory.create(building, elevator);
    	assertEquals(AdvancedAutomaticModeService.class, service.getClass());
    }
}
