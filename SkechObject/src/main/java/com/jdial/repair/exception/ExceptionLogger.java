package exception;

import org.slf4j.Logger;

public class ExceptionLogger {
	
	public static void logErrorException(Exception ex, Logger logger) {
		logger.error(ex.getMessage());
		logger.error(ex.getStackTrace().toString());
	}
	
	public static void logWarningException(Exception ex, Logger logger) {
		logger.warn(ex.getMessage());
		logger.warn(ex.getStackTrace().toString());
	}
}
