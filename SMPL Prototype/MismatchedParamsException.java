/**
 * Exception to represent when the arguments for a function call and 
 * Its function definition is mismatched
 */
public class MismatchedParamsException extends RuntimeException {

    public MismatchedParamsException(int paramater, int args) {
	super("Function arguments mismatched. Expected " + String.valueOf(paramater) + " Received " + String.valueOf(args));
    }

    public MismatchedParamsException(int paramater, int args, Throwable cause) {
	super("Function arguments mismatched. Expected " + String.valueOf(paramater) + " Received " + String.valueOf(args), cause);
    }

}
