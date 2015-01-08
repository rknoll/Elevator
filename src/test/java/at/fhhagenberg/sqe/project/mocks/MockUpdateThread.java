package at.fhhagenberg.sqe.project.mocks;

import at.fhhagenberg.sqe.project.services.IUpdateThread;
import org.springframework.stereotype.Component;

/**
 * Created by rknoll on 08/01/15.
 */
@Component
public class MockUpdateThread implements IUpdateThread {
    private boolean mIsDaemon;
    private boolean mIsStarted;

    @Override
    public void start() {
        mIsStarted = true;
    }

    @Override
    public boolean isAlive() {
        return mIsStarted;
    }

    @Override
    public void setDaemon(boolean daemon) {
        mIsDaemon = daemon;
    }

    @Override
    public boolean isDaemon() {
        return mIsDaemon;
    }
}
