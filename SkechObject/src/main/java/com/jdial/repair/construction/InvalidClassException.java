package construction;

import org.slf4j.Logger;

import exception.ExceptionLogger;

public class InvalidClassException extends RuntimeException {
	
	private static final long serialVersionUID = -3700880464494460290L;

	public InvalidClassException(Logger logger, Class<?> providedClass, Class<?> factoryClass) {
		super(factoryClass.getName() + " is unable to create instances of " + providedClass);
		ExceptionLogger.logErrorException(this, logger);
	}
}
