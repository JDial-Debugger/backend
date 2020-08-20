package repair;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import sketch.input.Coefficient;
import sketch.input.SketchExecException;
import sketch.input.SketchInvoker;
import sketch.input.SketchScript;
import sketch.output.Parser;
import sketchobj.core.Function;
import sketchobj.core.SketchObject;
import sketchobj.stmts.Statement;
import visitor.JavaVisitor;

public class RepairEngine {
	
	public static final String APP_NAME = "jdial";
	
	private static final String TRACE_POINT_CORRECTION_TYPE = "tracePointCorrection";
	private static final String FUNC_CORRECTION_TYPE = "funcCorrection";
	
	private static final Logger logger = LoggerFactory.getLogger(RepairEngine.class);
	
	private class JsonPropName {
		private static final String CODE = "code";
		private static final String TARGET_FUNC = "targetFunc";
	}
	
	private enum RepairType {
		TRACE_POINT,
		FUNC_CORRECTION
	}
	
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
		
		RepairType type = parseRepairType(args[0]);
		String inputFileName = args[1];
		if (type == null) {
			return;
		}
		
		String json = readInputFile(inputFileName);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		repair(type, json, gson);
	}
	
	
	private static RepairType parseRepairType(String repairType) {
		
		RepairType type = null; 
		if (repairType == TRACE_POINT_CORRECTION_TYPE) {
			type = RepairType.TRACE_POINT;
		} else if (repairType == FUNC_CORRECTION_TYPE) {
			type = RepairType.FUNC_CORRECTION;
		} else {
			logger.error(Errors.invalidRepairType(repairType));
		}
		return type;
		
	}
	
	private static String readInputFile(String inputFileName) {
		
		Scanner scnr = null;
		try {
			scnr = new Scanner(new File(inputFileName));
		} catch (FileNotFoundException e) {
			logger.error("Input file not found " + inputFileName);
			return null;
		}
		
		logger.info("Reading input from " + inputFileName);
		String fileContents = scnr.useDelimiter("\\A").next();
		scnr.close();//
		
		return fileContents;
	}
	
	private static void repair(RepairType repairType, String json, Gson parser) {
		
		JsonObject repairJson = JsonParser.parseString(json).getAsJsonObject();
		
		List<CorrectionExample> examples = createExamplesByRepairType(
				repairType, repairJson, parser);
		
		String code = repairJson.get(JsonPropName.CODE).getAsString();
		String targetFunc = repairJson.get(JsonPropName.TARGET_FUNC).getAsString();
		
		Map<String, Function> relevantFuncs = getRelevantFuncs(examples, code);
		
		SketchScript script = new SketchScript(code, targetFunc, examples, relevantFuncs);
		
		
		InputStream sketchOutput = null;
		try {
			sketchOutput = CallSketch.getSketchProc(script);
		} catch (SketchExecException ex) {
			
		}
		
		Set<Statement> changedStmts = calculateChangedSrcStmts(sketchOutput, script);
		outputLineChanges(changedStmts, parser, System.out);
	}
	
	private static List<CorrectionExample> createExamplesByRepairType(
			RepairType type,
			JsonObject repairJson,
			Gson gson) {
		
		List<CorrectionExample> examples = new ArrayList<CorrectionExample>();
		
		if (type == RepairType.FUNC_CORRECTION) {
			logger.info("Function Correction Repair Initiating...");
			addExamplesByFuncRepair(examples, repairJson, gson);
		} else if (type == RepairType.TRACE_POINT) {
			logger.info("Trace Point Correction Repair Initiating...");
			addExampleByTracePointRepair(examples, repairJson, gson);
		} else {
			throw new IllegalArgumentException("Invalid repair type provided");
		}
		
		return examples;
		
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
		
		Correction[] corrections = gson.fromJson(correctionsJson, Correction[].class);
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
	private static CorrectionExample getExample(
			Correction correction, String targetFunc) {
		
			Trace curTrace = new Trace(Arrays.asList(correction.getTracePoints()));
			int curReturnVal = correction.getReturnVal();
			int curCallLine = correction.getCallLine();
			
			Map<String, Integer> expectedVars = new HashMap<String, Integer>();
			expectedVars.put(Json.RETURN_VAR_NAME, curReturnVal);
			
			curTrace.trimTracePoints(targetFunc, null, curCallLine);
			
			return new CorrectionExample(curTrace, expectedVars);
	}
	
	private static Map<String, Function> getRelevantFuncs(
			List<CorrectionExample> examples, 
			String code) {
		
		Set<String> relevantFuncNames = getRelevantFuncNames(examples);
		logger.debug("Functions found in Traces: " + relevantFuncNames);
		Map<String, Function> relevantFuncs = parseJava(code, relevantFuncNames);
		return relevantFuncs;
	}
	
	/**
	 * Gets all function names invoked during execution of any of the examples
	 * TODO: does not work for overloaded methods
	 * @param examples - the examples to find function invocations in
	 * @return - All function names invoked
	 */
	private static Set<String> getRelevantFuncNames(List<CorrectionExample> examples) {
		Set<String> funcs = new HashSet<String>();
		for (CorrectionExample example : examples) {
			funcs.addAll(example.getProgramTrace().getCalledFuncs());
		}
		return funcs;
	}
	
	private static Set<Statement> calculateChangedSrcStmts(
			InputStream sketchOutput, SketchScript script) {
		
		Set<Coefficient> changedCoeffs = Parser.parseChangedCoeffs(
				sketchOutput, script.getCoefficients());
		
		logger.debug("Changed coefficients: " + changedCoeffs.toString());
		return getChangeStmts(changedCoeffs);
	}
	
	private static Set<Statement> getChangeStmts(Set<Coefficient> coeffs) {
		
		Set<Statement> changeStmts = new HashSet<Statement>();
		for (Coefficient coeff : coeffs) {
			changeStmts.add(coeff.getParentStmt());
		}
		return changeStmts;
		
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
}
