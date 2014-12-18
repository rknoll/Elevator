package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.services.listeners.IElevatorListener;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rknoll on 16/12/14.
 */
public abstract class ElevatorService<T extends IElevatorListener> {

    private Set<T> mListener;

    private UpdateThread mThread;
    private Class<T> tClass;
    private int mSleepTimeMs;

    public ElevatorService(Class<T> clazz, int sleepTimeMs) {
        mSleepTimeMs = sleepTimeMs;
        tClass = clazz;
        mListener = Collections.newSetFromMap(new ConcurrentHashMap<T, Boolean>());
    }

    public void addListener(T listener) {
        mListener.add(listener);
    }

    public void removeListener(T listener) {
        mListener.remove(listener);
    }

    public void removeAllListeners() {
        mListener.clear();
    }

    public boolean isCompatibleListener(IElevatorListener listener) {
        return tClass.isInstance(listener);
    }

    public void start() {
        if (mThread != null && mThread.isRunning()) return;
        mThread = new UpdateThread();
        mThread.start();
    }

    public void stop() {
        if (mThread == null || !mThread.isRunning()) return;
        mThread.stop();
        mThread = null;
    }

    protected void beginUpdate() {}
    abstract protected void update(T listener);
    protected void endUpdate() {}

    private class UpdateThread implements Runnable {
        private boolean mRunning;

        public void start() {
            if (mRunning) return;
            mRunning = true;
            new Thread(this).start();
        }

        public void stop() {
            if (!mRunning) return;
            mRunning = false;
        }

        public boolean isRunning() {
            return mRunning;
        }

        @Override
        public void run() {
            while (mRunning) {
                beginUpdate();
                for (T l : mListener) {
                    update(l);
                }
                endUpdate();
                try {
                    Thread.sleep(mSleepTimeMs);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

}
