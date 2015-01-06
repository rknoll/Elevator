package at.fhhagenberg.sqe.project.services.model;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapterFactory;
import at.fhhagenberg.sqe.project.connection.TestElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.services.IService;
import at.fhhagenberg.sqe.project.services.automatic.IAutomaticModeServiceFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.junit.Assert.*;

/**
 * Created by rknoll on 06/01/15.
 */
@Configuration
@ComponentScan(value = {"at.fhhagenberg.sqe.project.services.model"})
public class BuildingServiceTest {
    private AnnotationConfigApplicationContext context = null;

    private class MockAutomaticModeService implements IService {
        public MockAutomaticModeService(Building building, Elevator elevator) {
        }

        @Override
        public void refresh() throws ElevatorConnectionLostException {
        }
    }

    @Bean
    public IElevatorAdapterFactory getIElevatorAdapterFactory() {
        return TestElevatorAdapter::new;
    }

    @Bean
    public IAutomaticModeServiceFactory getIAutomaticModeServiceFactory() {
        return MockAutomaticModeService::new;
    }

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext(BuildingServiceTest.class);
    }

    @After
    public void tearDown() throws Exception {
        context.close();
    }

    @Test
    public void test() {
        // Create the Base Data Model
        Building building = new Building();

        // Bind the Building Service
        BuildingService buildingService = context.getBean(BuildingService.class, building);

        buildingService.refresh();

        assertTrue(building.isConnected());
    }
}
