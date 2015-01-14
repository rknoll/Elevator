/**
 * @author Richard Knoll - S1310567017
 */

package at.fhhagenberg.sqe.project;

import at.fhhagenberg.sqe.project.configuration.ElevatorConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main Entry Class for the Elevator Project
 */
public class ElevatorProgram {

    /**
     * The Launcher used to start the Application
     */
    private static IElevatorLauncher launcher = new ElevatorLauncher(new AnnotationConfigApplicationContext(ElevatorConfiguration.class));

    /**
     * Creates an Elevator Program.
     */
    public ElevatorProgram() {
    }

    /**
     * Main Entry function to the Elevator Project
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launcher.run(args);
    }

    /**
     * Set the Launcher to use to start the Application.
     * Mainly used for testing.
     *
     * @param launcher The new Launcher
     */
    public void setLauncher(IElevatorLauncher launcher) {
        ElevatorProgram.launcher = launcher;
    }

    /**
     * Get the launcher currently used to start the Application
     * Mainly used for testing.
     *
     * @return The current Launcher
     */
    public IElevatorLauncher getLauncher() {
        return ElevatorProgram.launcher;
    }
}
