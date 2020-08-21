package repair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constants.Json;
import json_input.Trace;
import json_input.TracePoint;
import sketch.input.SketchScript;
import sketchobj.expr.ExprBinary;
import sketchobj.expr.ExprConstInt;
import sketchobj.expr.ExprConstant;
import sketchobj.expr.ExprVar;
import sketchobj.stmts.StmtAssert;
import sketchobj.stmts.StmtBlock;

public class CorrectionExample {
	
	private Trace programTrace;
	//Maps a variable to what it's value should be after the repair
	//if this is a return value of a function, the name is __return__
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
	
	public CorrectionExample(Trace programTrace, int expectedReturnVal) {
		HashMap<String, Integer> correctVarVals = new HashMap<String, Integer>();
		correctVarVals.put(Json.RETURN_VAR_NAME, expectedReturnVal);
	}

	/**
	 * For each corrected variable, constructs a statement that asserts
	 * its final variable in the sketch script is equal to its corrected value
	 * @param targetFunc - the target function of the repair
	 * @return - a statement for each corrected variable to compare it to its
	 * actual value
	 */
	public StmtBlock getFinalAssertions(String targetFunc) {
		
		StmtBlock asserts = new StmtBlock();
		for (Map.Entry<String, ExprConstant> correction : 
			this.correctVarValues.entrySet()) {
			
			ExprBinary equals = new ExprBinary(
					new ExprVar(SketchScript.getFinalName(targetFunc, correction.getKey())),
					ExprBinary.BINOP_EQ,
					correction.getValue());
			asserts.addStmt(new StmtAssert(equals));
		}
		return asserts;
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
