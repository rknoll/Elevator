package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;

/**
 * Created by rknoll on 03/01/15.
 */
public class UpdateThread extends Thread {

    private IService mService;
    private int mRefreshTimeMs;

    public UpdateThread(IService service, int refreshTimeMs) {
        mService = service;
        mRefreshTimeMs = refreshTimeMs;
    }

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
