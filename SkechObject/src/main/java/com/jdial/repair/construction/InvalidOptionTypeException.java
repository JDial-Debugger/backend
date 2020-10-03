package construction;

import org.slf4j.Logger;

import exception.ExceptionLogger;

public class InvalidOptionTypeException extends RuntimeException {

	private static final long serialVersionUID = 61862842093878966L;

	public InvalidOptionTypeException(
		Class<? extends Options> providedOptionClass,
		Class<? extends Options> expectedOptionClass,
		Class<?> destinationClass,
		Logger logger
	) {
		super(
			"Was expecting options of type "
				+ expectedOptionClass.getName()
				+ " to construct an instance of "
				+ destinationClass.getName()
				+ " but received options of type "
				+ providedOptionClass.getName()
		);
		ExceptionLogger.logErrorException(this, logger);
	}
}
