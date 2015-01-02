/**
 * @author Richard Knoll - S1310567017
 */

package at.fhhagenberg.sqe.project;

//import at.fhhagenberg.sqe.project.connection.DummyElevator;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.connection.RMIElevator;
import at.fhhagenberg.sqe.project.model.Building;
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
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            IElevator rmi = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
            IElevatorAdapter adapter = new RMIElevator(rmi);

            //IElevatorAdapter adapter = new DummyElevator();

            Building building = new Building(adapter);

            SwingUtilities.invokeLater(() -> new ElevatorWindow(building));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
