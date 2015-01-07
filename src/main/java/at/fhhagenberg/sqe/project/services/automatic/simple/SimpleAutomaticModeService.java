package at.fhhagenberg.sqe.project.services.automatic.simple;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.automatic.BaseAutomaticModeService;

/**
 * A Simple Automatic Mode.
 * This algorithm just goes up until the last Floor, and then changes the direction.
 * While moving, it will stop at every Floor.
 * To use this Service, change the Default Spring Configuration to return SimpleAutomaticModeService::new
 */
public class SimpleAutomaticModeService extends BaseAutomaticModeService {

    /**
     * Create a new Simple Automatic Mode for an Elevator in a Building.
     *
     * @param building The Building
     * @param elevator The Elevator
     */
    public SimpleAutomaticModeService(Building building, Elevator elevator) {
        super(building, elevator);
    }

    /**
     * Get the next Goal of this Elevator
     *
     * @return The Next Goal, or null of no new goal should be set
     */
    @Override
    protected Floor getNextGoal() {
        int currentFloor = mElevator.getCurrentFloor().getFloorNumber();
        int nextFloor = currentFloor;
        int floorCount = mBuilding.getNumberOfFloors();
        boolean prevDirection = mElevator.getDirection() != Elevator.Direction.DOWN;
        boolean nextDirection = prevDirection;

        do {
            // calculate next goal
            nextFloor += nextDirection ? 1 : -1;

            // check if we reached a limit, if yes, invert direction
            if (nextFloor == floorCount) {
                nextDirection = false;
                nextFloor = currentFloor - 1;
                if (nextFloor == -1 || !prevDirection) return null; // building has only one reachable floor?
            } else if (nextFloor == -1) {
                nextDirection = true;
                nextFloor = currentFloor + 1;
                if (nextFloor == floorCount || prevDirection) return null; // building has only one reachable floor?
            }

            // continue until we find a floor, which can be services by this elevator
        } while (!mElevator.getService(mElevator.getFloor(nextFloor)));

        return mElevator.getFloor(nextFloor);
    }

}
