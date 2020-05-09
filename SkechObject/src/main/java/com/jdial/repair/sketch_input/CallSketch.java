package sketch_input;
import java.io.*;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CallSketch {
	
	private static final Logger logger = LoggerFactory.getLogger(CallSketch.class);

	public CallSketch() {}
	
	/**
	 * Invokes the MIT Sketch Synthesizer process on a given sketch script
	 * @param sketchInput the text input into sketch
	 * @return The standard output stream of the sketch process
	 */
	public static InputStream getSketchProc(SketchScript script) {

		//write the sketch script to a temporary file
		String suggestDIR = System.getenv("SUGGEST_PATH");
		
		//TODO find out what this does
		
		//Create a file to save the sketch script to
		File sketchIODir = new File(suggestDIR + "tmp");
		sketchIODir.mkdirs();
		File sketchInputFile = new File(sketchIODir, "tmp.txt");
		
		try {
			sketchInputFile.createNewFile();
		} catch (IOException e1) {
			logger.error("Unable to create temporary sketch file, is file in use?");
			logger.debug(e1.toString());
		}
		try {
			writeStringToFile(sketchInputFile, script.toString());
		} catch (IOException e1) {
			logger.error("Unable to write contents to temporary sketch file, is file in use?");
			logger.debug(e1.toString());
		}
		
		Runtime rt = Runtime.getRuntime();
		Process sketchProc = null;
		String sketchCmd = "sketch " + suggestDIR + "/tmp/tmp.txt"; 
		try {
			sketchProc = rt.exec(sketchCmd, null);
		} catch (IOException e) {
			logger.error("Unable to execute sketch process");
			logger.debug(e.toString());
		}
		
		InputStream stderr = sketchProc.getErrorStream();
		Scanner errScnr = new Scanner(stderr);
		errScnr.useDelimiter("\\A");
		while (errScnr.hasNext()) {
			logger.warn("Sketch process stderr: " + errScnr.nextLine());
		}
		errScnr.close();
		
		return sketchProc.getInputStream();
	}

	private static void writeStringToFile(File file, String contents) throws IOException {
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(contents);
		fileWriter.close();
	}
}
