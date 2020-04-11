package repair;

import java.util.Map;

import json_input.Trace;

public class CorrectionExample {
	private Trace programTrace;
	private Map<String, Integer> correctVarValues;
	
	public CorrectionExample(Trace programTrace, 
							Map<String, Integer> correctVarVals) {
		this.programTrace = programTrace;
		this.correctVarValues = correctVarVals;
	}

	public Trace getProgramTrace() {
		return this.programTrace;
	}

	public Map<String, Integer> getCorrectVarValues() {
		return this.correctVarValues;
	}

}
