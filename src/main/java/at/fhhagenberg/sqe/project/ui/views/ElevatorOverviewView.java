package at.fhhagenberg.sqe.project.ui.views;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.services.IElevatorPositionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorOverviewView extends JComponent implements IElevatorPositionListener {

    private Building mBuilding;

    public ElevatorOverviewView(Building building, IElevatorDetailSelectListener selectListener) {
        mBuilding = building;

        setLayout(new FlowLayout());

        for (Elevator e : building.getElevators()) {
            JButton button = new JButton(e.getDescription());
            button.addActionListener(event -> {
                building.removeListener(this);
                selectListener.elevatorSelected(e);
            });
            add(button);
        }

        building.addListener(this);
    }

    @Override
    public void update() {

    }
}
