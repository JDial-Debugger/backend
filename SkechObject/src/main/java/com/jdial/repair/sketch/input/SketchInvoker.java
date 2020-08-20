package sketch.input;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to invoke sketch processes on an input script and log any errors
 */
public final class SketchInvoker {

	private static final Logger logger = LoggerFactory.getLogger(SketchInvoker.class);
	private static final String SKETCH_PROC_NAME = "sketch";

	// A temporary file to hold the input script for the sketch process
	private FileWriter sketchInputWriter;
	private Process sketchProc;
	private Runtime runtime;
	private ErrorScannerFactory scannerFactory;

	private String sketchInputFilePath;

	//	Should use SketchInvokerFactory to instantiate
	protected SketchInvoker(
		String sketchInputFilePath,
		FileWriter sketchInputWriter,
		Runtime runtime,
		ErrorScannerFactory scannerFactory
	) {
		this.sketchInputFilePath = sketchInputFilePath;
		this.sketchInputWriter = sketchInputWriter;
		this.runtime = runtime;
		this.scannerFactory = scannerFactory;
	}

	/**
	 * Invokes the MIT Sketch Synthesizer process on a given sketch script
	 * 
	 * @param script the text input into sketch
	 * @return The standard output stream of the sketch process
	 * @throws IOException - if error executing sketch process
	 */
	public InputStream invokeSketch(String script) {

		this.writeContentsToInputFile(script);

		this.execSketch();

		this.logSketchStdError();

		return this.sketchProc.getInputStream();
	}

	private void writeContentsToInputFile(String contents) {
		
		logger.debug("Writing sketch input to " + this.sketchInputFilePath);

		try {
			this.sketchInputWriter.write(contents);
			this.sketchInputWriter.close();
		} catch (IOException ex) {
			throw new TempFileIOException(ex, logger);
		}
	}

	private void execSketch() {

		String sketchCmd = SKETCH_PROC_NAME + " " + this.sketchInputFilePath;
		try {
			this.sketchProc = this.runtime.exec(sketchCmd, null);
		} catch (IOException ex) {
			throw new SketchExecException(ex, logger);
		}
	}

	private void logSketchStdError() {

		ErrorScanner errScnr = this.scannerFactory.getErrorScanner(this.sketchProc);
		while (errScnr.hasNext()) {
			logger.warn("Sketch process stderr: " + errScnr.nextLine());
		}
		errScnr.close();
	}
}
