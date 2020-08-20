package sketch.input;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import repair.RepairEngine;

public class SketchInvokerFactory {

	private static final Logger logger = LoggerFactory.getLogger(SketchInvokerFactory.class);
	private static final String INPUT_FILE_SUFFIX = "sketch-script";

	public SketchInvokerFactory() {
	}

	/**
	 * Initializes a SketchInvoker to use a new temporary file
	 * 
	 * @throws IOException - if error creating temporary file for sketch input
	 */
	public SketchInvoker getSketchInvoker() {

		File sketchInput = getSketchInputFile();

		FileWriter fileWriter = getFileWriter(sketchInput);

		return new SketchInvoker(
			sketchInput.getAbsolutePath(),
			fileWriter,
			Runtime.getRuntime(),
			new ErrorScannerFactory()
		);
	}

	private File getSketchInputFile() {
		File sketchInput;
		try {
			sketchInput = File.createTempFile(RepairEngine.APP_NAME, INPUT_FILE_SUFFIX);
		} catch (IOException ex) {
			throw new TempFileIOException(ex, logger);
		}
		return sketchInput;
	}

	private FileWriter getFileWriter(File sketchInput) {
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(sketchInput);
		} catch (IOException ex) {
			throw new TempFileIOException(ex, logger);
		}
		return fileWriter;
	}

}
