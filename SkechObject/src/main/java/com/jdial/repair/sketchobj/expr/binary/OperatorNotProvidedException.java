package sketchobj.expr.binary;

import org.slf4j.Logger;

import exception.ExceptionLogger;

public class OperatorNotProvidedException extends RuntimeException {

	private static final long serialVersionUID = 3799446949352341221L;

	public OperatorNotProvidedException(Logger logger) {
		super("Operator not provided to base ExprBinary class");
		ExceptionLogger.logErrorException(this, logger);
	}
}
