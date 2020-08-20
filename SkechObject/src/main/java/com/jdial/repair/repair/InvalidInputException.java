package repair;

import org.slf4j.Logger;

import exception.ExceptionLogger;

public class InvalidInputException extends RuntimeException {

	private static final long serialVersionUID = 6470596622230686777L;

	public InvalidInputException(String message, Logger logger) {
		super(message);
		ExceptionLogger.logErrorException(this, logger);
	}
}
