package at.fhhagenberg.sqe.project.ui.components;

import abbot.finder.ComponentNotFoundException;
import abbot.finder.MultipleComponentsFoundException;
import abbot.finder.matchers.ClassMatcher;
import abbot.tester.ComponentTester;
import at.fhhagenberg.sqe.project.model.Building;
import junit.extensions.abbot.ComponentTestFixture;
import junit.framework.TestCase;

import javax.swing.*;
import java.awt.*;

/**
 * Created by rknoll on 07/01/15.
 */
public class ElevatorButtonsComponentTest extends TestCase {
    // TODO: finish ElevatorButtonsComponent tests

    private ElevatorButtonsComponent create(int numberOfFloors) {
        Building b = new Building();
        b.setNumberOfFloorsAndElevators(numberOfFloors, 1);
        return new ElevatorButtonsComponent(b, b.getElevators().iterator().next());
    }

    public void testLayout3() throws ComponentNotFoundException, MultipleComponentsFoundException {
        ElevatorButtonsComponent component = create(3);

        GridLayout layout = (GridLayout)component.getLayout();
        assertEquals(2, layout.getRows());
        assertEquals(2, layout.getColumns());
    }

    public void testLayout5() throws ComponentNotFoundException, MultipleComponentsFoundException {
        ElevatorButtonsComponent component = create(5);

        GridLayout layout = (GridLayout)component.getLayout();
        assertEquals(3, layout.getRows());
        assertEquals(2, layout.getColumns());
    }

    public void testLayout10() throws ComponentNotFoundException, MultipleComponentsFoundException {
        ElevatorButtonsComponent component = create(10);

        GridLayout layout = (GridLayout)component.getLayout();
        assertEquals(4, layout.getRows());
        assertEquals(3, layout.getColumns());
    }

    private String captionOf(int componentNumber, ElevatorButtonsComponent component) {
        return ((ElevatorButtonComponent)component.getComponent(componentNumber)).getText();
    }

    public void testLocations10() throws ComponentNotFoundException, MultipleComponentsFoundException {
        ElevatorButtonsComponent component = create(10);

        assertEquals("8", captionOf(0, component));
        assertEquals("9", captionOf(1, component));
        assertEquals("10", captionOf(2, component));
        assertEquals("5", captionOf(3, component));
        assertEquals("6", captionOf(4, component));
        assertEquals("7", captionOf(5, component));
        assertEquals("2", captionOf(6, component));
        assertEquals("3", captionOf(7, component));
        assertEquals("4", captionOf(8, component));
        assertEquals("1", captionOf(11, component));
    }
}
