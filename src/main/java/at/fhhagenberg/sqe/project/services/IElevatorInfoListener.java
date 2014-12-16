package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.model.Elevator;

/**
 * Created by rknoll on 16/12/14.
 */
public interface IElevatorInfoListener extends IElevatorListener {
    Elevator getElevator();
}
