package at.fhhagenberg.sqe.project;

/**
 * Created by rknoll on 07/01/15.
 */

import at.fhhagenberg.sqe.project.ui.ElevatorWindowTest;
import at.fhhagenberg.sqe.project.ui.components.*;
import at.fhhagenberg.sqe.project.ui.views.BuildingStatusViewTest;
import at.fhhagenberg.sqe.project.ui.views.ElevatorDetailViewTest;
import at.fhhagenberg.sqe.project.ui.views.ElevatorOverviewViewTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This Class is used to Link the Abbot Test Classes that still use JUnit 3.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ElevatorButtonComponentTest.class,
        ElevatorButtonsComponentTest.class,
        ElevatorFloorComponentTest.class,
        ElevatorModeComponentTest.class,
        ElevatorPositionComponentTest.class,
        FloorStatusComponentTest.class,
        OnOffButtonComponentTest.class,
        BuildingStatusViewTest.class,
        ElevatorDetailViewTest.class,
        ElevatorOverviewViewTest.class,
        ElevatorWindowTest.class
})
public class JUnit3Tests {
}
