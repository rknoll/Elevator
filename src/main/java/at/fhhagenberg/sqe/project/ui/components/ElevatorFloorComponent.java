package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by rknoll on 17/12/14.
 */
public class ElevatorFloorComponent extends JComponent implements PropertyChangeListener {

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

        elevator.addPropertyChangeListener(Elevator.PROP_SERVICE, this);
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

        mCallButton.addActionListener(event -> mElevator.setTarget(floor));

        gc.gridy = 1;
        mServeFloorCheckBox = new JCheckBox("Serve");
        mServeFloorCheckBox.setName(elevator.getDescription() + " Serve " + floor.getDescription());
        mServeFloorCheckBox.setSelected(elevator.getService(floor));
        
        mCallButton.setEnabled(!mElevator.isAutomaticMode());
    	mServeFloorCheckBox.setEnabled(!mElevator.isAutomaticMode());

        mServeFloorCheckBox.addActionListener(event -> mElevator.setService(mFloor, mServeFloorCheckBox.isSelected()));
        mElevator.addPropertyChangeListener(this);

        pnlElevatorSettings.add(mServeFloorCheckBox, gc);

        return pnlElevatorSettings;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        mServeFloorCheckBox.setSelected(mElevator.getService(mFloor));
        
        if (evt.getPropertyName() == Elevator.PROP_AUTOMATIC_MODE) 
        {
        	mCallButton.setEnabled(!mElevator.isAutomaticMode());
        	mServeFloorCheckBox.setEnabled(!mElevator.isAutomaticMode());
        }
    }
}
