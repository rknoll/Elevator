package at.fhhagenberg.sqe.project.services.automatic;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;

/**
 * Created by tzoechbauer on 08/01/15
 */
public class MyBaseAutomaticModeService extends BaseAutomaticModeService 
{
	private Floor mNextGoal;
	
	public MyBaseAutomaticModeService(Building building, Elevator elevator) 
	{
        super(building, elevator);
    }
	
	@Override
	protected Floor getNextGoal() 
	{
		return mNextGoal;
	}
	
	public void setNextGoalForTest(Floor nextGoal)
	{
		mNextGoal = nextGoal;
	}

}
