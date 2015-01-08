package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A Thread to Call a Service in a specific interval
 */
@Component
public class UpdateThread extends Thread implements IUpdateThread {

    /**
     * The Default Refresh Time in Milliseconds.
     */
    public static final int DEFAULT_REFRESH_TIME_MS = 100;

    /**
     * The Service to call
     */
    @Autowired
    private IService mService;
    /**
     * The Interval to call the Service
     */
    private int mRefreshTimeMs;

    /**
     * Create a new Thread that calls a Service in a specific Interval.
     */
    public UpdateThread() {
        mRefreshTimeMs = DEFAULT_REFRESH_TIME_MS;
    }

    /**
     * Set the new Refresh Time in Milliseconds.
     *
     * @param refreshTimeMs The new Refresh Time
     */
    public void setRefreshTimeMs(int refreshTimeMs) {
        mRefreshTimeMs = refreshTimeMs;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                sleep(mRefreshTimeMs);
                mService.refresh();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            } catch (ElevatorConnectionLostException ignored) {
            }
        }
    }
}
