package delegate;

import java.io.Closeable;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Scanner is a final class so it cannot be mocked using vanilla mockito, so we use the delegate
 * pattern to mock derivatives of this class
 */
public abstract class ScannerDelegate implements Iterator<String>, Closeable {
	
	protected Scanner scnr;
	
	public void close() {
		this.scnr.close();
	}
	
	public boolean hasNext() {
		return this.scnr.hasNext();
	}
	
	public String next() {
		return this.scnr.next();
	}
	
	public String nextLine() {
		return this.scnr.nextLine();
	}
	
	public void remove() {
		this.scnr.remove();
	}

}
