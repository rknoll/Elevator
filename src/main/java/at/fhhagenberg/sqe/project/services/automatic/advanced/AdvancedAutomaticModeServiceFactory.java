package at.fhhagenberg.sqe.project.services.automatic.advanced;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.services.IService;
import at.fhhagenberg.sqe.project.services.automatic.IAutomaticModeServiceFactory;

/**
 * Created by rknoll on 05/01/15.
 */
public class AdvancedAutomaticModeServiceFactory implements IAutomaticModeServiceFactory {
    @Override
    public IService create(Building building, Elevator elevator) {
        return new AdvancedAutomaticModeService(building, elevator);
    }
}
