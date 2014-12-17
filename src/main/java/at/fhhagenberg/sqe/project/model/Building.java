package at.fhhagenberg.sqe.project.model;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.services.*;

import java.util.ArrayList;
import java.util.List;

import at.fhhagenberg.sqe.project.services.listeners.IElevatorListener;

/**
 * Created by rknoll on 16/12/14.
 */
public class Building {

    private IElevatorAdapter mAdapter;

    private List<ElevatorService> mElevatorServices;
    
    private int mNumberOfFloors;
    private int mNumberOfElevators;

    private List<Elevator> mElevators;
    private List<Floor> mFloors;
    
    //private List<ChangeListener<JCheckBox>> mModeChangedListener;
    

    public Building(IElevatorAdapter adapter) {
        mAdapter = adapter;

        mElevators = new ArrayList<Elevator>();
        mFloors = new ArrayList<Floor>();

        // TODO: do this in a new Thread
        try {
        	mNumberOfFloors = mAdapter.getFloorNum();
        	mNumberOfElevators = mAdapter.getElevatorNum();

            for (int i = 0; i < mNumberOfFloors; ++i) {
                mFloors.add(new Floor(i, "Flooor " + (i + 1)));
            }

            for (int i = 0; i < mNumberOfElevators; ++i) {
                mElevators.add(new Elevator(i, "Elevator " + (i + 1), mFloors));
                //mModeChangedListener.add(new ChangeListener<JCheckBox>());                
            }

        } catch (ElevatorConnectionLostException ignored) {
            // TODO: error handling :D
        }

        mElevatorServices = new ArrayList<ElevatorService>();

        mElevatorServices.add(new ElevatorInfoService(mAdapter));
        mElevatorServices.add(new FloorStatusService(mAdapter));
        mElevatorServices.add(new ElevatorPositionService(mAdapter));

        for (ElevatorService service : mElevatorServices) {
            service.start();
        }
    }

    public Iterable<Elevator> getElevators() {
        return mElevators;
    }

    public Iterable<Floor> getFloors() {
        return mFloors;
    }

    public void addListener(IElevatorListener listener) {
        for (ElevatorService service : mElevatorServices) {
            if (service.isCompatibleListener(listener)) {
                service.addListener(listener);
            }
        }
    }

    public void removeListener(IElevatorListener listener) {
        for (ElevatorService service : mElevatorServices) {
            if (service.isCompatibleListener(listener)) {
                service.removeListener(listener);
            }
        }
    }

    public void removeAllListeners() {
        for (ElevatorService service : mElevatorServices) {
            service.removeAllListeners();
        }
    }

    public int getNumberOfElevators()
    {
    	return mNumberOfElevators;
    }
    
    public int getNumberOfFloors()
    {
    	return mNumberOfFloors;
    }

}
