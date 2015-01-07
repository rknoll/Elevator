package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;

/**
 * Interface for a Service that will be called periodically
 */
public interface IService {
    /**
     * This will be called periodically.
     *
     * @throws ElevatorConnectionLostException If there is no Connection, or it got lost
     */
    public void refresh() throws ElevatorConnectionLostException;
}
