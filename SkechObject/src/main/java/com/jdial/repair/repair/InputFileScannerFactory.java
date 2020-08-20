package repair;

public class InputFileScannerFactory {
	public InputFileScannerFactory() {
	}
	
	public InputFileScanner getInputFileScanner(String fileName) {
		return new InputFileScanner(fileName);
	}

}
