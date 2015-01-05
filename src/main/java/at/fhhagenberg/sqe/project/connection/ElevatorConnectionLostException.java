package at.fhhagenberg.sqe.project.connection;

/**
 * Exception for a Lost Connection
 */
public class ElevatorConnectionLostException extends Exception {
    private static final long serialVersionUID = 660595836051089059L;

    public ElevatorConnectionLostException() {
    }

    public ElevatorConnectionLostException(String message) {
        super(message);
    }

    public ElevatorConnectionLostException(Throwable cause) {
        super(cause);
    }

    public ElevatorConnectionLostException(String message, Throwable cause) {
        super(message, cause);
    }
}
