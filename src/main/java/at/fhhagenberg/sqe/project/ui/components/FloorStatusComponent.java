package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.ui.DynamicUIComponent;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * GUI Component to show the current Buttons that are pressed in a Floor
 */
public class FloorStatusComponent extends DynamicUIComponent implements PropertyChangeListener {
    private static final long serialVersionUID = 6587336751713265244L;

    /**
     * The Floor
     */
    private final Floor mFloor;
    /**
     * State of the Buttons on the Floor
     */
    private final JLabel mLabelButtonState;
    /**
     * Icons for Up and Down Arrows
     */
    private final ImageIcon[][] mIcons; // [Down][Up]

    /**
     * Create a new FloorStatusComponent for the specified Floor.
     *
     * @param floor The Floor
     */
    public FloorStatusComponent(Floor floor) {
        mFloor = floor;

        // Get Icons from Resources
        mIcons = new ImageIcon[2][2]; // [Down][Up]
        mIcons[0][0] = new ImageIcon(this.getClass().getResource("Down0Up0.png"));
        mIcons[0][1] = new ImageIcon(this.getClass().getResource("Down0Up1.png"));
        mIcons[1][0] = new ImageIcon(this.getClass().getResource("Down1Up0.png"));
        mIcons[1][1] = new ImageIcon(this.getClass().getResource("Down1Up1.png"));

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // Column 1
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.WEST;
        add(new JLabel(floor.getDescription()), gc);

        // Column 2
        gc.anchor = GridBagConstraints.EAST;
        gc.gridx += 1;
        add(new JLabel("   ")); // spacer

        // Column 3
        gc.anchor = GridBagConstraints.EAST;
        gc.gridx += 1;
        mLabelButtonState = new JLabel();
        add(mLabelButtonState, gc);

        setPreferredSize(new Dimension(150, 60));

        refreshView();

        floor.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        refreshView();
    }

    /**
     * Update the Icons
     */
    private void refreshView() {
        int downIdx = mFloor.isButtonDown() ? 1 : 0;
        int upIdx = mFloor.isButtonUp() ? 1 : 0;
        mLabelButtonState.setIcon(mIcons[downIdx][upIdx]);
    }

    @Override
    public void unload() {
        mFloor.removePropertyChangeListener(this);
    }
}
