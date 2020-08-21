package json_input;

public class FuncRepairInput {
	public String targetFunc;
	public FuncCorrection[] corrections;
	
	public FuncRepairInput(String targetFunc, FuncCorrection[] corrections) {
		this.targetFunc = targetFunc;
		this.corrections = corrections;
	}
}
