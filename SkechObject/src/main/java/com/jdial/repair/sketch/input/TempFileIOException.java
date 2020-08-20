package sketch.input;

import org.slf4j.Logger;

import exception.LibraryException;

public class TempFileIOException extends LibraryException {

	public TempFileIOException(Exception cause, Logger logger) {
		super("Error occured creating temporary file to write sketch input into", cause, logger);
	}

}
