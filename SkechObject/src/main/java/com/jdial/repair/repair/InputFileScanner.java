package repair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delegate.ScannerDelegate;

public class InputFileScanner extends ScannerDelegate {

	private static final Logger logger = LoggerFactory.getLogger(InputReader.class);

	private String fileName;

	public InputFileScanner(String fileName) {
		this.fileName = fileName;
		try {
			this.scnr = new Scanner(new File(fileName));
		} catch (FileNotFoundException ex) {
			throw new InvalidInputException("No input file found with name: " + fileName, logger);
		}
		this.scnr.useDelimiter("\\A");
	}

	public String readContents() {
		logger.info("Reading input from " + this.fileName);
		String fileContents = this.next();
		this.close();
		return fileContents;
	}
}
