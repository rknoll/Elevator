package at.fhhagenberg.sqe.project.services.automatic;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.services.IService;

/**
 * Created by rknoll on 05/01/15.
 */
public interface IAutomaticModeServiceFactory {
    public IService create(Building building, Elevator elevator);
}
