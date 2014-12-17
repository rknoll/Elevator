package at.fhhagenberg.sqe.project.services.listeners;

import at.fhhagenberg.sqe.project.model.Floor;

/**
 * Created by rknoll on 17/12/14.
 */
public interface IFloorStatusListener extends IElevatorListener {
    Floor getFloor();
}
