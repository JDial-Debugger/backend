package json_input;

import repair.VarCorrections;

public class TracePointRepairInput {
	public Trace trace;
	public int correctionIdx;
	public VarCorrections expectedVars;
	
	public TracePointRepairInput(Trace trace, int correctionIdx, VarCorrections expectedVars) {
		this.trace = trace;
		this.correctionIdx = correctionIdx;
		this.expectedVars = expectedVars;
	}
}
