package at.fhhagenberg.sqe.project;

import at.fhhagenberg.sqe.project.configuration.ElevatorConfiguration;
import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.services.UpdateThread;
import at.fhhagenberg.sqe.project.services.model.BuildingService;
import at.fhhagenberg.sqe.project.ui.ElevatorWindow;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;

/**
 * Class that constructs all necessary Objects and Launches the Application Window
 */
public class ElevatorLauncher {
    public void run(String[] args) {
        // create the spring application context
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ElevatorConfiguration.class);
        context.registerShutdownHook();

        // Create the Base Data Model
        Building building = new Building();

        // Bind the Building Service
        BuildingService buildingService = context.getBean(BuildingService.class, building);

        // Start the Update Thread
        UpdateThread updateThread = new UpdateThread(buildingService, 100);
        updateThread.setDaemon(true);
        updateThread.start();

        // show the gui window
        SwingUtilities.invokeLater(() -> {
            ElevatorWindow window = new ElevatorWindow(building);
            window.setVisible(true);
        });
    }
}
