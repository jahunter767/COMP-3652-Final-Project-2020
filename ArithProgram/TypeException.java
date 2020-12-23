/**
 * Exception to represent when a method was invoked by a incompatible type.
 */
public class TypeException extends RuntimeException {

    public TypeException() {
	super("TypeException: INCOMPATIBLE METHOD");
    }

    public TypeException(String Type) {
	super("TypeException: Incompatible method for " + Type);
    }

    public TypeException(Throwable cause) {
	super("TypeException: INCOMPATIBLE METHOD", cause);
    }

}
