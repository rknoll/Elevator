package at.fhhagenberg.sqe.project.services.model;

import at.fhhagenberg.sqe.mocks.EmptyAdapter;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapterFactory;
import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.services.automatic.IAutomaticModeServiceFactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

/**
 * Created by rknoll on 06/01/15.
 */
public class BuildingServiceTest {
    @InjectMocks
    private BuildingService service;

    @Spy
    private Building building;

    @Mock
    private IElevatorAdapterFactory adapterFactory;

    @Mock
    private IAutomaticModeServiceFactory autoModeFactory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(adapterFactory.create()).thenReturn(new EmptyAdapter());
    }

    @Test
    public void testConnect() {
        InOrder inOrder = inOrder(ignoreStubs(building));
        service.refresh();
        inOrder.verify(building).setConnected(true);
    }

    @Test
    public void testNoConnection() {
        InOrder inOrder = inOrder(ignoreStubs(building));
        when(adapterFactory.create()).thenReturn(null);
        service.refresh();
        inOrder.verify(building).setConnected(false);
    }

    @Test
    public void testReconnect() {
        InOrder inOrder = inOrder(ignoreStubs(building));
        when(adapterFactory.create()).thenReturn(null);
        service.refresh();
        when(adapterFactory.create()).thenReturn(new EmptyAdapter());
        service.refresh();
        inOrder.verify(building).setConnected(false);
        inOrder.verify(building).setConnected(true);
    }
}
