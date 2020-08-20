package sketch.input;

import java.io.InputStream;
import java.util.Scanner;

public class ErrorScanner {
	
	private Scanner errScnr;
	
	public ErrorScanner(Process sketchProcess) {
		InputStream stderr = sketchProcess.getErrorStream();
		this.errScnr = new Scanner(stderr);
		this.errScnr.useDelimiter("\\A");
	}
	
	public boolean hasNext() {
		return this.errScnr.hasNext();
	}
	
	public String nextLine() {
		return this.errScnr.nextLine();
	}
	
	public void close() {
		this.errScnr.close();
	}

}
