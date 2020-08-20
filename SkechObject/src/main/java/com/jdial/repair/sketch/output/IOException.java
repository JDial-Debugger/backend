package sketch.output;

import org.slf4j.Logger;

import exception.LibraryException;

public class IOException extends LibraryException {
	
	private static final long serialVersionUID = 3614590124085235456L;

	public IOException(java.io.IOException cause, Logger logger) {
		super("Error occured reading sketch output", cause, logger);
	}

}
