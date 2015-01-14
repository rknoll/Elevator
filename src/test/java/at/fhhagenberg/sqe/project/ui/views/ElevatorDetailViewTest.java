package at.fhhagenberg.sqe.project.ui.views;

import abbot.finder.ComponentNotFoundException;
import abbot.finder.MultipleComponentsFoundException;
import abbot.finder.matchers.ClassMatcher;
import abbot.tester.ComponentTester;
import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.ui.views.listeners.IElevatorOverviewSelectListener;
import junit.extensions.abbot.ComponentTestFixture;

import javax.swing.*;

/**
 * Created by rknoll on 07/01/15.
 */
public class ElevatorDetailViewTest extends ComponentTestFixture implements IElevatorOverviewSelectListener {
    private ComponentTester tester;
    private boolean selected;
    private Elevator elevator;
    private ElevatorDetailView component;

    protected void setUp() {
        tester = new ComponentTester();
        Building b = new Building();
        b.setNumberOfFloorsAndElevators(1, 1);
        elevator = b.getElevators().iterator().next();
        component = new ElevatorDetailView(b, elevator, this);
        JPanel pane = new JPanel();
        pane.add(component);
        showFrame(pane);
    }

    protected void tearDown() {
        tester = null;
    }

    public void testListener() throws ComponentNotFoundException, MultipleComponentsFoundException {
        assertEquals(1, elevator.getPropertyChangeListenersCount(""));

        JLabel autoMode = (JLabel)getFinder().find(c -> c instanceof JLabel && ("Automatic".equals(((JLabel)c).getText()) || "Manual".equals(((JLabel)c).getText())));
        assertEquals("Manual", autoMode.getText());

        elevator.setAutomaticMode(true);

        autoMode = (JLabel)getFinder().find(c -> c instanceof JLabel && ("Automatic".equals(((JLabel)c).getText()) || "Manual".equals(((JLabel)c).getText())));
        assertEquals("Automatic", autoMode.getText());

        component.unload();
        assertEquals(0, elevator.getPropertyChangeListenersCount(""));
    }

    public void testSelect() throws ComponentNotFoundException, MultipleComponentsFoundException {
        JButton button = (JButton)getFinder().find(new ClassMatcher(JButton.class));
        tester.actionClick(button);
        assertTrue(selected);
    }

    @Override
    public void selectOverview() {
        assertFalse(selected);
        selected = true;
    }
}
