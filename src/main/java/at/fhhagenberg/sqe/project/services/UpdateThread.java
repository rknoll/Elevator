package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;

/**
 * A Thread to Call a Service in a specific interval
 */
public class UpdateThread extends Thread {

    /**
     * The Service to call
     */
    private final IService mService;
    /**
     * The Interval to call the Service
     */
    private final int mRefreshTimeMs;

    /**
     * Create a new Thread that calls a Service in a specific Interval.
     *
     * @param service       The Service
     * @param refreshTimeMs The Interval to call the Service in Milliseconds
     */
    public UpdateThread(IService service, int refreshTimeMs) {
        mService = service;
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
