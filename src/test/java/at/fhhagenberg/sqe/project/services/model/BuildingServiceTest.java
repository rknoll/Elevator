package at.fhhagenberg.sqe.project.services.model;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapterFactory;
import at.fhhagenberg.sqe.project.mocks.EmptyAdapter;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by rknoll on 06/01/15.
 */
@Configuration
@ComponentScan(value = {"at.fhhagenberg.sqe.project.services.model"})
public class BuildingServiceTest {
    private AnnotationConfigApplicationContext context;
    private MockElevatorAdapterFactory mockAdapter;

    private class MockAutomaticModeService implements IService {
        public MockAutomaticModeService(Building building, Elevator elevator) {
        }

        @Override
        public void refresh() throws ElevatorConnectionLostException {
        }
    }

    private class MockElevatorAdapterFactory implements IElevatorAdapterFactory {
        private boolean mHasConnection = true;

        public void setHasConnection(boolean hasConnection) {
            mHasConnection = hasConnection;
        }

        @Override
        public IElevatorAdapter create() {
            return mHasConnection ? new EmptyAdapter() : null;
        }
    }

    @Bean
    public IElevatorAdapterFactory getIElevatorAdapterFactory() {
        return new MockElevatorAdapterFactory();
    }

    @Bean
    public IAutomaticModeServiceFactory getIAutomaticModeServiceFactory() {
        return MockAutomaticModeService::new;
    }

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext(BuildingServiceTest.class);
        mockAdapter = (MockElevatorAdapterFactory) context.getBean(IElevatorAdapterFactory.class);
    }

    @After
    public void tearDown() throws Exception {
        context.close();
    }

    @Test
    public void testConnect() {
        // Create the Base Data Model
        Building building = new Building();

        // Bind the Building Service
        BuildingService buildingService = context.getBean(BuildingService.class, building);

        buildingService.refresh();

        assertTrue(building.isConnected());
    }

    @Test
    public void testNoConnection() {
        // Create the Base Data Model
        Building building = new Building();

        // Bind the Building Service
        BuildingService buildingService = context.getBean(BuildingService.class, building);

        mockAdapter.setHasConnection(false);

        buildingService.refresh();

        assertFalse(building.isConnected());
    }

    @Test
    public void testReconnect() {
        // Create the Base Data Model
        Building building = new Building();

        // Bind the Building Service
        BuildingService buildingService = context.getBean(BuildingService.class, building);

        mockAdapter.setHasConnection(false);

        buildingService.refresh();

        assertFalse(building.isConnected());

        mockAdapter.setHasConnection(true);

        buildingService.refresh();

        assertTrue(building.isConnected());
    }
}
