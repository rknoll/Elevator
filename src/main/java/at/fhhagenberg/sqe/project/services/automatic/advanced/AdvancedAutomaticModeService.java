package at.fhhagenberg.sqe.project.services.automatic.advanced;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.automatic.BaseAutomaticModeService;

/**
 * An Advanced Automatic mode that implements the Elevator Algorithm.
 * Basically it keeps going into the current Direction until there are no more
 * Requests. Then it goes into the other Direction. If there are no Requests at all
 * the Elevator will wait in the Base Floor.
 * This is the default Service, as defined in the Spring Configuration.
 */
public class AdvancedAutomaticModeService extends BaseAutomaticModeService {

    /**
     * Create a new Advanced Automatic Mode for an Elevator in a Building.
     *
     * @param building The Building
     * @param elevator The Elevator
     */
    public AdvancedAutomaticModeService(Building building, Elevator elevator) {
        super(building, elevator);
    }

    /**
     * Check if any other Elevator in the Building already targets the Floor.
     *
     * @param floor The Floor
     * @return true, if there is at least one Elevator targeting that Floor, false otherwise
     */
    private boolean containsGoal(Floor floor) {
        for (Elevator e : mBuilding.getElevators()) {
            if (e == mElevator) continue;
            if (e.getTarget() == floor) return true;
        }
        return false;
    }

    /**
     * Get the next Goal of this Elevator
     *
     * @return The Next Goal, or null of no new goal should be set
     */
    @Override
    protected Floor getNextGoal() {
        if (mElevator.getDirection() == Elevator.Direction.UNCOMMITTED) {
            // no direction yet, check for requests
            // check passengers first
            for (Floor f : mElevator.getFloors()) {
                if (!mElevator.getService(f)) continue;
                if (mElevator.getButton(f)) return f;
            }
            // check external people
            for (Floor f : mElevator.getFloors()) {
                if (!mElevator.getService(f)) continue;
                if (containsGoal(f)) continue;
                if (f.isButtonDown() || f.isButtonUp()) return f;
            }
        } else {
            // we have a committed direction, continue going until there are no requests
            int currentFloor = mElevator.getCurrentFloor().getFloorNumber();
            int floorCount = mBuilding.getNumberOfFloors();
            if (mElevator.getDirection() == Elevator.Direction.UP) {
                for (int i = currentFloor + 1; i < floorCount; ++i) {
                    Floor f = mElevator.getFloor(i);
                    if (!mElevator.getService(f)) continue;
                    if (mElevator.getButton(f)) return f;
                    if (containsGoal(f)) continue;
                    if (f.isButtonUp()) return f;
                }
            } else {
                for (int i = currentFloor - 1; i >= 0; --i) {
                    Floor f = mElevator.getFloor(i);
                    if (!mElevator.getService(f)) continue;
                    if (mElevator.getButton(f)) return f;
                    if (containsGoal(f)) continue;
                    if (f.isButtonDown()) return f;
                }
            }
            // no requests in this direction, reverse
            if (mElevator.getDirection() == Elevator.Direction.UP) {
                for (int i = currentFloor - 1; i >= 0; --i) {
                    Floor f = mElevator.getFloor(i);
                    if (!mElevator.getService(f)) continue;
                    if (mElevator.getButton(f)) return f;
                    if (containsGoal(f)) continue;
                    if (f.isButtonDown()) return f;
                }
            } else {
                for (int i = currentFloor + 1; i < floorCount; ++i) {
                    Floor f = mElevator.getFloor(i);
                    if (!mElevator.getService(f)) continue;
                    if (mElevator.getButton(f)) return f;
                    if (containsGoal(f)) continue;
                    if (f.isButtonUp()) return f;
                }
            }
            // check all directions
            if (mElevator.getDirection() == Elevator.Direction.UP) {
                for (int i = currentFloor - 1; i >= 0; --i) {
                    Floor f = mElevator.getFloor(i);
                    if (!mElevator.getService(f)) continue;
                    if (mElevator.getButton(f)) return f;
                    if (containsGoal(f)) continue;
                    if (f.isButtonDown() || f.isButtonUp()) return f;
                }
            } else {
                for (int i = currentFloor + 1; i < floorCount; ++i) {
                    Floor f = mElevator.getFloor(i);
                    if (!mElevator.getService(f)) continue;
                    if (mElevator.getButton(f)) return f;
                    if (containsGoal(f)) continue;
                    if (f.isButtonDown() || f.isButtonUp()) return f;
                }
            }
        }
        // go home by default
        return mElevator.getFloor(0);
    }
}
