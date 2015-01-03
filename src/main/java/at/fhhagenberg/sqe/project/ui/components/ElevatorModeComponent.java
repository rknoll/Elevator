package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.ui.IDynamicUIControl;
import at.fhhagenberg.sqe.project.ui.views.listeners.IElevatorDetailSelectListener;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by rknoll on 17/12/14.
 */
public class ElevatorModeComponent extends JComponent implements PropertyChangeListener, IDynamicUIControl {

    private Elevator mElevator;
    private AbstractButton mAutomaticModeToggleButton;

    public ElevatorModeComponent(Elevator elevator, IElevatorDetailSelectListener listener) {
        mElevator = elevator;

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.CENTER;

        setBackground(Color.black);

        JButton button = new JButton(elevator.getDescription());
        button.addActionListener(event -> listener.elevatorSelected(elevator));

        add(button, gc);

        gc.gridy += 1;

        mAutomaticModeToggleButton = new OnOffButtonComponent("Auto", "Manual");
        mAutomaticModeToggleButton.setSelected(elevator.isAutomaticMode());

        mAutomaticModeToggleButton.addChangeListener(event -> {
            boolean selected = mAutomaticModeToggleButton.isSelected();
            if (selected != elevator.isAutomaticMode()) {
                elevator.setAutomaticMode(selected);
            }
        });

        add(mAutomaticModeToggleButton, gc);

        setPreferredSize(new Dimension(120, 60));

        mElevator.addPropertyChangeListener(Elevator.PROP_AUTOMATIC_MODE, this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        mAutomaticModeToggleButton.setSelected(mElevator.isAutomaticMode());
    }

    @Override
    public void unload() {
        mElevator.removePropertyChangeListener(Elevator.PROP_AUTOMATIC_MODE, this);
    }
}
