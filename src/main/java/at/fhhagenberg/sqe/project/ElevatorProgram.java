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
    private static ElevatorLauncher launcher = new ElevatorLauncher(new AnnotationConfigApplicationContext(ElevatorConfiguration.class));

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
    public static void setLauncher(ElevatorLauncher launcher) {
        ElevatorProgram.launcher = launcher;
    }
}
