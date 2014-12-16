package at.fhhagenberg.sqe.project.ui.views;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.services.IElevatorInfoListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorDetailView extends JComponent implements IElevatorInfoListener {

    private Building mBuilding;
    private Elevator mElevator;

    private JLabel mDescriptionLabel;

    public ElevatorDetailView(Building building, Elevator elevator, IElevatorOverviewSelectListener selectListener) {
        mBuilding = building;
        mElevator = elevator;

        setLayout(new FlowLayout());

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(event -> {
            building.removeListener(this);
            selectListener.selectOverview();
        });
        add(returnButton);

        mDescriptionLabel = new JLabel();
        add(mDescriptionLabel);

        building.addListener(this);
    }

    @Override
    public Elevator getElevator() {
        return mElevator;
    }

    @Override
    public void update() {
        mDescriptionLabel.setText(mElevator.getDescription());
    }
}
