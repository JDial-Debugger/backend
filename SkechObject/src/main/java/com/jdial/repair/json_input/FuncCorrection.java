package json_input;

import com.google.gson.annotations.SerializedName;

import repair.CorrectionExample;

/**
 * Used for function correction repairs, stores the trace and the expected
 * return value, deserialized directly from json
 *
 */
public class FuncCorrection {
	// The trace of the program
	@SerializedName(value = "trace")
	private TracePoint[] points;
	// What the function is expected to return but doesn't
	private int returnVal;
	// The line number the function is called on
	private int callLine;

	public FuncCorrection() {
	}

	public TracePoint[] getTracePoints() {
		return this.points;
	}

	public int getReturnVal() {
		return this.returnVal;
	}

	public int getCallLine() {
		return this.callLine;
	}
	
	public CorrectionExample convertToExample(String targetFuncName) {
		Trace trace = new Trace(this.points);
		trace.removeTracePointsOutsideCallStack(targetFuncName, null, callLine);
		return new CorrectionExample(trace, returnVal);
	}
}
