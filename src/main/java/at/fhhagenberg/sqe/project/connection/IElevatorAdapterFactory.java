package at.fhhagenberg.sqe.project.connection;

/**
 * Factory Interface to create ElevatorAdapters.
 */
public interface IElevatorAdapterFactory {
    /**
     * Create a new Instance of an ElevatorAdapter.
     *
     * @return The new Connection Object or null, if the connection failed.
     */
    public IElevatorAdapter create();
}
