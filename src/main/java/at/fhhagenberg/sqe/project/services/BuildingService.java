package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rknoll on 03/01/15.
 */
public class BuildingService implements IService {

    private IElevatorAdapter mAdapter;
    private Building mBuilding;

    private List<IService> mSubServices;

    private boolean mConnected;

    public BuildingService(IElevatorAdapter adapter, Building building) {
        mAdapter = adapter;
        mBuilding = building;
        mSubServices = new ArrayList<IService>();
    }

    @Override
    public void refresh() {
        try {
            if (!mConnected) refreshBaseInformation();
            for (IService service : mSubServices) {
                service.refresh();
            }
        } catch (ElevatorConnectionLostException e) {
            mConnected = false;
        }
    }

    private void refreshBaseInformation() throws ElevatorConnectionLostException {
        mSubServices.clear();

        int floorNum = mAdapter.getFloorNum();
        int elevatorNum = mAdapter.getElevatorNum();

        mBuilding.setNumberOfFloorsAndElevators(floorNum, elevatorNum);
        mBuilding.setHeight(mAdapter.getFloorHeight() * floorNum);

        for (Floor floor : mBuilding.getFloors()) {
            mSubServices.add(new FloorService(mAdapter, floor));
        }

        for (Elevator elevator : mBuilding.getElevators()) {
            mSubServices.add(new ElevatorService(mAdapter, elevator));
            mSubServices.add(new AutomaticModeService(mBuilding, elevator));
        }

        mConnected = true;
    }

}
