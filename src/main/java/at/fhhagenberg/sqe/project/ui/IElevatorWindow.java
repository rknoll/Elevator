package at.fhhagenberg.sqe.project.ui;

/**
 * Interface for a basic Window
 */
public interface IElevatorWindow {
    /**
     * Set if this Window is visible.
     *
     * @param visible true to show the window, false to hide it
     */
    public void setVisible(boolean visible);

    /**
     * Check if this Window is visible.
     *
     * @return true if this Window is visible, false otherwise
     */
    public boolean isVisible();
}
