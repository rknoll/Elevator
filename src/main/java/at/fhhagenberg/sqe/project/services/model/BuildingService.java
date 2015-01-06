package at.fhhagenberg.sqe.project.services.model;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapterFactory;
import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.IService;
import at.fhhagenberg.sqe.project.services.automatic.BaseAutomaticModeService;
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

    private final Building mBuilding;
    private final List<IService> mSubServices;

    @Autowired
    private IElevatorAdapterFactory mAdapterFactory;

    @Autowired
    private IAutomaticModeServiceFactory mAutomaticModeFactory;

    public BuildingService(Building building) {
        mBuilding = building;
        mSubServices = new ArrayList<>();
    }

    @Override
    public void refresh() {
        try {
            if (!mBuilding.isConnected()) refreshBaseInformation();
            for (IService service : mSubServices) {
                service.refresh();
            }
        } catch (ElevatorConnectionLostException e) {
            mBuilding.setConnected(false);
        }
    }

    private void refreshBaseInformation() throws ElevatorConnectionLostException {
        IElevatorAdapter adapter = mAdapterFactory.create();
        if (adapter == null) throw new ElevatorConnectionLostException();

        mSubServices.clear();

        int floorNum = adapter.getFloorNum();
        int elevatorNum = adapter.getElevatorNum();

        mBuilding.setNumberOfFloorsAndElevators(floorNum, elevatorNum);
        mBuilding.setHeight(adapter.getFloorHeight() * floorNum);

        for (Floor floor : mBuilding.getFloors()) {
            mSubServices.add(new FloorService(adapter, floor));
        }

        for (Elevator elevator : mBuilding.getElevators()) {
            mSubServices.add(new ElevatorService(adapter, elevator));
            BaseAutomaticModeService automaticService = mAutomaticModeFactory.create(mBuilding, elevator);
            if (automaticService != null) mSubServices.add(automaticService);
        }

        mBuilding.setConnected(true);
    }

}
