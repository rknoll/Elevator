package at.fhhagenberg.sqe.project.ui.views.listeners;

import at.fhhagenberg.sqe.project.model.Elevator;

/**
 * Listener for switching to an Elevator Detail View
 */
public interface IElevatorDetailSelectListener {
    /**
     * Show the Details of an Elevator.
     *
     * @param elevator The Elevator
     */
    void elevatorSelected(Elevator elevator);
}
