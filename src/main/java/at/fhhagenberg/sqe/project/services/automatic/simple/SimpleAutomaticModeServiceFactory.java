package at.fhhagenberg.sqe.project.services.automatic.simple;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.services.IService;
import at.fhhagenberg.sqe.project.services.automatic.IAutomaticModeServiceFactory;

/**
 * Created by rknoll on 05/01/15.
 */
public class SimpleAutomaticModeServiceFactory implements IAutomaticModeServiceFactory {
    @Override
    public IService create(Building building, Elevator elevator) {
        return new SimpleAutomaticModeService(building, elevator);
    }
}
