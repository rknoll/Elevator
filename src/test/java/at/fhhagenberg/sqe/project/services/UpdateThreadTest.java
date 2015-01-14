package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.mocks.DummyService;
import at.fhhagenberg.sqe.mocks.TestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;

/**
 * Created by rknoll on 07/01/15.
 */
public class UpdateThreadTest {
    // TODO: finish UpdateThread tests

    private AnnotationConfigApplicationContext context;
    private UpdateThread thread;

    @Autowired
    private IService mService;

    @Before
    public void setUp() {
        thread = new UpdateThread();
        context = new AnnotationConfigApplicationContext(TestConfiguration.class);
        AutowireCapableBeanFactory acbFactory = context.getAutowireCapableBeanFactory();
        acbFactory.autowireBean(thread);
        acbFactory.autowireBean(this);
    }

    @Test
    public void testSetup() {
        assertEquals(0, ((DummyService)mService).getRefreshCount());
    }

    @Test
    public void testDefaultRefreshTime() throws InterruptedException {
        thread.start();
        Thread.sleep(UpdateThread.DEFAULT_REFRESH_TIME_MS / 2);
        assertEquals(0, ((DummyService) mService).getRefreshCount());
        Thread.sleep(UpdateThread.DEFAULT_REFRESH_TIME_MS);
        thread.interrupt();
        assertEquals(1, ((DummyService) mService).getRefreshCount());
        Thread.sleep((long) (UpdateThread.DEFAULT_REFRESH_TIME_MS * 1.5));
        assertEquals(1, ((DummyService) mService).getRefreshCount());
    }

    @Test
    public void testSetRefreshTime() throws InterruptedException {
        thread.setRefreshTimeMs(UpdateThread.DEFAULT_REFRESH_TIME_MS / 2);
        thread.start();
        Thread.sleep(UpdateThread.DEFAULT_REFRESH_TIME_MS / 4);
        assertEquals(0, ((DummyService) mService).getRefreshCount());
        Thread.sleep(UpdateThread.DEFAULT_REFRESH_TIME_MS / 2);
        thread.interrupt();
        assertEquals(1, ((DummyService) mService).getRefreshCount());
        Thread.sleep((long) (UpdateThread.DEFAULT_REFRESH_TIME_MS * 0.75));
        assertEquals(1, ((DummyService) mService).getRefreshCount());
    }

    @Test
    public void testException() throws InterruptedException {
        ((DummyService) mService).setFireException();
        thread.start();
        Thread.sleep(UpdateThread.DEFAULT_REFRESH_TIME_MS / 2);
        assertEquals(0, ((DummyService) mService).getRefreshCount());
        Thread.sleep(UpdateThread.DEFAULT_REFRESH_TIME_MS);
        thread.interrupt();
        assertEquals(1, ((DummyService) mService).getRefreshCount());
        Thread.sleep((long) (UpdateThread.DEFAULT_REFRESH_TIME_MS * 1.5));
        assertEquals(1, ((DummyService) mService).getRefreshCount());
    }

}
