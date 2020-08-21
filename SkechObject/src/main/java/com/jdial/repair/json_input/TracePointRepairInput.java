package json_input;

import java.util.Map;

public class TracePointRepairInput {
	public Trace trace;
	public int correctionIdx;
	public Map<String, Integer> expectedVars;
	
	public TracePointRepairInput(Trace trace, int correctionIdx, Map<String, Integer> expectedVars) {
		this.trace = trace;
		this.correctionIdx = correctionIdx;
		this.expectedVars = expectedVars;
	}
}
