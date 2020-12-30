/**
 * Exception to represent when a method was invoked by a incompatible type.
 */
public class VectorException extends RuntimeException {

    public VectorException() {
	super("VectorOutOfBoundException: VECTOR INDEX OUT OF BOUND");
    }

    public VectorException(Throwable cause) {
	super("VectorOutOfBoundException: VECTOR INDEX OUT OF BOUND", cause);
    }

}
