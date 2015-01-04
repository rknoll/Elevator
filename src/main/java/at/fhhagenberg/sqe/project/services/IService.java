package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;

/**
 * Interface for a Service that will be called cyclic
 */
interface IService {
    void refresh() throws ElevatorConnectionLostException;
}
