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

    private final Floor mFloor;
    private final JLabel mLabelButtonState;
    private final ImageIcon[][] mIcons;    // [Down][Up]

    public FloorStatusComponent(Floor floor) {
        mFloor = floor;

        // Create Icons
        mIcons = new ImageIcon[2][2];    // [Down][Up]
        mIcons[0][0] = new ImageIcon(this.getClass().getResource("Down0Up0.png"));
        mIcons[0][1] = new ImageIcon(this.getClass().getResource("Down0Up1.png"));
        mIcons[1][0] = new ImageIcon(this.getClass().getResource("Down1Up0.png"));
        mIcons[1][1] = new ImageIcon(this.getClass().getResource("Down1Up1.png"));

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // -- Spalte 1 -----------------------------------------
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.WEST;
        add(new JLabel(floor.getDescription()), gc);

        // -- Spalte 2 -----------------------------------------
        gc.anchor = GridBagConstraints.EAST;
        gc.gridx += 1;
        add(new JLabel("   "));    // spacer

        // -- Spalte 3 -----------------------------------------
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

    private void refreshView() {
        boolean buttonUp = mFloor.isButtonUp();
        boolean buttonDown = mFloor.isButtonDown();

        mLabelButtonState.setIcon(mIcons[(buttonDown ? 1 : 0)][(buttonUp ? 1 : 0)]);
    }

    @Override
    public void unload() {
        mFloor.removePropertyChangeListener(this);
    }
}
