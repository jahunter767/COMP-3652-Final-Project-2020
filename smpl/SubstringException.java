/**
 * Exception to represent when there was substring error
 */
public class SubstringException extends RuntimeException {

private String err = new String();

    public SubstringException() {
	super("SubStringException: The start index must be atleast 0 and less than the length of the string. The endindex must be less or equal to the length of the string");
    }

    public SubstringException(Throwable cause) {
	super("SubStringException: The start index must be atleast 0 and less than the length of the string. The endindex must be less or equal to the length of the string", cause);
    }

}
