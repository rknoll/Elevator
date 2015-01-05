package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rknoll on 05/01/15.
 */
public class AdvancedAutomaticModeService extends BaseAutomaticModeService {

    private static Map<AdvancedAutomaticModeService, Floor> sGlobalGoals = new HashMap<AdvancedAutomaticModeService, Floor>();

    public AdvancedAutomaticModeService(Building building, Elevator elevator) {
        super(building, elevator);
    }

    private boolean containsGoal(Floor floor) {
        for (AdvancedAutomaticModeService service : sGlobalGoals.keySet()) {
            if (service == this) continue;
            if (floor == sGlobalGoals.get(service)) return true;
        }
        return false;
    }

    private Floor searchGoal() {
        if (mElevator.getDirection() == Elevator.Direction.UNCOMMITTED) {
            // no direction yet, check for requests
            // check passengers first
            for (Floor f : mElevator.getFloors()) {
                if (mElevator.getButton(f)) return f;
            }
            // check external people
            for (Floor f : mElevator.getFloors()) {
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
                    if (mElevator.getButton(f)) return f;
                    if (containsGoal(f)) continue;
                    if (f.isButtonUp()) return f;
                }
            } else {
                for (int i = currentFloor - 1; i >= 0; --i) {
                    Floor f = mElevator.getFloor(i);
                    if (mElevator.getButton(f)) return f;
                    if (containsGoal(f)) continue;
                    if (f.isButtonDown()) return f;
                }
            }
            // no requests in this direction, reverse
            if (mElevator.getDirection() == Elevator.Direction.UP) {
                for (int i = currentFloor - 1; i >= 0; --i) {
                    Floor f = mElevator.getFloor(i);
                    if (mElevator.getButton(f)) return f;
                    if (containsGoal(f)) continue;
                    if (f.isButtonDown()) return f;
                }
            } else {
                for (int i = currentFloor + 1; i < floorCount; ++i) {
                    Floor f = mElevator.getFloor(i);
                    if (mElevator.getButton(f)) return f;
                    if (containsGoal(f)) continue;
                    if (f.isButtonUp()) return f;
                }
            }
            // check all directions
            if (mElevator.getDirection() == Elevator.Direction.UP) {
                for (int i = currentFloor - 1; i >= 0; --i) {
                    Floor f = mElevator.getFloor(i);
                    if (mElevator.getButton(f)) return f;
                    if (containsGoal(f)) continue;
                    if (f.isButtonDown() || f.isButtonUp()) return f;
                }
            } else {
                for (int i = currentFloor + 1; i < floorCount; ++i) {
                    Floor f = mElevator.getFloor(i);
                    if (mElevator.getButton(f)) return f;
                    if (containsGoal(f)) continue;
                    if (f.isButtonDown() || f.isButtonUp()) return f;
                }
            }
        }
        // go home by default
        Floor baseFloor = mElevator.getFloor(0);
        if (mElevator.getCurrentFloor() != baseFloor) return baseFloor;
        // nothing to do yet
        return null;
    }

    @Override
    protected Floor getNextGoal() {
        Floor f = searchGoal();
        sGlobalGoals.put(this, f);
        return f;
    }
}
