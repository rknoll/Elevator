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
public class ElevatorPositionComponent extends JComponent implements IElevatorPositionListener {

    private Building mBuilding;
    private Elevator mElevator;

    public ElevatorPositionComponent(Building building, Elevator elevator) {
        mBuilding = building;
        mElevator = elevator;

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 0;

        Component positionPanel = CreateComponentElevatorPosition();
        add(positionPanel, gc);

        building.addListener(this);
    }

    private Component CreateComponentElevatorPosition()
    {
        JPanel pnlFloorPosition = new JPanel();

        Random rand = new Random();
        // Java 'Color' class takes 3 floats, from 0 to 1.
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color randomColor = new Color(r, g, b);
        pnlFloorPosition.setBackground(randomColor);

        return pnlFloorPosition;
    }

    @Override
    public Elevator getElevator() {
        return mElevator;
    }

    @Override
    public void update() {

    }
}
