package at.fhhagenberg.sqe.project.services.listeners;

import at.fhhagenberg.sqe.project.model.Elevator;

/**
 * Created by rknoll on 16/12/14.
 */
public interface IElevatorPositionListener extends IElevatorListener {
    Elevator getElevator();
}
