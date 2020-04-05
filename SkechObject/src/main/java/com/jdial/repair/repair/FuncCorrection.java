package repair;

import java.util.Map;

import json_input.Trace;

public class FuncCorrection extends ProgramRepair {
	private Map<Trace, Integer> funcTraceCorrections;
	private String targetFunc;
	
	public FuncCorrection(Map<Trace, Integer> funcTraceCorrections,
						  String targetFunc) {
		this.setFuncTraceCorrections(funcTraceCorrections);
		this.setTargetFunc(targetFunc);
	}

	public Map<Trace, Integer> getFuncTraceCorrections() {
		return funcTraceCorrections;
	}
	public void setFuncTraceCorrections(Map<Trace, Integer> funcTraceCorrections) {
		this.funcTraceCorrections = funcTraceCorrections;
	}

	public String getTargetFunc() {
		return targetFunc;
	}
	public void setTargetFunc(String targetFunc) {
		this.targetFunc = targetFunc;
	}
}
