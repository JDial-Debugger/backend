package repair;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import constants.Errors;
import constants.Json;
import javaparser.simpleJavaLexer;
import javaparser.simpleJavaParser;
import json_input.Correction;
import json_input.Trace;
import json_input.TracePoint;
import sketch_input.CallSketch;
import sketch_input.Coefficient;
import sketch_input.SketchResult;
import sketch_input.SketchScript;
import sketchobj.core.Function;
import sketchobj.core.SketchObject;
import sketchobj.stmts.Statement;
import visitor.JavaVisitor;

public class RepairEngine {
	
	private static final String TRACE_POINT_CORRECTION_TYPE = "tracePointCorrection";
	private static final String FUNC_CORRECTION_TYPE = "funcCorrection";
	private static final Logger logger = LoggerFactory.getLogger(RepairEngine.class);;

	/**
	 * Main entrance point to get program repairs. Takes in JSON input in args[0]
	 * and prints JSON output to stdout. The output json object has a property 
	 * for each changed line number, which maps to a string of the code for that
	 * line number
	 * @param args - The first element is the type of the repair
	 * (tracePointCorrection | funcCorrection) and the second element is the 
	 * JSON input object for the repair.
	 * JSDOC for the input object:
	 * If type == tracePointCorrection:
	 * param {string} input.code - the source code of the program to repair
	 * param {Object[]} input.trace - The trace of the program to repair
	 * param {number} input.correctionIdx - index of the correct trace point
	 * param {Object} input.expectedVars - The correct values of each variable
	 * If type == funcCorrection:
	 * param {string} input.code - the source code of the program to repair
	 * param {string} input.targetFunc - The name of the 
	 * function to repair
	 * param {Object[]} input.corrections - expected
	 * input/output examples for the function
	 * param {Object[]} input.corrections[].trace - a trace
	 * of the function's execution given some parameters 
	 * param {number} input.corrections[].returnVal - the
	 * expected return value of the function
	 * 
	 */
	public static void main(String[] args) {
		
		if (args.length != 2) {
			logger.error(Errors.USAGE);
			return;
		}
		
		
		String repairType = args[0];
		String json = args[1];
		
		Gson gson = new Gson();
		repair(repairType, json, gson);
	}
	
	public static void repair(String repairType, String json, Gson parser) {
		
		JsonObject repairJson = JsonParser.parseString(json).getAsJsonObject();
		String code = repairJson.get("code").getAsString();
		String targetFunc = repairJson.get("targetFunc").getAsString();
		
		List<CorrectionExample> examples = new ArrayList<CorrectionExample>();
		
		if (repairType.equals(TRACE_POINT_CORRECTION_TYPE)) {
			logger.info("Trace Point Correction Repair Initiating...");
			
			addExampleByTracePointRepair(examples, repairJson, parser);
		} else if (repairType.equals(FUNC_CORRECTION_TYPE)) {
			logger.info("Function Correction Repair Initiating...");
			
			addExamplesByFuncRepair(examples, repairJson, parser);
			
		} else {
			logger.error(Errors.invalidRepairType(repairType));
			return;
		}
		
		Set<String> relevantFuncNames = getRelevantFuncs(examples);
		logger.debug("Functions found in Traces: " + relevantFuncNames);
		Map<String, Function> relevantFuncs = parseJava(code, relevantFuncNames);
		SketchScript script = new SketchScript(code, targetFunc, examples, relevantFuncs);
		
		InputStream sketchOutput = CallSketch.getSketchProc(script);
		Set<Coefficient> changedCoeffs = 
				SketchResult.getChangedCoeffs(sketchOutput, script.getCoefficients());
		logger.info(changedCoeffs.toString());
		Set<Statement> changedStmts = getChangeStmts(changedCoeffs);
		outputLineChanges(changedStmts, new Gson(), System.out);
	}
	
	/**
	 * Given a set of coefficients, find the containing statements and outputs
	 * a json object where each property is a line number and each value
	 * is the str value of that statement
	 * @param coeffs - the coefficients to apply changes for
	 */
	private static void outputLineChanges(Set<Statement> stmts, Gson gson, PrintStream output) {
		
		Map<Integer, String> lineToContents = new HashMap<Integer, String>();
		for (Statement stmt : stmts) {
			lineToContents.put(stmt.getLineNumber(), stmt.toString());
		}
		output.println(gson.toJson(lineToContents));
	}
	
	private static Set<Statement> getChangeStmts(Set<Coefficient> coeffs) {
		
		Set<Statement> changeStmts = new HashSet<Statement>();
		for (Coefficient coeff : coeffs) {
			changeStmts.add(coeff.getParentStmt());
		}
		return changeStmts;
		
	}
	
	private static Map<String, Function> parseJava(String source, Set<String> relevantFuncNames) {
		
		ANTLRInputStream input = new ANTLRInputStream(source);
		simpleJavaLexer lexer = new simpleJavaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		simpleJavaParser parser = new simpleJavaParser(tokens);
		Map<String, Function> relevantFuncs = new HashMap<String, Function>();
		
		for (String funcName : relevantFuncNames) {
			SketchObject funcAst = new JavaVisitor(funcName).visit(parser.compilationUnit());
			
			relevantFuncs.put(funcName, (Function) funcAst);
			
		}
		return relevantFuncs;
	}
	
	/**
	 * Gets all function names invoked during execution of any of the examples
	 * TODO: does not work for overloaded methods
	 * @param examples - the examples to find function invocations in
	 * @return - All function names invoked
	 */
	private static Set<String> getRelevantFuncs(List<CorrectionExample> examples) {
		Set<String> funcs = new HashSet<String>();
		for (CorrectionExample example : examples) {
			funcs.addAll(example.getProgramTrace().getCalledFuncs());
		}
		return funcs;
	}
	
	private static void addExampleByTracePointRepair(
			List<CorrectionExample> examples,
			JsonObject repairJson,
			Gson gson) {
		
		TracePoint[] tracePoints = gson.fromJson(
								repairJson.get("trace"), TracePoint[].class);
		Trace trace = new Trace(Arrays.asList(tracePoints));
		
		Integer correctionIdx = gson.fromJson(repairJson.get("correctionIdx"), 
												Integer.class);
		trace.trimTracePoints(correctionIdx);
		
		Type expectedVarsType = new TypeToken<Map<String, Integer>>() {}.getType();
		Map<String, Integer> expectedVars = gson.fromJson(repairJson.get("expectedVars"), expectedVarsType);
		
		examples.add(new CorrectionExample(trace, expectedVars));
	}
	
	private static void addExamplesByFuncRepair(
			List<CorrectionExample> examples, 
			JsonObject repairJson,
			Gson gson) {
		
		JsonElement targetFuncJson = repairJson.get("targetFunc");
		JsonElement correctionsJson = repairJson.get("corrections");
		String targetFunc = gson.fromJson(targetFuncJson, String.class);
		
		Type correctionColType = 
				new TypeToken<Collection<Correction>>() {}.getType();
		Correction[] corrections = gson.fromJson(correctionsJson, 
														Correction[].class);
		for (Correction correction: corrections) {
			
			examples.add(getExample(correction, targetFunc));
		}
		
	}
	/**
	 * Converts a correction and its targetFunction to a general example type
	 * that can be used for the repair
	 * @param correction - the correction to create an example for
	 * @param targetFunc - the function to repair
	 * @return - an example with the necessary data to perform a repair
	 */
	private static CorrectionExample getExample(Correction correction, String targetFunc) {
		
			Trace curTrace = new Trace(Arrays.asList(correction.getTracePoints()));
			int curReturnVal = correction.getReturnVal();
			int curCallLine = correction.getCallLine();
			
			Map<String, Integer> expectedVars = new HashMap<String, Integer>();
			expectedVars.put(Json.RETURN_VAR_NAME, curReturnVal);
			
			curTrace.trimTracePoints(targetFunc, null, curCallLine);
			
			return new CorrectionExample(curTrace, expectedVars);
	}
	
/*	
	private boolean doExecutionsMatchCode(List<ProgramExecution> executions) {
		return false;
	}
*/	
	

}
