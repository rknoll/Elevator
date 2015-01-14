package at.fhhagenberg.sqe.project;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by rknoll on 07/01/15.
 */
public class ElevatorProgramTest {

    private ElevatorLauncher launcher;
    private ElevatorProgram program;

    @Before
    public void setUp() {
        launcher = createMock(ElevatorLauncher.class);
        program = new ElevatorProgram();
    }

    @Test
    public void testStaticSetup() {
        assertTrue(program.getLauncher() instanceof ElevatorLauncher);
    }

    @Test
    public void testSetLauncher() {
        program.setLauncher(launcher);
        assertEquals(launcher, program.getLauncher());
    }

    @Test
    public void testMain() {
        String args[] = {"a", "b"};
        program.setLauncher(launcher);

        launcher.run(args);
        EasyMock.replay(launcher);

        ElevatorProgram.main(args);

        EasyMock.verify(launcher);
    }
}
