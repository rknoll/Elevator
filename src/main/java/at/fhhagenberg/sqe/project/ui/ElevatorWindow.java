package at.fhhagenberg.sqe.project.ui;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.ui.views.BuildingStatusView;
import at.fhhagenberg.sqe.project.ui.views.ElevatorDetailView;
import at.fhhagenberg.sqe.project.ui.views.ElevatorOverviewView;
import at.fhhagenberg.sqe.project.ui.views.listeners.IElevatorDetailSelectListener;
import at.fhhagenberg.sqe.project.ui.views.listeners.IElevatorOverviewSelectListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorWindow implements IElevatorDetailSelectListener, IElevatorOverviewSelectListener {
    private JFrame mFrame;
    private Building mBuilding;
    private DynamicUIComponent mCurrentView;
    private JComponent mContentPane;

    public ElevatorWindow(Building building) {
        mBuilding = building;

        mFrame = new JFrame("ElevatorControl");
        mFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mContentPane = new JPanel(new BorderLayout());
        mContentPane.add(new BuildingStatusView(mBuilding), BorderLayout.PAGE_END);

        mFrame.setContentPane(mContentPane);

        selectOverview();

        mFrame.setSize(800, 600);
        mFrame.setVisible(true);
    }

    private void setView(DynamicUIComponent view) {
        if (mCurrentView != null) {
            mCurrentView.unload();
            mContentPane.remove(mCurrentView);
        }
        mCurrentView = view;
        mContentPane.add(mCurrentView, BorderLayout.CENTER);
        mFrame.revalidate();
        mFrame.repaint();
    }

    @Override
    public void elevatorSelected(Elevator elevator) {
        mFrame.setTitle("ElevatorControl - Elevator Details");
        setView(new ElevatorDetailView(mBuilding, elevator, this));
    }

    @Override
    public void selectOverview() {
        mFrame.setTitle("ElevatorControl - Overview");
        setView(new ElevatorOverviewView(mBuilding, this));
    }
}
