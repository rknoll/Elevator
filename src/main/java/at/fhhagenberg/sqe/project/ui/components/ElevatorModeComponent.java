package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.ui.DynamicUIComponent;
import at.fhhagenberg.sqe.project.ui.views.listeners.IElevatorDetailSelectListener;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * GUI Component for an Elevator.
 * Shows the current Mode (automatic, manual) and a Button to Switch to the Detail View of this Elevator
 */
public class ElevatorModeComponent extends DynamicUIComponent implements PropertyChangeListener {
    private static final long serialVersionUID = 5416603250092029073L;

    /**
     * The Elevator
     */
    private final Elevator mElevator;
    /**
     * The Button Component to Switch the Automatic Mode
     */
    private final AbstractButton mAutomaticModeToggleButton;

    /**
     * Create a new ElevatorModeComponent for the specified Elevator and register a Listener to change to a Detail View.
     *
     * @param elevator The Elevator
     * @param listener The Listener to be called if the user wants to see the Detail View for the Elevator
     */
    public ElevatorModeComponent(Elevator elevator, IElevatorDetailSelectListener listener) {
        mElevator = elevator;

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.CENTER;

        JButton button = new JButton(mElevator.getDescription());

        // register the listener with the Button Click Event
        button.addActionListener(event -> listener.elevatorSelected(mElevator));

        add(button, gc);

        gc.gridy += 1;

        mAutomaticModeToggleButton = new OnOffButtonComponent("Auto", "Manual");

        updateView();

        mAutomaticModeToggleButton.addChangeListener(event -> mElevator.setAutomaticMode(mAutomaticModeToggleButton.isSelected()));

        add(mAutomaticModeToggleButton, gc);

        setPreferredSize(new Dimension(120, 60));

        mElevator.addPropertyChangeListener(Elevator.PROP_AUTOMATIC_MODE, this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateView();
    }

    /**
     * Update the State of the Automatic Mode Toggle Button
     */
    private void updateView() {
        mAutomaticModeToggleButton.setSelected(mElevator.isAutomaticMode());
    }

    @Override
    public void unload() {
        mElevator.removePropertyChangeListener(Elevator.PROP_AUTOMATIC_MODE, this);
    }
}
