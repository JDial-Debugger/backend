package json_input;

public class InvalidTraceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidTraceException(String message) {
		super(message);
	}
	public InvalidTraceException(String message, Trace trace) {
		super(message + "\nTrace: " + trace.toString());
	}
	public InvalidTraceException(String message, Trace trace, Integer index) {
		super(message + "\nTrace: " + trace.toString() + "\nIndex: " + index);
	}
}
