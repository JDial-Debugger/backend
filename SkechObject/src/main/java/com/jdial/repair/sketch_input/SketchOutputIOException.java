package sketch_input;

import java.io.IOException;

import org.slf4j.Logger;

public class SketchOutputIOException extends RuntimeException {
	
	public SketchOutputIOException(IOException ex, Logger logger) {
		super("Error occured reading sketch output: " + ex.toString());
		this.initCause(ex);
		this.setStackTrace(ex.getStackTrace());
		logger.error(this.getMessage());
		logger.error(this.getStackTrace().toString());
	}

}
