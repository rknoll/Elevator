package at.fhhagenberg.sqe.project;

import at.fhhagenberg.sqe.project.services.IUpdateThread;
import at.fhhagenberg.sqe.project.ui.IElevatorWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;

import javax.swing.*;

/**
 * Class that constructs all necessary Objects and Launches the Application Window
 */
public class ElevatorLauncher implements IElevatorLauncher {
    /**
     * The Update Thread to Refresh the Service
     */
    @Autowired
    private IUpdateThread mUpdateThread;

    /**
     * The Window to show on Screen
     */
    @Autowired
    private IElevatorWindow mElevatorWindow;

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
    @Override
    public void run(String[] args) {
        mUpdateThread.setDaemon(true);
        mUpdateThread.start();

        // show the gui window
        SwingUtilities.invokeLater(() -> mElevatorWindow.setVisible(true));
    }
}
