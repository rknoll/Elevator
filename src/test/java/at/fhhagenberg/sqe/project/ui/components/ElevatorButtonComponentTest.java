package at.fhhagenberg.sqe.project.ui.components;

import abbot.tester.ComponentTester;
import junit.extensions.abbot.ComponentTestFixture;
import org.junit.After;
import org.junit.Before;
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

    @Before
    protected void setUp() {
        tester = new ComponentTester();
    }

    @After
    protected void tearDown() {
        tester = null;
    }

    @Test
    public void testText() {
        ElevatorButtonComponent component = new ElevatorButtonComponent("1");
        JPanel pane = new JPanel();
        pane.add(component);
        showFrame(pane);

        assertEquals("1", component.getText());
    }

    @Test
    public void testPressed() {
        ElevatorButtonComponent component = new ElevatorButtonComponent("2");
        JPanel pane = new JPanel();
        pane.add(component);
        showFrame(pane);

        assertFalse(component.isPressed());
        component.setPressed(true);
        assertTrue(component.isPressed());
    }

    @Test
    public void testContains() {
        ElevatorButtonComponent component = new ElevatorButtonComponent("3");
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
