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
    private IDynamicUIControl mCurrentPane;

    public ElevatorWindow(Building building) {
        mBuilding = building;

        mFrame = new JFrame("ElevatorControl");
        mFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        selectOverview();

        mFrame.setSize(800, 600);
        mFrame.setVisible(true);
    }

    @Override
    public void elevatorSelected(Elevator elevator) {
        if (mCurrentPane != null) mCurrentPane.unload();
        ElevatorDetailView view = new ElevatorDetailView(mBuilding, elevator, this);
        mCurrentPane = view;
        mFrame.setContentPane(view);
        mFrame.setTitle("ElevatorControl - Elevator Details");
        mFrame.revalidate();
        mFrame.repaint();
    }

    @Override
    public void selectOverview() {
        if (mCurrentPane != null) mCurrentPane.unload();
        ElevatorOverviewView view = new ElevatorOverviewView(mBuilding, this);
        mCurrentPane = view;
        mFrame.setContentPane(view);
        mFrame.setTitle("ElevatorControl - Overview");
        mFrame.revalidate();
        mFrame.repaint();
    }
}
