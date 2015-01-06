package at.fhhagenberg.sqe.project.services.automatic;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;

/**
 * Created by rknoll on 05/01/15.
 */
public interface IAutomaticModeServiceFactory {
    public BaseAutomaticModeService create(Building building, Elevator elevator);
}
