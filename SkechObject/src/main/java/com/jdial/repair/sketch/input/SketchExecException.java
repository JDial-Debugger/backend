package sketch.input;

import java.io.IOException;

import org.slf4j.Logger;

import exception.LibraryException;

public class SketchExecException extends LibraryException {
	
	private static final long serialVersionUID = 6884233679466541151L;

	public SketchExecException(IOException cause, Logger logger) {
		super("Error occured executing sketch", cause, logger);
	}

}
