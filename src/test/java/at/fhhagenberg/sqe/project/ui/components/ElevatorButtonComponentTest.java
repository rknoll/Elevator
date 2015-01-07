package at.fhhagenberg.sqe.project.ui.components;

import abbot.tester.ComponentTester;
import junit.extensions.abbot.ComponentTestFixture;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by rknoll on 07/01/15.
 */
public class ElevatorButtonComponentTest extends ComponentTestFixture {
    private ComponentTester tester;

    protected void setUp() {
        tester = new ComponentTester();
    }

    protected void tearDown() {
        tester = null;
    }

    public void testText() {
        ElevatorButtonComponent component = new ElevatorButtonComponent("1");
        showFrame(component);

        assertEquals("1", component.getText());
    }

    public void testPressed() {
        ElevatorButtonComponent component = new ElevatorButtonComponent("2");
        showFrame(component);

        assertFalse(component.isPressed());
        component.setPressed(true);
        assertTrue(component.isPressed());
    }

    public void testContains() {
        ElevatorButtonComponent component = new ElevatorButtonComponent("3");
        showFrame(component);

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
