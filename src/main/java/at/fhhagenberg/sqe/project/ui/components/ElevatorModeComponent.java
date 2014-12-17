package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.ui.views.listeners.IElevatorDetailSelectListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by rknoll on 17/12/14.
 */
public class ElevatorModeComponent extends JComponent {
    public ElevatorModeComponent(Elevator elevator, IElevatorDetailSelectListener listener) {
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

        JCheckBox checkBox = new JCheckBox("automatic");
        checkBox.setName(elevator.getDescription() + " AutomaticMode");
        checkBox.setSelected(elevator.isAutomaticMode());

        checkBox.addChangeListener(event -> {
            AbstractButton abstractButton = (AbstractButton)event.getSource();
            ButtonModel buttonModel = abstractButton.getModel();
            boolean selected = buttonModel.isSelected();

            if (selected != elevator.isAutomaticMode())
            {
                System.out.println(abstractButton.getName() + " Changed: " + selected);
                elevator.setAutomaticMode(selected);
            }
        });

        // TODO: Der Listener funktioniert so nicht.
        // 		 Im Building muss es f�r jeden Elevator einen ModeChangedListener geben, das dieser Funktion �bergeben wird.

        add(checkBox, gc);
    }
}
