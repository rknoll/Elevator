package at.fhhagenberg.sqe.project.services.model;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapterFactory;
import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.IService;
import at.fhhagenberg.sqe.project.services.automatic.IAutomaticModeServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Service to Refresh Building Values
 */
@Component
@Scope("prototype")
public class BuildingService implements IService {

    /**
     * The Building
     */
    @Autowired
    private Building mBuilding;
    /**
     * All Sub Services that need to be refreshed
     */
    private final List<IService> mSubServices;

    /**
     * The injected Elevator Adapter Factory to create Connections
     */
    @Autowired
    private IElevatorAdapterFactory mAdapterFactory;

    /**
     * The injected Automatic Mode Factory to create Automatic Modes
     */
    @Autowired
    private IAutomaticModeServiceFactory mAutomaticModeFactory;

    /**
     * Create a new Building Service to refresh a Building.
     */
    public BuildingService() {
        mSubServices = new ArrayList<>();
    }

    @Override
    public void refresh() {
        try {
            // if the Building is disconnected, try to connect
            if (!mBuilding.isConnected()) refreshBaseInformation();

            // refresh all sub services
            for (IService service : mSubServices) {
                service.refresh();
            }
        } catch (ElevatorConnectionLostException e) {
            // if any service reports a lost connection, reconnect
            mBuilding.setConnected(false);
        }
    }

    /**
     * Reconnect and get the Base Information of a Building.
     *
     * @throws ElevatorConnectionLostException
     */
    private void refreshBaseInformation() throws ElevatorConnectionLostException {
        IElevatorAdapter adapter = mAdapterFactory.create();
        if (adapter == null) throw new ElevatorConnectionLostException();

        mSubServices.clear();

        int floorNum = adapter.getFloorNum();
        int elevatorNum = adapter.getElevatorNum();

        mBuilding.setNumberOfFloorsAndElevators(floorNum, elevatorNum);
        mBuilding.setHeight(adapter.getFloorHeight() * floorNum);

        for (Floor floor : mBuilding.getFloors()) {
            // create a FloorService for each Floor
            mSubServices.add(new FloorService(adapter, floor));
        }

        for (Elevator elevator : mBuilding.getElevators()) {
            // create an Elevator Service for each Elevator
            mSubServices.add(new ElevatorService(adapter, elevator));
            // create an Automatic Mode Service for each Elevator
            IService automaticService = mAutomaticModeFactory.create(mBuilding, elevator);
            if (automaticService != null) mSubServices.add(automaticService);
        }

        // everything went well, we are connected!
        mBuilding.setConnected(true);
    }

}
