package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Elevator.Direction;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.ui.DynamicUIComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * GUI Component to show one Floor Element of an Elevator.
 * Contains a Button to Call the Elevator and shows if the Elevator services this Floor.
 */
public class ElevatorFloorComponent extends DynamicUIComponent implements PropertyChangeListener, ActionListener {

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
        setPreferredSize(new Dimension(90, 60));

        // property changed listeners
        mElevator.addPropertyChangeListener(Elevator.PROP_SERVICE, this);
        mElevator.addPropertyChangeListener(Elevator.PROP_AUTOMATIC_MODE, this);

        // action listeners
        mCallButton.addActionListener(this);
        mServeFloorCheckBox.addActionListener(this);
    }

    private Component CreateComponentElevatorSettings(Elevator elevator, Floor floor) {
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

        pnlElevatorSettings.add(mServeFloorCheckBox, gc);

        return pnlElevatorSettings;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mCallButton) {
            int targetFloor = mFloor.getFloorNumber();
            int currentFloor = mElevator.getCurrentFloor().getFloorNumber();

            // set direction
            if (targetFloor > currentFloor) {
                mElevator.setDirection(Direction.UP);
            } else if (targetFloor < currentFloor) {
                mElevator.setDirection(Direction.DOWN);
            } else {
                mElevator.setDirection(Direction.UNCOMMITTED);
            }

            // set new target
            mElevator.setTarget(mFloor);
        } else if (e.getSource() == mServeFloorCheckBox) {
            mElevator.setService(mFloor, mServeFloorCheckBox.isSelected());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Elevator.PROP_SERVICE:
                boolean newValue = mElevator.getService(mFloor);
                boolean oldValue = mServeFloorCheckBox.isSelected();
                if (newValue != oldValue) {
                    mServeFloorCheckBox.setSelected(newValue);
                }
                break;
            case Elevator.PROP_AUTOMATIC_MODE:
                mCallButton.setEnabled(!mElevator.isAutomaticMode());
                break;
        }
    }

    @Override
    public void unload() {
        mElevator.removePropertyChangeListener(Elevator.PROP_SERVICE, this);
        mElevator.removePropertyChangeListener(Elevator.PROP_AUTOMATIC_MODE, this);
    }
}
