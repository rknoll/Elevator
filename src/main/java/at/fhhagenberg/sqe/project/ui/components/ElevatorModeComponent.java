package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.ui.views.listeners.IElevatorDetailSelectListener;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by rknoll on 17/12/14.
 */
public class ElevatorModeComponent extends JComponent implements PropertyChangeListener {

    private Elevator mElevator;
    private JCheckBox mAutomaticModeCheckBox;

    public ElevatorModeComponent(Elevator elevator, IElevatorDetailSelectListener listener) {
        mElevator = elevator;

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;
        gc.gridx = 0;

        JButton button = new JButton(elevator.getDescription());
        button.addActionListener(event -> {
            listener.elevatorSelected(elevator);
        });
        add(button, gc);

        gc.gridy += 1;

        mAutomaticModeCheckBox = new JCheckBox("automatic");
        mAutomaticModeCheckBox.setName(elevator.getDescription() + " AutomaticMode");
        mAutomaticModeCheckBox.setSelected(elevator.isAutomaticMode());

        mAutomaticModeCheckBox.addChangeListener(event -> {
            AbstractButton abstractButton = (AbstractButton)event.getSource();
            ButtonModel buttonModel = abstractButton.getModel();
            boolean selected = buttonModel.isSelected();

            if (selected != elevator.isAutomaticMode())
            {
                //System.out.println(abstractButton.getName() + " Changed: " + selected);
                elevator.setAutomaticMode(selected);
            }
        });

        add(mAutomaticModeCheckBox, gc);

        setPreferredSize(new Dimension(150, 60));

        mElevator.addPropertyChangeListener(Elevator.PROP_AUTOMATIC_MODE, this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        mAutomaticModeCheckBox.setSelected(mElevator.isAutomaticMode());
    }
}
