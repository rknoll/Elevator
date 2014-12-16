package at.fhhagenberg.sqe.project.ui;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.services.IElevatorInfoListener;
import at.fhhagenberg.sqe.project.ui.views.ElevatorDetailView;
import at.fhhagenberg.sqe.project.ui.views.ElevatorOverviewView;
import at.fhhagenberg.sqe.project.ui.views.IElevatorDetailSelectListener;
import at.fhhagenberg.sqe.project.ui.views.IElevatorOverviewSelectListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorWindow implements IElevatorDetailSelectListener, IElevatorOverviewSelectListener {
    private JFrame mFrame;
    private Building mBuilding;

    public ElevatorWindow(Building building) {
        mBuilding = building;

        mFrame = new JFrame("ElevatorControl - Overview");
        mFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        selectOverview();

        //mFrame.setSize(300, 200);
        mFrame.pack();
        mFrame.setVisible(true);
    }

    @Override
    public void elevatorSelected(Elevator elevator) {
        mFrame.setContentPane(new ElevatorDetailView(mBuilding, elevator, this));
        mFrame.revalidate();
        mFrame.repaint();
    }

    @Override
    public void selectOverview() {
        mFrame.setContentPane(new ElevatorOverviewView(mBuilding, this));
        mFrame.revalidate();
        mFrame.repaint();
    }
}
