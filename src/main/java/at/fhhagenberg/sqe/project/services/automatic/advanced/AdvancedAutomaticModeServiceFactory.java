package at.fhhagenberg.sqe.project.services.automatic.advanced;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.services.automatic.BaseAutomaticModeService;
import at.fhhagenberg.sqe.project.services.automatic.IAutomaticModeServiceFactory;

/**
 * Created by rknoll on 05/01/15.
 */
public class AdvancedAutomaticModeServiceFactory implements IAutomaticModeServiceFactory {
    @Override
    public BaseAutomaticModeService create(Building building, Elevator elevator) {
        return new AdvancedAutomaticModeService(building, elevator);
    }
}
