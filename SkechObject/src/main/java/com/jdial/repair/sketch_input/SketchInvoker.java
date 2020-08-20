package sketch_input;

import java.io.*;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import repair.RepairEngine;

/**
 * Used to invoke sketch processes on input scripts and log any errors
 *
 */
public final class SketchInvoker {

	private static final Logger logger =
			LoggerFactory.getLogger(SketchInvoker.class);
	private static final String INPUT_FILE_SUFFIX = "sketch-script";
	private static final String SKETCH_PROC_NAME = "sketch";

	// A temporary file to hold the input script for the sketch process
	private FileWriter sketchInputWriter;
	private Process sketchProc;
	private Runtime runtime;
	private Scanner sketchErrorReader;
	private BufferedReader sketchErrReader;
	private BufferedReader sketchOutReader;

	private String sketchInputFilePath;

	/**
	 * Initializes a SketchInvoker to use a new temporary file
	 * 
	 * @throws IOException - if error creating temporary file for sketch input
	 */
	public static SketchInvoker createSketchInvoker() throws IOException {

		File sketchInput =
				File.createTempFile(RepairEngine.APP_NAME, INPUT_FILE_SUFFIX);
		return new SketchInvoker(
				sketchInput.getAbsolutePath(),
				new FileWriter(sketchInput),
				Runtime.getRuntime());
	}

	private SketchInvoker(
			String sketchInputFilePath,
			FileWriter sketchInputWriter,
			Runtime runtime) {

		this.sketchInputFilePath = sketchInputFilePath;
		this.sketchInputWriter = sketchInputWriter;
	}
	
	/**
	 * Invokes the MIT Sketch Synthesizer process on a given sketch script
	 * 
	 * @param script the text input into sketch
	 * @return The standard output stream of the sketch process
	 * @throws IOException - if error executing sketch process
	 */
	public InputStream invokeSketch(String script) throws IOException {

		this.writeContentsToInputFile(script);

		this.execSketch();

		this.logSketchStdError();

		return this.sketchProc.getInputStream();
	}
	
	public InputStream getSketchProc(Object script) throws IOException {
		return this.getSketchProc(script.toString());
	}

	private void writeContentsToInputFile(String contents) throws IOException {

		logger.debug("Writing sketch input to " + this.sketchInputFilePath);

		this.sketchInputWriter.write(contents);
		this.sketchInputWriter.close();
	}

	private void execSketch() throws IOException {

		String sketchCmd = SKETCH_PROC_NAME + " " + this.sketchInputFilePath;
		this.sketchProc = this.runtime.exec(sketchCmd, null);
	}

	private void logSketchStdError() {

		InputStream stderr = this.sketchProc.getErrorStream();
		Scanner errScnr = new Scanner(stderr);
		errScnr.useDelimiter("\\A");
		while (errScnr.hasNext()) {
			logger.warn("Sketch process stderr: " + errScnr.nextLine());
		}
		errScnr.close();
	}
}
