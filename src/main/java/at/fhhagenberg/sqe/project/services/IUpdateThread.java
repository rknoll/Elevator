package at.fhhagenberg.sqe.project.services;

/**
 * Created by rknoll on 08/01/15.
 */
public interface IUpdateThread {

    /**
     * Start this Thread
     */
    public void start();

    /**
     * Check if this Thread is Running.
     *
     * @return true if this Thread is running, false otherwise
     */
    public boolean isAlive();

    /**
     * Set this Thread to be a Daemon Thread.
     *
     * @param daemon true if this Thread should be a Daemon Thread, false otherwise
     */
    public void setDaemon(boolean daemon);

    /**
     * Check if this Thread is a Daemon Thread.
     *
     * @return true if this Thread is a Daemon Thread
     */
    public boolean isDaemon();
}
