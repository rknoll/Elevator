package at.fhhagenberg.sqe.project.ui;

import javax.swing.*;

/**
 * Base Class for all Dynamic UI Components.
 * A UI Component is Dynamic, if it registers a Listener to one of the Models
 */
public abstract class DynamicUIComponent extends JComponent {
    private static final long serialVersionUID = 6313714160593767273L;

    /**
     * Unload all registered Listeners, as this View will get removed from Screen
     */
    public abstract void unload();
}
