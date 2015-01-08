package at.fhhagenberg.sqe.project;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.services.UpdateThread;
import at.fhhagenberg.sqe.project.services.model.BuildingService;
import at.fhhagenberg.sqe.project.ui.ElevatorWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;

import javax.swing.*;

/**
 * Class that constructs all necessary Objects and Launches the Application Window
 */
public class ElevatorLauncher {

    /**
     * The Building
     */
    @Autowired
    private Building mBuilding;

    /**
     * The Building Service to Update the Building
     */
    @Autowired
    private BuildingService mBuildingService;

    /**
     * The Update Thread to Refresh the Service
     */
    @Autowired
    private UpdateThread mUpdateThread;

    /**
     * The Window to show on Screen
     */
    @Autowired
    private ElevatorWindow mElevatorWindow;

    /**
     * Create a new Launcher with the specified Application Context
     *
     * @param context The Application Context
     */
    public ElevatorLauncher(GenericApplicationContext context) {
        context.registerShutdownHook();
        AutowireCapableBeanFactory acbFactory = context.getAutowireCapableBeanFactory();
        acbFactory.autowireBean(this);
    }

    /**
     * Run the Elevator Application with the supplied Command Line Arguments.
     *
     * @param args Command Line Arguments
     */
    public void run(String[] args) {
        mUpdateThread.setDaemon(true);
        mUpdateThread.start();

        // show the gui window
        SwingUtilities.invokeLater(() -> mElevatorWindow.setVisible(true));
    }
}
