package exception;

import org.slf4j.Logger;

/**
 *	Used for third party libraries that originate their own exception
 *	Often used for converting a checked exception to an unchecked exception
 *	as checked exceptions violate open/closed principle 
 */
public class LibraryException extends RuntimeException {

	private static final long serialVersionUID = 1019889902273704494L;
	
	public LibraryException(String message, Exception cause, Logger logger) {
		super(message + ": " + cause.toString());
		this.initCause(cause);
		this.setStackTrace(cause.getStackTrace());
		logger.error(this.getMessage());
		logger.error(this.getStackTrace().toString());
	}
}
