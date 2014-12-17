package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.listeners.IElevatorPositionListener;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by rknoll on 17/12/14.
 */
public class ElevatorFloorComponent extends JComponent {

    private Building mBuilding;

    public ElevatorFloorComponent(Building building, Elevator elevator, Floor floor) {
        mBuilding = building;

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 0;

        Component elevatorSetting = CreateComponentElevatorSettings(elevator, floor);
        add(elevatorSetting, gc);
    }

    private Component CreateComponentElevatorSettings(Elevator elevator, Floor floor)
    {
        JPanel pnlElevatorSettings = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;

        gc.anchor = GridBagConstraints.CENTER;

        JButton callButton = new JButton("Call");
        callButton.setName(elevator.getDescription() + " Call " + floor.getDescription());
        pnlElevatorSettings.add(callButton, gc);

        gc.gridy = 1;
        JCheckBox serveFloorCheckBox = new JCheckBox("Serve");
        serveFloorCheckBox.setName(elevator.getDescription() + " Serve " + floor.getDescription());
        serveFloorCheckBox.setSelected(elevator.getService(floor));

        // TODO: Add Listener!
        pnlElevatorSettings.add(serveFloorCheckBox, gc);

        return pnlElevatorSettings;
    }

}
