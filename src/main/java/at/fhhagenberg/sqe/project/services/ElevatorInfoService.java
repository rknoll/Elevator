package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorInfoService {

    private IElevatorAdapter mAdapter;
    private Set<IElevatorInfoListener> mListener;

    private UpdateThread mThread;

    public ElevatorInfoService(IElevatorAdapter adapter) {
        mAdapter = adapter;
        mListener = Collections.newSetFromMap(new ConcurrentHashMap<IElevatorInfoListener, Boolean>());
    }

    public void addListener(IElevatorInfoListener listener) {

    }

    public void removeListener(IElevatorInfoListener listener) {

    }

    public void start() {
        if (mThread != null) return;
        mThread = new UpdateThread();
        mThread.start();
    }

    public void stop() {
        if (mThread == null) return;
        mThread.stop();
        mThread = null;
    }

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

        @Override
        public void run() {
            while (mRunning) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

}
