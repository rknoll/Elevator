package at.fhhagenberg.sqe.project.services.automatic.simple;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.services.automatic.BaseAutomaticModeService;
import at.fhhagenberg.sqe.project.services.automatic.IAutomaticModeServiceFactory;

/**
 * Created by rknoll on 05/01/15.
 */
public class SimpleAutomaticModeServiceFactory implements IAutomaticModeServiceFactory {
    @Override
    public BaseAutomaticModeService create(Building building, Elevator elevator) {
        return new SimpleAutomaticModeService(building, elevator);
    }
}
