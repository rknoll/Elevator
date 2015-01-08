package at.fhhagenberg.sqe.project.ui.components;

import abbot.tester.ComponentTester;
import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import junit.extensions.abbot.ComponentTestFixture;

import javax.swing.*;
import java.util.Arrays;

/**
 * Created by rknoll on 07/01/15.
 */
public class ElevatorPositionComponentTest extends ComponentTestFixture {
    // TODO: finish ElevatorPositionComponent tests

    private ComponentTester tester;

    protected void setUp() {
        tester = new ComponentTester();
    }

    protected void tearDown() {
        tester = null;
    }

    public void testCreation() {
        Building b = new Building();
        b.setNumberOfFloorsAndElevators(1, 1);
        Elevator elevator = b.getElevators().iterator().next();
        ElevatorPositionComponent component = new ElevatorPositionComponent(b, elevator);
        JPanel pane = new JPanel();
        pane.add(component);
        showFrame(pane);
    }
}
