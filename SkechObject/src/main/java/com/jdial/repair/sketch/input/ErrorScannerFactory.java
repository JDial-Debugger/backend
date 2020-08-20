package sketch.input;

public class ErrorScannerFactory {
	
	public ErrorScannerFactory() {
	}
	
	public ErrorScanner getErrorScanner(Process proc) {
		return new ErrorScanner(proc);
	}
	
	

}
