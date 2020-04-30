package repair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import json_input.Trace;
import json_input.TracePoint;
import sketchobj.expr.ExprConstInt;
import sketchobj.expr.ExprConstant;

public class CorrectionExample {
	
	private Trace programTrace;
	private Map<String, ExprConstant> correctVarValues;

	/**
	 * Creates an input/output example where the input is the trace of a 
	 * program and the output is a collection of variables and what their
	 * desired value is. This constructor creates the example when all the
	 * correction variables are of type Integer
	 * @param programTrace
	 * @param correctVarVals
	 */
	public CorrectionExample(Trace programTrace, 
							Map<String, Integer> correctVarVals) {
		this.programTrace = programTrace;
		this.correctVarValues = new HashMap<String, ExprConstant>();
		for (Map.Entry<String, Integer> entry : correctVarVals.entrySet()) {
			this.correctVarValues.put(
					entry.getKey(), new ExprConstInt(entry.getValue()));
		}
	}

	public Trace getProgramTrace() {
		return this.programTrace;
	}

	public Map<String, ExprConstant> getCorrectVarValues() {
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
