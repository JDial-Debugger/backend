package repair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constants.Json;
import json_input.Trace;
import json_input.TracePoint;
import sketch.input.SketchScript;
import sketchobj.expr.ExprConstInt;
import sketchobj.expr.ExprConstant;
import sketchobj.expr.ExprVar;
import sketchobj.expr.binary.ExprBinary2;
import sketchobj.stmts.StmtAssert;
import sketchobj.stmts.StmtBlock;

public class CorrectionExample {

	private Trace programTrace;
	private VarCorrections varCorrections;

	/**
	 * Creates an input/output example where the input is the trace of a program and the output is a
	 * collection of variables and what their desired value is. This constructor creates the example
	 * when all the correction variables are of type Integer
	 * 
	 * @param programTrace
	 * @param correctVarVals
	 */
	public CorrectionExample(Trace programTrace, VarCorrections varCorrections) {
		this.programTrace = programTrace;
		this.varCorrections = varCorrections;
	}

	public CorrectionExample(Trace programTrace, int expectedReturnVal) {
		HashMap<String, Integer> correctVarVals = new HashMap<String, Integer>();
		correctVarVals.put(Json.RETURN_VAR_NAME, expectedReturnVal);
	}

	/**
	 * For each corrected variable, constructs a statement that asserts its final variable in the
	 * sketch script is equal to its corrected value
	 * 
	 * @param targetFunc - the target function of the repair
	 * @return - a statement for each corrected variable to compare it to its actual value
	 */
	public StmtBlock getSketchCorrectionAssertions(String targetFunc) {

		StmtBlock asserts = new StmtBlock();
		for (VarCorrections.VarCorrection correction : this.varCorrections) {

			ExprBinary2 equals
				= new ExprBinary2(
					new ExprVar(SketchScript.getFinalName(targetFunc, correction.varName)),
					ExprBinary2.BINOP_EQ,
					correction.valueExpr
				);
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
	 * 
	 * @return - the number of the line
	 */
	public int getCorrectionLine() {
		List<TracePoint> points = this.programTrace.getTracePoints();
		return points.get(points.size() - 1).getLine();

	}
}
