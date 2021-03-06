package at.fhhagenberg.sqe.project.ui.components;

import abbot.tester.ComponentTester;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import junit.extensions.abbot.ComponentTestFixture;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by rknoll on 07/01/15.
 */
public class ElevatorFloorComponentTest extends ComponentTestFixture {
    // TODO: finish ElevatorFloorComponent tests

    private ComponentTester tester;

    protected void setUp() {
        tester = new ComponentTester();
    }

    protected void tearDown() {
        tester = null;
    }

    public void testCreation() {
        Floor f = new Floor(0, "F1", "1");
        Elevator e = new Elevator(0, "E1", Arrays.asList(f));
        ElevatorFloorComponent component = new ElevatorFloorComponent(e, f);
        JPanel pane = new JPanel();
        pane.add(component);
        showFrame(pane);
    }
}
