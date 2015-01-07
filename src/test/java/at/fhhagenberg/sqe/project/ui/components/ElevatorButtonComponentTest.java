package at.fhhagenberg.sqe.project.ui.components;

import abbot.tester.ComponentTester;
import at.fhhagenberg.sqe.project.model.Floor;
import junit.extensions.abbot.ComponentTestFixture;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by rknoll on 07/01/15.
 */
public class ElevatorButtonComponentTest extends ComponentTestFixture {
    private ComponentTester tester;

    public ElevatorButtonComponentTest(String name) {
        super(name);
    }

    protected void setUp() {
        tester = new ComponentTester();
    }

    protected void tearDown() {
        tester = null;
    }

    @Test
    public void testText() {
        Floor floor = new Floor(0, "Floor 1", "1");
        ElevatorButtonComponent component = new ElevatorButtonComponent(floor);
        JPanel pane = new JPanel();
        pane.add(component);
        showFrame(pane);

        assertEquals("1", component.getText());
    }

    @Test
    public void testPressed() {
        Floor floor = new Floor(0, "Floor 1", "1");
        ElevatorButtonComponent component = new ElevatorButtonComponent(floor);
        JPanel pane = new JPanel();
        pane.add(component);
        showFrame(pane);

        assertFalse(component.isPressed());
        component.setPressed(true);
        assertTrue(component.isPressed());
    }

    @Test
    public void testContains() {
        Floor floor = new Floor(0, "Floor 1", "1");
        ElevatorButtonComponent component = new ElevatorButtonComponent(floor);
        JPanel pane = new JPanel();
        pane.add(component);
        showFrame(pane);

        final boolean[] gotPressed = {false};

        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gotPressed[0] = true;
            }
        });

        tester.actionClick(component, 0, 0);
        assertFalse(gotPressed[0]);

        tester.actionClick(component);
        assertTrue(gotPressed[0]);
    }

}
