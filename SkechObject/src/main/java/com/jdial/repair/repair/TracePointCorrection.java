package repair;

import json_input.Trace;
import json_input.TracePoint;

public class TracePointCorrection extends ProgramRepair {
	private Trace trace;
	private TracePoint correction;
	private int correctionIndex;
	
	public TracePointCorrection(Trace trace, TracePoint correction, int correctionIndex) {
		this.setTrace(trace);
		this.setCorrection(correction);
		this.setCorrectionIndex(correctionIndex);
	}

	public int getCorrectionIndex() { return this.correctionIndex; }
	public void setCorrectionIndex(int correctionIndex) { this.correctionIndex = correctionIndex; }

	public TracePoint getCorrection() { return this.correction; }
	public void setCorrection(TracePoint correction) { this.correction = correction; }

	public Trace getTrace() { return this.trace; }
	public void setTrace(Trace trace) { this.trace = trace; }
}
