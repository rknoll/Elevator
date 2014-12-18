package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.listeners.IElevatorPositionListener;
import at.fhhagenberg.sqe.project.services.listeners.IElevatorServicesFloorListener;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by rknoll on 17/12/14.
 */
public class ElevatorFloorComponent extends JComponent implements IElevatorServicesFloorListener {

    private Building mBuilding;
    private Elevator mElevator;
    private Floor mFloor;

    private JCheckBox mServeFloorCheckBox;

    public ElevatorFloorComponent(Building building, Elevator elevator, Floor floor) {
        mBuilding = building;
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

        mBuilding.addListener(this);
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

        callButton.addActionListener(event -> {
            mBuilding.callElevator(elevator, floor);
        });

        gc.gridy = 1;
        mServeFloorCheckBox = new JCheckBox("Serve");
        mServeFloorCheckBox.setName(elevator.getDescription() + " Serve " + floor.getDescription());
        mServeFloorCheckBox.setSelected(elevator.getService(floor));

        mServeFloorCheckBox.addActionListener(event -> {
            mBuilding.setServicesFloor(mElevator, mFloor, mServeFloorCheckBox.isSelected());
        });

        pnlElevatorSettings.add(mServeFloorCheckBox, gc);

        return pnlElevatorSettings;
    }

    @Override
    public Elevator getElevator() {
        return mElevator;
    }

    @Override
    public Floor getFloor() {
        return mFloor;
    }

    @Override
    public void update() {
        mServeFloorCheckBox.setSelected(mElevator.getService(mFloor));
    }
}
