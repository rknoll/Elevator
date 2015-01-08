package at.fhhagenberg.sqe.project.ui.components;

import abbot.tester.ComponentTester;
import junit.extensions.abbot.ComponentTestFixture;

import javax.swing.*;

/**
 * Created by rknoll on 07/01/15.
 */
public class OnOffButtonComponentTest extends ComponentTestFixture {
    // TODO: finish OnOffButtonComponent tests

    private ComponentTester tester;

    protected void setUp() {
        tester = new ComponentTester();
    }

    protected void tearDown() {
        tester = null;
    }

    public void testCreation() {
        OnOffButtonComponent component = new OnOffButtonComponent("true", "false");
        JPanel pane = new JPanel();
        pane.add(component);
        showFrame(pane);
    }
}
