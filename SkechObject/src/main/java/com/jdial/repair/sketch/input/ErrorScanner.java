package sketch.input;

import java.io.InputStream;
import java.util.Scanner;

import delegate.ScannerDelegate;

public class ErrorScanner extends ScannerDelegate {
	
	public ErrorScanner(Process sketchProcess) {
		InputStream stderr = sketchProcess.getErrorStream();
		this.scnr = new Scanner(stderr);
		this.scnr.useDelimiter("\\A");
	}
}
