package sketch_input;

import org.slf4j.Logger;

public class SketchOutputParseException extends RuntimeException {
	
	public SketchOutputParseException(String message, Logger logger) {
		super(message);
		logger.warn(message);
		logger.warn(this.getStackTrace().toString());
	}

}
