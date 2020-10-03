package exception;

import org.slf4j.Logger;

public class InvalidClassException extends RuntimeException {

	private static final long serialVersionUID = 6075456278408955422L;

	public InvalidClassException(Logger logger, String className) {
		super("Invalid class '" + className + "' provided to factory method");
		ExceptionLogger.logErrorException(this, logger);
	}
}
