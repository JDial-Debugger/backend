package repair;

public class InputReaderFactory {
	public InputReaderFactory() {
		
	}
	
	public InputReader getInputReader() {
		return new InputReader(new InputFileScannerFactory());
	}

}
