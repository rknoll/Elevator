package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.listeners.IFloorStatusListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by rknoll on 17/12/14.
 */
public class FloorStatusComponent extends JComponent implements IFloorStatusListener {

    private Floor mFloor;
    private JCheckBox mButtonUp;
    private JCheckBox mButtonDown;

    public FloorStatusComponent(Building building, Floor floor) {
        mFloor = floor;

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;

        gc.anchor = GridBagConstraints.CENTER;
        gc.gridheight = 2;
        add(new JLabel(floor.getDescription()), gc);

        // -- Spalte 2 -----------------------------------------
        gc.anchor = GridBagConstraints.WEST;
        gc.gridheight = 1;
        gc.gridx = 1;
        mButtonUp = new JCheckBox("Up");
        add(mButtonUp, gc);
        gc.gridy = 1;
        mButtonDown = new JCheckBox("Down");
        add(mButtonDown, gc);
        mButtonDown.setFocusable(false);

        setPreferredSize(new Dimension(150, 60));

        building.addListener(this);
    }


    @Override
    public Floor getFloor() {
        return mFloor;
    }

    @Override
    public void update() {
        mButtonUp.setSelected(mFloor.isButtonUp());
        mButtonDown.setSelected(mFloor.isButtonDown());
    }
}
