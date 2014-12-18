package at.fhhagenberg.sqe.project.services.listeners;

import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;

/**
 * Created by rknoll on 16/12/14.
 */
public interface IElevatorServicesFloorListener extends IElevatorListener {
    Elevator getElevator();
    Floor getFloor();
}
