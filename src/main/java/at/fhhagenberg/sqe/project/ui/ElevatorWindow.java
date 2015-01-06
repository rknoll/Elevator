package at.fhhagenberg.sqe.project.ui;

import apple.dts.samplecode.osxadapter.OSXAdapter;
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
 * The Window of the Elevator Program
 */
public class ElevatorWindow extends JFrame implements IElevatorDetailSelectListener, IElevatorOverviewSelectListener {
    private final Building mBuilding;
    private final JComponent mContentPane;

    private DynamicUIComponent mCurrentView;

    private static boolean MAC_OS_X = (System.getProperty("os.name").toLowerCase().startsWith("mac os x"));

    public ElevatorWindow(Building building) {
        if (MAC_OS_X) {
            try {
                OSXAdapter.setQuitHandler(this, getClass().getDeclaredMethod("onClose", (Class[]) null));
            } catch (NoSuchMethodException ignored) {
            }
        }

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        setTitle("ElevatorControl");

        mBuilding = building;
        mContentPane = new JPanel(new BorderLayout());
        mContentPane.add(new BuildingStatusView(mBuilding), BorderLayout.PAGE_END);

        setContentPane(mContentPane);

        selectOverview();

        setSize(800, 600);
    }

    // Only for OSX and its Cmd-Q Close Shortcut and to Close the Application in a clean way
    public boolean onClose() {
        dispose();
        return false;
    }

    private void setView(DynamicUIComponent view) {
        if (mCurrentView != null) {
            mCurrentView.unload();
            mContentPane.remove(mCurrentView);
        }
        mCurrentView = view;
        mContentPane.add(mCurrentView, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    @Override
    public void elevatorSelected(Elevator elevator) {
        setTitle("ElevatorControl - Elevator Details");
        setView(new ElevatorDetailView(mBuilding, elevator, this));
    }

    @Override
    public void selectOverview() {
        setTitle("ElevatorControl - Overview");
        setView(new ElevatorOverviewView(mBuilding, this));
    }
}
