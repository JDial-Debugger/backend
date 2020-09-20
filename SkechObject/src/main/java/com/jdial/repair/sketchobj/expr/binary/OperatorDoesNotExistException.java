package sketchobj.expr.binary;

import org.slf4j.Logger;

import exception.ExceptionLogger;

public class OperatorDoesNotExistException extends RuntimeException {

	private static final long serialVersionUID = -5280673779159896429L;

	public OperatorDoesNotExistException(String operator, Logger logger) {
		super("Operator not recognized: " + operator);
		ExceptionLogger.logErrorException(this, logger);
	}
}
