package at.fhhagenberg.sqe.project.model;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.services.ElevatorInfoService;
import at.fhhagenberg.sqe.project.services.IElevatorInfoListener;
import at.fhhagenberg.sqe.project.services.IElevatorListener;
import at.fhhagenberg.sqe.project.services.IElevatorPositionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;

import javafx.beans.value.ChangeListener;

/**
 * Created by rknoll on 16/12/14.
 */
public class Building {

    private IElevatorAdapter mAdapter;

    private ElevatorInfoService mElevatorInfoService;
    
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
        	mNumberOfFloors = mAdapter.getElevatorNum();
        	mNumberOfElevators = mAdapter.getFloorNum();

            for (int i = 0; i < mNumberOfElevators; ++i) {
                mFloors.add(new Floor(i, "Flooor " + (i + 1)));
            }

            for (int i = 0; i < mNumberOfFloors; ++i) {
                mElevators.add(new Elevator(i, "Elevator " + (i + 1), mFloors));
                //mModeChangedListener.add(new ChangeListener<JCheckBox>());                
            }

        } catch (ElevatorConnectionLostException ignored) {
            // TODO: error handling :D
        }

        mElevatorInfoService = new ElevatorInfoService(mAdapter);
        mElevatorInfoService.start();
    }

    public Iterable<Elevator> getElevators() {
        return mElevators;
    }

    public Iterable<Floor> getFloors() {
        return mFloors;
    }

    public void addListener(IElevatorInfoListener listener) {
        mElevatorInfoService.addListener(listener);
    }

    public void addListener(IElevatorPositionListener listener) {

    }

    public void removeListener(IElevatorInfoListener listener) {
        mElevatorInfoService.removeListener(listener);
    }

    public void removeListener(IElevatorPositionListener listener) {

    }
    
    public int GetNumberOfElevators()
    {
    	return mNumberOfElevators;
    }
    
    public int GetNumberOfFloors()
    {
    	return mNumberOfFloors;
    }

}
