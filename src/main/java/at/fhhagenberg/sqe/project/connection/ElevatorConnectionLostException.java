package at.fhhagenberg.sqe.project.connection;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorConnectionLostException extends Exception {

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
