package sketch.output;

import org.slf4j.Logger;

public class IOException extends RuntimeException {
	
	private static final long serialVersionUID = 3614590124085235456L;

	public IOException(java.io.IOException ex, Logger logger) {
		super("Error occured reading sketch output: " + ex.toString());
		this.initCause(ex);
		this.setStackTrace(ex.getStackTrace());
		logger.error(this.getMessage());
		logger.error(this.getStackTrace().toString());
	}

}
