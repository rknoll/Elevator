package at.fhhagenberg.sqe.project.ui.components;

import abbot.tester.ComponentTester;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import junit.extensions.abbot.ComponentTestFixture;

import javax.swing.*;
import java.util.Arrays;

/**
 * Created by rknoll on 07/01/15.
 */
public class ElevatorModeComponentTest extends ComponentTestFixture {
    // TODO: finish ElevatorModeComponent tests

    private ComponentTester tester;

    protected void setUp() {
        tester = new ComponentTester();
    }

    protected void tearDown() {
        tester = null;
    }

    public void testCreation() {
        Floor f = new Floor(0, "F1", "1");
        Elevator elevator = new Elevator(0, "E1", Arrays.asList(f));
        final boolean[] hasClicked = new boolean[1];
        ElevatorModeComponent component = new ElevatorModeComponent(elevator, e -> {
            hasClicked[0] = true;
        });
        JPanel pane = new JPanel();
        pane.add(component);
        showFrame(pane);
    }
}
