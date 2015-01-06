/**
 * @author Richard Knoll - S1310567017
 */

package at.fhhagenberg.sqe.project;

import at.fhhagenberg.sqe.project.configuration.ElevatorConfiguration;
import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.services.UpdateThread;
import at.fhhagenberg.sqe.project.services.model.BuildingService;
import at.fhhagenberg.sqe.project.ui.ElevatorWindow;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;

/**
 * Main Entry Class for the Elevator Project
 */
public class ElevatorProgram {

    /**
     * Main Entry function to the Elevator Project
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ElevatorConfiguration.class);

        // Create the Base Data Model
        Building building = new Building();

        // Bind the Building Service
        BuildingService buildingService = context.getBean(BuildingService.class, building);

        // Start the Update Thread
        UpdateThread updateThread = new UpdateThread(buildingService, 100);
        updateThread.setDaemon(true);
        updateThread.start();

        // Show the GUI
        try {
            SwingUtilities.invokeAndWait(() -> {
                ElevatorWindow window = new ElevatorWindow(building);
                window.setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        context.close();
    }
}
