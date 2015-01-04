package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.ui.DynamicUIComponent;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * GUI Component to show the Status of all Buttons inside an Elevator
 */
public class ElevatorButtonsComponent extends DynamicUIComponent implements PropertyChangeListener {

    private Building mBuilding;
    private Elevator mElevator;

    private JLabel mElevatorButtons;

    public ElevatorButtonsComponent(Building building, Elevator elevator) {
        mBuilding = building;
        mElevator = elevator;

        setLayout(new BorderLayout());
        mElevatorButtons = new JLabel();
        add(mElevatorButtons);

        showDetails();

        mElevator.addPropertyChangeListener(Elevator.PROP_BUTTON, this);
    }

    @Override
    public void unload() {
        mElevator.removePropertyChangeListener(Elevator.PROP_BUTTON, this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        showDetails();
    }

    private void showDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("Pressed Buttons:<br>");
        for (Floor f : mBuilding.getFloors()) {
            if (mElevator.getButton(f)) {
                sb.append(f.getDescription());
                sb.append("<br>");
            }
        }
        sb.append("</html>");
        mElevatorButtons.setText(sb.toString());
    }
}
