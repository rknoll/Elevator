package at.fhhagenberg.sqe.project.ui;

import apple.dts.samplecode.osxadapter.OSXAdapter;
import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.ui.views.BuildingStatusView;
import at.fhhagenberg.sqe.project.ui.views.ElevatorDetailView;
import at.fhhagenberg.sqe.project.ui.views.ElevatorOverviewView;
import at.fhhagenberg.sqe.project.ui.views.listeners.IElevatorDetailSelectListener;
import at.fhhagenberg.sqe.project.ui.views.listeners.IElevatorOverviewSelectListener;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;

/**
 * The Window of the Elevator Program
 */
@org.springframework.stereotype.Component
public class ElevatorWindow extends JFrame implements IElevatorDetailSelectListener, IElevatorOverviewSelectListener, IElevatorWindow {
	private static final long serialVersionUID = -8670790568524405985L;

    /**
     * Checks if the OS is MAC.
     */
    private static boolean MAC_OS_X = (System.getProperty("os.name").toLowerCase().startsWith("mac os x"));

    /**
     * The Building
     */
    private final Building mBuilding;
    /**
     * Our Content Pane where the View will be shown
     */
    private final JComponent mContentPane;
    /**
     * The currently active View
     */
    private DynamicUIComponent mCurrentView;

    /**
     * Create a new Elevator Program Window for a Building.
     *
     * @param building The Building
     */
    @Autowired
    public ElevatorWindow(Building building) {
        // OSX needs a special Quit Handler to do a clean shutdown
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

    /**
     * Only for OSX and its Cmd-Q Close Shortcut and to Close the Application in a clean way.
     *
     * @return true if the Application should Quit now
     */
    public boolean onClose() {
        dispose();
        return false;
    }

    /**
     * Update the View.
     *
     * @param view The new View
     */
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
