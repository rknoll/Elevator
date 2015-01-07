package at.fhhagenberg.sqe.project.ui;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by rknoll on 07/01/15.
 */
public class DynamicUIComponentTest {
    @Test
    public void testComponentInterface() {
        final boolean[] called = new boolean[1];

        DynamicUIComponent component = new DynamicUIComponent() {
            @Override
            public void unload() {
                called[0] = true;
            }
        };

        // just try to call the function
        component.unload();

        assertTrue(called[0]);
    }
}
