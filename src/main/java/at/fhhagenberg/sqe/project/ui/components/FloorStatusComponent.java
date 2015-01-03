package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Floor;

import javax.swing.*;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by rknoll on 17/12/14.
 */
public class FloorStatusComponent extends JComponent implements PropertyChangeListener {

    private Floor mFloor;    
    private JLabel mLabelButtonState;
    
    private ImageIcon[][] mIcons;	// [Down][Up]

    public FloorStatusComponent(Floor floor) {
        mFloor = floor;
        
        // Create Icons 
        mIcons = new ImageIcon[2][2];	// [Down][Up]
        mIcons[0][0] = new ImageIcon("img/Down0Up0.png", "IconDown0Up0");
        mIcons[0][1] = new ImageIcon("img/Down0Up1.png", "IconDown0Up1");
        mIcons[1][0] = new ImageIcon("img/Down1Up0.png", "IconDown1Up0");
        mIcons[1][1] = new ImageIcon("img/Down1Up1.png", "IconDown1Up1");

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
        add(new JLabel("   "));	// spacer

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
}
