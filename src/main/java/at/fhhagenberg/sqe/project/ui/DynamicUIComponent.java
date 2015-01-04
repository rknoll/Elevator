package at.fhhagenberg.sqe.project.ui;

import javax.swing.*;

/**
 * Base Class for all Dynamic UI Components.
 * A UI Component is Dynamic, if it registers a Listener to one of the Models
 */
public abstract class DynamicUIComponent extends JComponent {
    public abstract void unload();
}
