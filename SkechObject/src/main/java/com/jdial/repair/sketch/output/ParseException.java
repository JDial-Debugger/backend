package sketch.output;

import org.slf4j.Logger;

public class ParseException extends RuntimeException {
	
	private static final long serialVersionUID = -3487226290588429563L;

	public ParseException(String message, Logger logger) {
		super(message);
		logger.warn(message);
		logger.warn(this.getStackTrace().toString());
	}

}
