package construction;

import java.util.Set;

import org.slf4j.Logger;

import exception.ExceptionLogger;

public class ConstructorMissingRequiredOptionsException extends RuntimeException {

	private static final long serialVersionUID = -5546062339867436689L;

	public ConstructorMissingRequiredOptionsException(Logger logger, String className, Set<String> fieldNames) {
		super("Constructor for " + className + " was provided null values for required fields: " + fieldNames.toString());
		ExceptionLogger.logErrorException(this, logger);
	}
}
