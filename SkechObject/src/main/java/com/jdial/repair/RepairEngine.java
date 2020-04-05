import java.util.Map;
import com.google.gson.Gson;

import json_input.Trace;
import json_input.TracePoint;
import repair.TracePointCorrection;

public class RepairEngine {

	/**
	 * Main entrance point to get program repairs. Takes in JSON input in args[0]
	 * and prints JSON output to stdout. The output json object has a property 
	 * for each changed line number, which maps to a string of the code for that
	 * line number
	 * @param args - The first element is the JSON input object for the repair.
	 * JSDOC for the input:
	 * param {Object} input - Contains program data necessary for the repair
	 * param {'tracePointCorrection' | 'funcCorrection'} input.type - specifies 
	 * the type of repair
	 * param {Object} input.tracePointRepair - Program data provided if 
	 * the type is a tracePointCorrection
	 * param {Object} input.tracePointRepair.trace - The trace of the program to repair
	 * param {number} input.tracePointRepair.correctPointIdx - index of the correct trace point
	 * param {Object} input.tracePointRepair[].tracePoint - The correct trace 
	 * point the repair should attempt to solve to
	 * param {Object} input.funcCorrectionRepair - Provided if the type is a
	 * funcCorrection
	 * param {string} input.funcCorrectionRepair.targetFunc - The name of the 
	 * function to repair
	 * param {Object[]} input.funcCorrectionRepair.corrections - expected
	 * input/output examples for the function
	 * param {Object} input.funcCorrectionRepair.corrections[].Trace - a trace
	 * of the function's execution given some parameters 
	 * param {number} input.funcCorrectionRepair.corrections[].returnVal - the
	 * expected return value of the function
	 * 
	 */
	public static void main(String[] args) {
		String json = args[0];
		
		

	}
	
	public Map<Integer, String> getTracePointRepair(Trace traceString, TracePoint correctPointString, int correctPointIdx) {
		return null;
	}
	
	public Map<Integer, String> getFuncCorrectionRepair(Map<Trace, Integer> funcTraceCorrections, String targetFunc) {
		return null;
	}
/*	
	private boolean doExecutionsMatchCode(List<ProgramExecution> executions) {
		return false;
	}
*/	
	private TracePointCorrection parseTracePointCorrectionJson(String json) {
		return null;
	}
	

}
