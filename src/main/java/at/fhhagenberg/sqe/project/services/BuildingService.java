package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rknoll on 03/01/15.
 */
public abstract class BuildingService implements IService {

    private Building mBuilding;

    private List<IService> mSubServices;

    public BuildingService(Building building) {
        mBuilding = building;
        mSubServices = new ArrayList<IService>();
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

    abstract protected IElevatorAdapter connect() throws ElevatorConnectionLostException;

    private void refreshBaseInformation() throws ElevatorConnectionLostException {
        IElevatorAdapter adapter = connect();

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
            mSubServices.add(new SimpleAutomaticModeService(mBuilding, elevator));
        }

        mBuilding.setConnected(true);
    }

}
