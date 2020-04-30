package repair;

import java.util.List;
import java.util.Map;

import json_input.Trace;
import json_input.TracePoint;

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
	
	/**
	 * Gets the line number in the source code that the correction occurs on
	 * @return - the number of the line
	 */
	public int getCorrectionLine() {
		List<TracePoint> points = this.getProgramTrace().getTracePoints();
		return points.get(points.size() - 1).getLine();
				
	}
}
