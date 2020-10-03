package exception;

import org.slf4j.Logger;

public class MissingRequiredFieldsException extends IllegalArgumentException {

	private static final long serialVersionUID = -2790421368981295883L;
	
	public MissingRequiredFieldsException(Logger logger, Option) {
		super("Options object is missing required fields")
	}
}
