package at.fhhagenberg.sqe.project.services.automatic;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.services.IService;

/**
 * An Interface for an Automatic Mode Service Factory.
 */
public interface IAutomaticModeServiceFactory {
    /**
     * Create a new Automatic Mode Service for an Elevator inside a Building.
     *
     * @param building The Building
     * @param elevator The Floor
     * @return A new Automatic Mode Service
     */
    public IService create(Building building, Elevator elevator);
}
