package at.fhhagenberg.sqe.project;

import at.fhhagenberg.sqe.mocks.TestConfiguration;
import at.fhhagenberg.sqe.project.services.IUpdateThread;
import at.fhhagenberg.sqe.project.ui.IElevatorWindow;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertTrue;

/**
 * Created by rknoll on 07/01/15.
 */
public class ElevatorLauncherTest {
    private AnnotationConfigApplicationContext context;

    private ElevatorLauncher launcher;

    @Autowired
    private IElevatorWindow mElevatorWindow;

    @Autowired
    private IUpdateThread mUpdateThread;

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext(TestConfiguration.class);
        AutowireCapableBeanFactory acbFactory = context.getAutowireCapableBeanFactory();
        acbFactory.autowireBean(this);
        launcher = new ElevatorLauncher(context);
    }

    @After
    public void tearDown() throws Exception {
        context.close();
    }

    @Test
    public void test() throws InvocationTargetException, InterruptedException {
        launcher.run(new String[]{});
        final Thread[] edt = new Thread[1];

        EventQueue.invokeAndWait(() -> edt[0] = Thread.currentThread());

        edt[0].join();

        assertTrue(mElevatorWindow.isVisible());
        assertTrue(mUpdateThread.isAlive());
        assertTrue(mUpdateThread.isDaemon());
    }
}
