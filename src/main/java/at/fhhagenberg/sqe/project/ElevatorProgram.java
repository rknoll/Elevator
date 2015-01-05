/**
 * @author Richard Knoll - S1310567017
 */

package at.fhhagenberg.sqe.project;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.connection.RMIElevator;
import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.services.*;
import at.fhhagenberg.sqe.project.ui.ElevatorWindow;
import sqelevator.IElevator;

import javax.swing.*;
import java.rmi.Naming;

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
        // Create the Base Data Model
        Building building = new Building();

        // Bind the Building Service
        BuildingService buildingService = new BuildingService(building) {
            @Override
            protected IElevatorAdapter connect() throws ElevatorConnectionLostException {
                //return new DummyElevator();

                // Create the Remote Connection
                try {
                    IElevator rmi = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
                    return new RMIElevator(rmi);
                } catch (Exception e) {
                    throw new ElevatorConnectionLostException(e);
                }
            }

            @Override
            protected BaseAutomaticModeService getAutomaticService(Building building, Elevator elevator) {
                //return new SimpleAutomaticModeService(building, elevator);
                return new AdvancedAutomaticModeService(building, elevator);
            }
        };

        // Start the Update Thread
        UpdateThread updateThread = new UpdateThread(buildingService, 100);
        updateThread.start();

        // Show the GUI
        SwingUtilities.invokeLater(() -> new ElevatorWindow(building));
    }
}
