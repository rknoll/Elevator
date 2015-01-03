package at.fhhagenberg.sqe.project.ui;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.ui.views.ElevatorDetailView;
import at.fhhagenberg.sqe.project.ui.views.ElevatorOverviewView;
import at.fhhagenberg.sqe.project.ui.views.listeners.IElevatorDetailSelectListener;
import at.fhhagenberg.sqe.project.ui.views.listeners.IElevatorOverviewSelectListener;

import javax.swing.*;

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

        mFrame.setSize(800, 600);
        //mFrame.pack();
        mFrame.setVisible(true);
    }

    @Override
    public void elevatorSelected(Elevator elevator) {
        //mBuilding.removeAllListeners();
        mFrame.setContentPane(new ElevatorDetailView(mBuilding, elevator, this));
        mFrame.revalidate();
        mFrame.repaint();
    }

    @Override
    public void selectOverview() {
        //mBuilding.removeAllListeners();
        mFrame.setContentPane(new ElevatorOverviewView(mBuilding, this));
        mFrame.revalidate();
        mFrame.repaint();
    }
}
