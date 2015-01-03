package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Elevator.Direction;
import at.fhhagenberg.sqe.project.model.Floor;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by rknoll on 17/12/14.
 */
public class ElevatorFloorComponent extends JComponent implements PropertyChangeListener, ActionListener {

    private Elevator mElevator;
    private Floor mFloor;

    private JCheckBox mServeFloorCheckBox;
    private JButton mCallButton;

    public ElevatorFloorComponent(Elevator elevator, Floor floor) {
        mElevator = elevator;
        mFloor = floor;

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 0;

        Component elevatorSetting = CreateComponentElevatorSettings(elevator, floor);
        add(elevatorSetting, gc);
        setPreferredSize(new Dimension(120, 60));

        // property changed listeners
        mElevator.addPropertyChangeListener(Elevator.PROP_SERVICE, this);
        mElevator.addPropertyChangeListener(Elevator.PROP_DIRECTION, this);
        mElevator.addPropertyChangeListener(Elevator.PROP_TARGET, this);
        mElevator.addPropertyChangeListener(Elevator.PROP_AUTOMATIC_MODE, this);
        
    	// action listeners
        mCallButton.addActionListener(this);
        mServeFloorCheckBox.addActionListener(this);
    }

    private Component CreateComponentElevatorSettings(Elevator elevator, Floor floor)
    {
        JPanel pnlElevatorSettings = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;

        gc.anchor = GridBagConstraints.CENTER;

        mCallButton = new JButton("Call");
        mCallButton.setName(elevator.getDescription() + " Call " + floor.getDescription());
        pnlElevatorSettings.add(mCallButton, gc);

        gc.gridy = 1;
        mServeFloorCheckBox = new JCheckBox("Serve");
        mServeFloorCheckBox.setName(elevator.getDescription() + " Serve " + floor.getDescription());
        mServeFloorCheckBox.setSelected(elevator.getService(floor));
        
        mCallButton.setEnabled(!mElevator.isAutomaticMode());
    	mServeFloorCheckBox.setEnabled(!mElevator.isAutomaticMode());
    	
        pnlElevatorSettings.add(mServeFloorCheckBox, gc);

        return pnlElevatorSettings;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    { 
//    	System.out.println(e.getActionCommand());
    	
    	if (e.getSource() == mCallButton)
    	{
    		Floor targetFloor = mFloor;
    		Floor currentFloor =  mElevator.getCurrentFloor();
    		
    		
    		
    		// set new target
    		mElevator.setTarget(mFloor);
    		
    		// set direction
    		if (mFloor.getFloorNumber() > mElevator.getCurrentFloor().getFloorNumber())
            {
            	mElevator.setDirection(Direction.UP);
            }
            else if (mFloor.getFloorNumber() < mElevator.getCurrentFloor().getFloorNumber())
            {
            	mElevator.setDirection(Direction.DOWN);
            }
            else
            {
            	mElevator.setDirection(Direction.UNCOMMITTED);
            }
    		
    		System.out.print("current: " + currentFloor.getDescription() + " --> Target: " + targetFloor.getDescription());
    		System.out.println(" : " + mElevator.getDirection().name());
    	}
    	else if (e.getSource() == mServeFloorCheckBox)
    	{
    		mElevator.setService(mFloor, mServeFloorCheckBox.isSelected());
    	}        
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    	if (evt.getPropertyName() == Elevator.PROP_SERVICE)
    	{
    		mServeFloorCheckBox.setSelected(mElevator.getService(mFloor));
    	}
        
        if (evt.getPropertyName() == Elevator.PROP_AUTOMATIC_MODE) 
        {
        	mCallButton.setEnabled(!mElevator.isAutomaticMode());
        	mServeFloorCheckBox.setEnabled(!mElevator.isAutomaticMode());
        }
    }
}
