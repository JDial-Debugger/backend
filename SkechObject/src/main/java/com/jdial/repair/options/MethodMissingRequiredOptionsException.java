package options;

import java.util.Set;

import org.slf4j.Logger;

import exception.ExceptionLogger;

public class MethodMissingRequiredOptionsException extends RuntimeException {
	
	private static final long serialVersionUID = -3286317498186039510L;

	public MethodMissingRequiredOptionsException(Logger logger, String methodName, Set<String> fieldNames) {
		super("Method " + methodName + " was provided null values for required fields: " + fieldNames.toString());
		ExceptionLogger.logErrorException(this, logger);
	}
}
