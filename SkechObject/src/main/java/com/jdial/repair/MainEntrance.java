import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.PrintWriter;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import constraintfactory.AuxMethods;
import constraintfactory.ConstraintFactory;
import constraintfactory.ExternalFunction;
import global.Global;
import javaparser.simpleJavaLexer;
import javaparser.simpleJavaParser;
import json_input.TracePoint;
import json_input.Trace;
import jsonparser.jsonLexer;
import jsonparser.jsonParser;
import sketchobj.core.FcnHeader;
import sketchobj.core.Function;
import sketchobj.core.SketchObject;
import sketchobj.expr.Expression;
import sketchobj.stmts.Statement;
import sketchobj.stmts.StmtFuncAssert;
import visitor.JavaVisitor;
import visitor.JsonVisitor;
import cfg.*;

import static org.matheclipse.core.expression.F.*;

import org.matheclipse.core.basic.Config;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;

/**
 * An execution trace is a sequence of states that captures the 
 * state of a program at any point during its execution.
 * 
 * MainEntrance represents an entry-point into the backend application.
 * Given an execution trace, and a manipulation to that execution trace,
 * will utilize the Sketch Synthesizer by placing constraints on the 
 * execution trace, to generate modifications for the original source code
 * that generates the execution trace.
 *
 */
public class MainEntrance {
	//The original code's execution trace, in JSON form, in opt trace format specified in
	//https://github.com/pgbovine/OnlinePythonTutor/blob/master/v3/docs/opt-trace-format.md
	private String originalTrace;
	//The single execution trace with a revised set of local variables in JSON form, in opt trace format
	private String manipulation;
	//The index in the original execution trace that the suggestion corrects
	private int indexOfCorrectTrace;

	//The root of AST JSON from originalTrace converted to Java objects
	private Root root;
	//the original source code
	private String code;
	//The function name at the top of the stack frame at the point in the trace at 
	//which the manipulation occurs (e.g. the enclosing function)
	private String targetFunc;
	//A unique list of all function names in the execution trace
	private List<String> function_names;
	//Maps the name of a function in the execution to the object in the AST for the source code
	private Map<String, Function> func_name_to_code;
	//The execution trace, converted from JSON to a Java AST
	private Trace traces;

	private int mod;

	private List<Integer> repair_range;

	private List<String> ori_trace;
	private List<String> target_trace;
	public static boolean iomod = false;
	public List<Integer> indexes = new ArrayList<>();

	/**
	 * The main entrance to the backend application. Initializes by saving the 
	 * execution trace, manipulation, and where in the execution trace that manipulation occurs
	 * @param json - The original execution trace, in raw JSON and in opt trace format specified in
	 * https://github.com/pgbovine/OnlinePythonTutor/blob/master/v3/docs/opt-trace-format.md
	 * @param correctTrace - The single execution trace with a revised set of local variables in 
	 * 						JSON form and in opt trace format
	 * @param indexOfCorrectTrace - The index in the original execution trace that 
	 * 								the suggestion corrects
	 */
	public MainEntrance(String json, String correctTrace, int indexOfCorrectTrace) {
		this(json, correctTrace, indexOfCorrectTrace, 0);
	}
	/**
	 * 
	 * @param json - The original execution trace, in raw JSON and in opt trace format specified in
	 * https://github.com/pgbovine/OnlinePythonTutor/blob/master/v3/docs/opt-trace-format.md
	 * @param correctTrace - The single execution trace with a revised set of local variables in 
	 * 						JSON form and in opt trace format
	 * @param indexOfCorrectTrace - The index in the original execution trace that 
	 * 								the suggestion corrects
	 * @param mod - unknown
	 */
	public MainEntrance(String json, String correctTrace, int indexOfCorrectTrace, int mod) {
		//if the code contains assert calls, traceprinter will add these 
		//assertionsDisabled variables that don't follow json syntax
		json = json.replace("\"Main.$assertionsDisabled\":false", "");
		correctTrace = correctTrace.replace("\"Main.$assertionsDisabled\":false", "");
		
		this.originalTrace = json;
		this.manipulation = correctTrace;
		this.indexOfCorrectTrace = indexOfCorrectTrace;
		this.repair_range = null;
		this.function_names = new ArrayList<String>();
		this.func_name_to_code = new HashMap<>();
		this.mod = mod;
		this.ori_trace = new ArrayList<>();
		this.target_trace = new ArrayList<>();
	}

	public void addindex (int index){ this.indexes.add(index);}
	public void addOriTrace (String ori){ this.ori_trace.add(ori);}
	public void addTargetTrace (String target){ this.target_trace.add(target);}

	/**
	 * Synthesizes a set of changes to the original source code that match the specifications of
	 * the manipulation to the execution trace
	 * @return - Each mapping maps a line number in the source code to the newly synthesized 
	 * 			code for that line number. For example: if JDial synthesizes a new program given
	 * 			a source code, execution trace, and manipulation on the execution trace, and it then
	 * 			synthesizes a new source code and the only change from the original source code is that
	 * 			the code on line 7 was changed from int x = 5 to int x = 8, then this would return a map
	 * 			in the form {7 => int x = 8;
	 */
	public Map<Integer, String> Synthesize() throws InterruptedException, SketchExecException {
		return this.Synthesize(false, false);
	}

	/**
	 * Synthesizes a set of changes to the original source code that match the specifications of
	 * the manipulation to the execution trace
	 * @param useLC - Whether to use linear combinations to solve the manipulation
	 * @return - Each mapping maps a line number in the source code to the newly synthesized 
	 * 			code for that line number. For example: if JDial synthesizes a new program given
	 * 			a source code, execution trace, and manipulation on the execution trace, and it then
	 * 			synthesizes a new source code and the only change from the original source code is that
	 * 			the code on line 7 was changed from int x = 5 to int x = 8, then this would return a map
	 * 			in the form {7 => int x = 8;
	 */
	public Map<Integer, String> Synthesize(boolean useLC) throws InterruptedException, SketchExecException {
		return this.Synthesize(useLC, false);
	}

	/**
	 * Synthesizes a set of changes to the original source code that match the specifications of
	 * the manipulation to the execution trace
	 * @param useLC - Whether to use linear combinations to solve the manipulation
	 * @param oneLine
	 * @return - Each mapping maps a line number in the source code to the newly synthesized 
	 * 			code for that line number. For example: if JDial synthesizes a new program given
	 * 			a source code, execution trace, and manipulation on the execution trace, and it then
	 * 			synthesizes a new source code and the only change from the original source code is that
	 * 			the code on line 7 was changed from int x = 5 to int x = 8, then this would return a map
	 * 			in the form {7 => int x = 8;
	 * @throws InterruptedException
	 * @throws SketchExecException - Occurs when there was an error running sketch on the generated code
	 * 								This is usually because of an exception or error in the source code
	 * 
	 */
	public Map<Integer, String> Synthesize(boolean useLC, boolean oneLine) 
			throws InterruptedException, SketchExecException {
		this.targetFunc = extractFuncName(manipulation);
		//Use a custom built json parser to put the string json in Java objects
		this.root = jsonRootCompile(this.originalTrace);
		this.code = root.getCode().getCode();

		//TODO find out what mod does
		if (oneLine)
			mod = 1;

		List<Expression> args = AuxMethods.extractArguments(root.getTraces(), indexOfCorrectTrace,
				this.targetFunc);
		//trim traces to only traces in targetFunc
		root.getTraces().findSubTraces(this.targetFunc, indexOfCorrectTrace);
		this.traces = root.getTraces();
		
		code = code.replace("\\n", "\n");
		code = code.replace("\\t", "\t");
		
		//Create AST for the source code
		ANTLRInputStream input = new ANTLRInputStream(code);
		Function function = (Function) javaSketchObjectCompile(input, targetFunc);
		CFG cfg = new CFG(function);
		cfg.printCFG();
		Map<Integer, Set<String>> facts = cfg.dataflow();
		Global.facts = facts;
		CFG.GenfeasibleVars();
		CFG.GenAlwaysVars();
		cfg.inilocs();
		if (Global.only_mod)
			cfg.getAltFacts();
		//Now that we have the AST for the source code, map all function names
		//to their function object in the AST
		this.buildFuncNameList();
		//Holds all function objects in the AST that are not the function containing
		//the manipulation
		List<Function> otherFunctions = new ArrayList<>();
		for(int i = 0; i < this.function_names.size(); i++){
			String curName = this.function_names.get(i);
			if(!curName.equals(targetFunc)) {
				otherFunctions.add(this.func_name_to_code.get(curName));
			}
		}
		
		//TODO find out what mod does 
		boolean prime_mod = global.Global.prime_mod;
		boolean rec_mod = global.Global.rec_mod;
		
		TracePoint correctionTrace = jsonTraceCompile(manipulation);
		
		//Convert function assertions to parse tree nodes
		List<StmtFuncAssert> assertionStatements = new ArrayList<StmtFuncAssert>();
		for (String assertion : correctionTrace.getAssertions().getAssertions()) {
			ANTLRInputStream assertionInput = new ANTLRInputStream(assertion.replace("\"", ""));
			assertionStatements.add(javaStatementCompile(assertionInput));
		}
		
		ConstraintFactory cf = new ConstraintFactory(traces, 
													 jsonTraceCompile(manipulation),
													 new FcnHeader(function.getName(), 
															 	   function.getReturnType(), 
															 	   function.getParams()), 
													 args, 
													 mod, 
													 prime_mod,
													 otherFunctions,
													 assertionStatements);
		ConstraintFactory.correctionIndex = this.indexOfCorrectTrace;
		if (this.repair_range != null)
			cf.setRange(this.repair_range);
		String script;
		
		if(iomod){
			for(int i = 0; i < this.ori_trace.size(); i++){
				Root addRoot =  jsonRootCompile(ori_trace.get(i));
				indexOfCorrectTrace = addRoot.getTraces().getLength() - 1;
				addRoot.getTraces().findSubTraces(this.targetFunc, indexOfCorrectTrace);
				Trace addtraces = addRoot.getTraces();
				cf.addOriTraces(addtraces);
				cf.addTargetTrace(jsonTraceCompile(this.target_trace.get(i)));
				
				Root cur_root = jsonRootCompile(ori_trace.get(i));
				List<Expression> cur_args = AuxMethods.extractArguments(cur_root.getTraces(), cur_root.getTraces().getLength() - 1,
						this.targetFunc);
				ConstraintFactory.extra_args.add(cur_args);
			}

			cf.iomod = true;
		}

		if (rec_mod){
			script = cf.getScriptLinearCombination(function.getBody());
		} else {
			script = cf.getScriptLinearCombination(function.getBody());
		}
		script = script.replaceAll("External_", "");
		if (mod != 2) {
			return this.getSourceLineChanges(script);
		}
		
		return null;
		
	}

	/*
	 * Searches for all function names in the execution trace, and adds them 
	 * uniquely to this.function_names. Then maps each function name to its
	 * object in the AST for the original source code
	 */
	private void buildFuncNameList() {
		Root curRoot = jsonRootCompile(this.originalTrace);

		List<TracePoint> traces = curRoot.getTraces().getTraces();
		for(TracePoint trace: traces){
			String name = trace.getFuncName();
			if (name.equals("main"))
				continue;
			if(!this.function_names.contains(name)) {
				this.function_names.add(name);
				
				ANTLRInputStream input1 = new ANTLRInputStream(code);
				Function function = (Function) javaSketchObjectCompile(input1, name);
				this.func_name_to_code.put(name, function);
			}
		}
	}

	private String addGetArrayDistance(int[][] ori, int tarRow, int tarCol, int iValue)
	{
		int row = ori.length;
		int col = ori[0].length;
		/*
		int getArrayDistance(int[m] a1, int[n] a2, int illegalValue)
		{
			int distance = 0;
			distance += (a1[0] != a2[0]);
			for(int i = 1;i<m && i< n && a1[i] != illegalValue && a2[i] != illegalkValue;i++)
			{
				distance += (abs(a1[i], a1[i-1])  !=  abs(a2[i], a2[i-1]) );
			}
			return distance
		}

		int[col][row] ori;
		ori[0] = {};


		int[tarCol][tarRow] tar;
		iValue = ;

		int dis = 0;
		for(int i = 0;i<tarRow && i < row;i++)
		{
			dis += getArrayDistance(ori[i], tar[i], iValue)
		}

		 */
		StringBuilder result = new StringBuilder();
		result.append("int getArrayDistance(int[m] a1, int[n] a2, int illegalValue)\n");
		result.append("{\n");
		result.append("int distance = 0;\n");
		result.append("distance += (a1[0] != a2[0]);\n");
		result.append("for(int i = 1;i<m && i<n && a1[i] != illegalValue && a2[i] != illegalValue;i++)\n");
		result.append("{\n");
		result.append("distance += (abs(a1[i], a1[i-1]) != abs(a2[i], a2[i-1]));\n");
		result.append("}\n");
		result.append("return distance;\n");
		result.append("}\n");
		result.append("int row = "+row+"\n");
		result.append("int col = "+col+"\n");
		result.append("int tarRow = "+tarRow+"\n");
		result.append("int tarCol = "+tarCol+"\n");


		result.append("int[col][row] ori;\n");
		for(int i = 0;i<ori.length;i++)
		{
			StringBuilder tmp = new StringBuilder();
			for(int j = 0;j<ori[i].length;j++)
			{
				tmp.append(ori[i] + ",");
			}
			String temp = tmp.toString().substring(0,tmp.length()-1);
			result.append("ori["+i+"] = {"+temp+"};\n");
		}

		result.append("int[tarCol][tarRow] tar;\n");
		result.append("iValue = "+iValue+";\n");
		result.append("int dis = 0;\n");
		result.append("for(int i = 0;i<tarRow && i < row;i++)\n");
		result.append("{\n");
		result.append("dis += getArrayDistance(ori[i], tar[i], iValue)");
		result.append("}\n");
		return result.toString();
	}
	//added 11/19

	//@1int bfinal = 0; need to know nameOfVar, primes
	//@2int a = 1 + ((Coeff0()) * (Coeff1())); need to know nameOfVar, primes ("Coeff")
	//@3int[3] oringianlaArray = {0,1,1};
	//@4int correctFinal_b = 500; need to know nameOfVar, primes

	private String tranScriptCall(String script)
	{
		StringBuilder result = new StringBuilder();
		int index0 = 0;
		int index1 = 0;
		index1 = script.indexOf("int count = -1;");
		index1 = script.indexOf('\n', index1);
		result.append(script.substring(index0, index1+1));
		index0 = index1 + 1;

		// store the value of the result variable in each iteration
		result.append("int[10] resArray = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};\n");
		// level of recursion
		result.append("int funcCount = -1;\n");

		index1 = script.indexOf('\n', index0);
		index0 = index1+1;
		index1 = script.indexOf('\n', index0);
		result.append(script.substring(index0, index1+1));
		index0 = index1 + 1;

		result.append("(funcCount)++;\n");
		//step 1--done

		index1 = script.indexOf("finalcount = count;", index0);
		System.err.println("index1: " + index1);
		index1 = index1 -2;
		index1 = script.lastIndexOf('\n', index1);

		result.append(script.substring(index0, index1+1));
		index0 = script.indexOf('\n', index1+1);
		String tmpVarname = script.substring(index1+1, index0);
		System.err.println("tmpVarname: " + tmpVarname);
		int indexEqual = tmpVarname.indexOf('=');
		int indexSemi = tmpVarname.indexOf(';');
		tmpVarname = tmpVarname.substring(indexEqual+1, indexSemi).trim();

		result.append("resArray[funcCount] = "+tmpVarname+";\n");
		result.append("finalcount = count;\n");
		result.append("}\n");
		index0 = script.indexOf('}', index1)+2;
		//step 2--done

		index1 = script.indexOf("assert", index0);
		result.append(script.substring(index0, index1));
		index0 = script.indexOf('\n', index1);
		String tmpLine = script.substring(index1, index0);

		int corIndex = tmpLine.indexOf("correctFinal_");
		int parenIndex = tmpLine.indexOf(")");
		String varName = tmpLine.substring(corIndex, parenIndex);

		String tmpAssert = "assert (resArray[0] == "+varName+" || resArray[1] == "+varName+" ||\n" +
				"resArray[2] == "+varName+" || resArray[3] == "+varName+" ||\n" +
				"resArray[4] == "+varName+" || resArray[5] == "+varName+" ||\n" +
				"resArray[6] == "+varName+" || resArray[7] == "+varName+" ||\n" +
				"resArray[8] == "+varName+" || resArray[9] == "+varName+");\n";
		result.append(tmpAssert);
		result.append(script.substring(index0+1));
		//step 3--done


		return result.toString();
	}

	private String tranScript(String script)
	{


		int times = 5;
		int[] primeNumber = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 39};
		ArrayList<Integer> primes = new ArrayList<>();
		int finalValue;
		int maxValue = Integer.MIN_VALUE;
		String nameOfVar;

		//nameOfVar
		int index4 = script.indexOf("@4");
		int last4BN = script.lastIndexOf('\n', index4);
		int index4_ = script.indexOf('_', last4BN);
		int index4space = script.indexOf(' ', index4_);
		nameOfVar = script.substring(index4_+1, index4space);
		//nameOfVar

		//finalValue
		int index4equal = script.indexOf('=', index4_);
		int index4semi = script.indexOf(';', index4equal);
		String tmpFinalValue = script.substring(index4equal+1, index4semi).trim();
		finalValue = Integer.valueOf(tmpFinalValue);
		//finalValue

		//maxValue, primes
		int index3 = 0;
		while ((index3 = script.indexOf("@3", index3+1)) != -1)
		{
			int tmp3BN = script.lastIndexOf('\n', index3);
			int left = script.indexOf('{', tmp3BN);
			int right = script.indexOf('}', tmp3BN);
			String[] tmpArray = script.substring(left+1, right).split(",");
			for(String s : tmpArray)
			{
				int tmpInt = Math.abs(Integer.valueOf(s));
				System.err.println("current num is" + tmpInt);
				maxValue = Math.max(maxValue, tmpInt);
			}
		}
		maxValue = Math.max(maxValue, finalValue) * times;
		int tmpp = 1;
		for(int n : primeNumber)
		{
			tmpp *= n;
			primes.add(n);
			if(tmpp > maxValue)
				break;
		}
		//maxValue, primes





		StringBuilder result = new StringBuilder();
		int lastIndex = 0;
		int index = 0;

		while ((index = script.indexOf('@',lastIndex)) != -1)
		{
			StringBuilder curString = new StringBuilder();
			int lastBN = script.lastIndexOf('\n', index);
			int nextBN = script.indexOf('\n', index);

			result.append(script.substring(lastIndex, lastBN+1));

			if(script.charAt(index+1) == '1')
			{
				curString.append(script.substring(lastBN+1, index));
				curString.append(script.substring(index+2, nextBN+1));
				//@1int bfinal = 0; need to know nameOfVar, primes
				//int b2 = 0;
				for(Integer i : primes)
				{
					String tmp1 = "int "+nameOfVar+i+" = 0;\n";
					curString.append(tmp1);
				}
			}
			else if(script.charAt(index+1) == '2')
			{
				//@2int a = 1 + ((Coeff0()) * (Coeff1()));
				//int a2 =
				// need to know nameOfVar, primes ("Coeff")
				String tmp2iffor = script.substring(lastBN+1, nextBN+1);
				if(tmp2iffor.contains("if") || (tmp2iffor.contains("for")))
				{
					tmp2iffor = tmp2iffor.replaceAll("@2", "");
					curString.append(tmp2iffor);
				}
				else
				{
					curString.append(script.substring(lastBN+1, index));
					curString.append(script.substring(index+2, nextBN+1));
					for(Integer i : primes)
					{
						String curLine = script.substring(lastBN+1, index) + script.substring(index+2, nextBN+1);
						String[] tmp2Array = curLine.split(" ");
						boolean firstIsInt = tmp2Array[0].equals("int");
						boolean isTargetVar = false;
						String varName = "";
						if (firstIsInt)
						{
							isTargetVar = tmp2Array[1].equals(nameOfVar);
							varName = tmp2Array[1];
						}
						else
						{
							isTargetVar = tmp2Array[0].equals(nameOfVar);
							varName = tmp2Array[0];
						}

						curLine = curLine.substring(curLine.indexOf('=')+1, curLine.indexOf(';'));
						int tmpIndex = 0;
						int tmpIndex1 = 0;
						String tmpCurLine = "";
						//int b = (((10 * a) + 100) + ((Coeff2()) * a)) + ((Coeff3()) * (Coeff4()));
						//int a = 1 + ((Coeff0()) * (Coeff1()));
						while ((tmpIndex1 = curLine.indexOf('*', tmpIndex)) != -1)
						{
							int tmpPare = curLine.indexOf(')', tmpIndex1);
							String tmpVar = curLine.substring(tmpIndex1+1, tmpPare).trim();
							if(!tmpVar.contains("Coeff") && !tmpVar.contains("External_"))
							{
								tmpVar = tmpVar+i;
								tmpCurLine += curLine.substring(tmpIndex, tmpIndex1+1) + " "+ tmpVar+")";
								tmpIndex = tmpPare+1;
							}
							else
							{
								tmpCurLine += curLine.substring(tmpIndex, tmpPare+1);
								tmpIndex = tmpPare+1;
							}
						}
						tmpCurLine += curLine.substring(tmpIndex);
						curLine = tmpCurLine;
						String tmp1 = "";
						if(!isTargetVar)
						{
							tmp1 += "int ";
						}
						tmp1 += varName+i+" = (" + curLine + ") % "+i+";\n";
						curString.append(tmp1);
					}
				}
			}
			if(script.charAt(index+1) == '4' || script.charAt(index+1) == '3')
			{
				curString.append(script.substring(lastBN+1, index));
				curString.append(script.substring(index+2, nextBN+1));
			}
			else if(script.charAt(index+1) == '5')
			{
				//assert (b@5final == correctFinal_b);
				//assert (b2 % 2 == correctFinal_b %2)
				for(Integer i : primes)
				{
					String tmp1 = "assert (" + nameOfVar+i +" % "+i+" == "+"correctFinal_"+nameOfVar+" %"+i+");\n";
					curString.append(tmp1);
				}
			}
			lastIndex = nextBN+1;
			result.append(curString.toString());
		}
		result.append(script.substring(lastIndex));

		//delete External_

		String strResult = result.toString();
		strResult = strResult.replace("External_", "");
		return strResult;
		//delete External_
	}
	//Prints repair in a form to be interpreted by frontend in the form 
	//<line number>||||<correct line string>\n
	//Might be beneficial to make this JSON in the future
	private void printRepair(Map<Integer, String> repair) {
		for(int k : repair.keySet() ) {
			String repairedLine = repair.get(k);
			if (repairedLine != null) {
				System.out.println(k + "||||" + repair.get(k).replaceAll("\n", ""));
			}
		}
	}
	
	/**
	 * Maps coeffecient values from sketch back to changes in the original
	 * source code. Prints result to System.out for frontend to use.
	 * @param sketchInput - Input script to sketch
	 * @return - Maps a line number in the source code to a string that is
	 * 			 the generated source code suggestion for that line
	 * @throws InterruptedException - 
	 * @throws SketchExecException - If an error occurs executing sketch on
	 * 								 the given input
	 */
	public Map<Integer, String> getSourceLineChanges(String sketchInput)
			throws InterruptedException, SketchExecException {

		List<ExternalFunction> externalFuncs = ConstraintFactory.externalFuncs;

		// no external Functions
		if (externalFuncs.size() == 0) {

			Map<Integer, Integer> coefToVal = CallSketch.CallByString(sketchInput);
			Map<Integer, String> repair = new HashMap<Integer, String>();
			
			int tmpLine = -1;
			
			for (int coefIdx : coefToVal.keySet()) {
				if (ConstraintFactory.coeffIndexToLine.get(coefIdx) != null 
					&& ConstraintFactory.coeffIndexToLine.get(coefIdx) == tmpLine) 
					continue;
				if(ConstraintFactory.coeffIndexToLine.get(coefIdx) != null)
					tmpLine = ConstraintFactory.coeffIndexToLine.get(coefIdx);
				String stmtString = ConstraintFactory.line_to_string.get(tmpLine);
				repair.put(tmpLine, replaceCoeff(stmtString, coefToVal, ConstraintFactory.coeffIndexToLine, tmpLine));
			}
			printRepair(repair);
			return repair;
		} else {
			boolean consistancy = false;
			List<ExternalFunction> efs = new ArrayList<ExternalFunction>();
			for (ExternalFunction s : externalFuncs) {
				efs.add(s);
			}
			while (!consistancy) {
				String script_ex = sketchInput;
				for (ExternalFunction ef : efs) {
					script_ex = ef.toString() + script_ex;
				}
				consistancy = true;
			}
			return null;
		}
	}

	private String replaceCoeff(String stmtString, Map<Integer, Integer> result,
			Map<Integer, Integer> coeffIndex_to_Line, int tmpLine) {
		
		if (stmtString == null) {
			return null;
		}
		
		List<Integer> rangedCoeff = new ArrayList<Integer>();
		for (int k : coeffIndex_to_Line.keySet()) {
			if (coeffIndex_to_Line.get(k) == tmpLine)
				rangedCoeff.add(k);
		}
		for (int c : rangedCoeff) {
			if (result.containsKey(c))
				stmtString = stmtString.replace("(Coeff" + c + "())", result.get(c).toString());
			else
				stmtString = stmtString.replace("(Coeff" + c + "())", "0");

		}
		stmtString = simplifyByCAS(stmtString);

		return stmtString;
	}

	private String simplifyByCAS(String stmtString) {
		String[] s;
		if (stmtString.substring(0, 2).equals("if")){
			String tmp = stmtString.substring(3, stmtString.length()-1);
			stmtString= "if(" +eval(tmp)+")";
			return stmtString;
		}
		if (stmtString.substring(0, 3).equals("for")) {
			s = stmtString.split(";", 3);
			s[0] = s[0].split("=",2)[0]+"= " + eval(s[0].split("=",2)[1]);
			s[1] = eval(s[1]);
			s[2] = eval(s[2].substring(0, s[2].length()-1));
			return s[0]+";"+s[1]+";"+s[2]+"){";
		}
		s = stmtString.split("=", 2);
		return s[0] + "= " + eval(s[1].substring(0, s[1].length() - 1)) + ";";

	}

	public String simplifyByRegEx(String stmtString) {
		String tmp = "";
		while (!tmp.equals(stmtString)) {
			tmp = stmtString;
			stmtString = stmtString.replaceAll("[(]0( )*[*]( )*[-]?( )*([0-9A-Za-z])*( )*[)]", "0");
			stmtString = stmtString.replaceAll("[(]( )*[-]?( )*([0-9A-Za-z])*( )*[*]( )*[0]( )*[)]", "0");
			stmtString = stmtString.replaceAll("[(]0( )*[+]( )*", "(");
			stmtString = stmtString.replaceAll("( )*[+]( )*0[)]", ")");
			stmtString = stmtString.replaceAll("( )*[-]( )*0[)]", ")");
			stmtString = stmtString.replaceAll("( )*[+]( )*0[;]", ";");
			stmtString = stmtString.replaceAll("( )*[-]( )*0[;]", ";");
			stmtString = stmtString.replaceAll("[(]0[)]", "0");
			stmtString = stmtString.replaceAll("[(]1( )*[*]( )*", "(");
			stmtString = stmtString.replaceAll("( )*[*]( )*1( )*[)]", ")");
			stmtString = stmtString.replaceAll("( )*[*]( )*1( )*[;]", ";");
			stmtString = deleRedPara(stmtString);

		}
		return stmtString;
	}

	public String deleRedPara(String s) {
		int len = s.length();
		for (int k = 2; k < len; k++) {
			for (int i = 0; i <= len - k; i++) {
				if (s.substring(i, i + k).matches("[(]( )*[\\d\\w]*( )*[)]")) {
					s = s.substring(0, i) + s.substring(i + 1, i + k - 1) + s.substring(i + k);
					len = len - 2;
					k = 4;
					i = 0;
					continue;
				}
				if (s.substring(i, i + k).matches("[(]( )*[(][\\w\\W]*[)]()*[)]")) {
					s = s.substring(0, i) + s.substring(i + 1, i + k - 1) + s.substring(i + k);
					len = len - 2;
					k = 4;
					i = 0;
				}
			}
		}
		return s;
	}

	public void setRepairRange(List<Integer> l) {
		this.repair_range = l;
	}

	public String extractFuncName(String trace) {
		TracePoint tr = jsonTraceCompile(trace);
		return tr.getFuncName();
	}

	public static Root jsonRootCompile(String s) {
		ANTLRInputStream input = new ANTLRInputStream(s);
		jsonLexer lexer = new jsonLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		jsonParser parser = new jsonParser(tokens);
		ParseTree tree = parser.json();
		return (Root) new JsonVisitor().visit(tree);
	}

	public static TracePoint jsonTraceCompile(String s) {
		ANTLRInputStream input = new ANTLRInputStream(s);
		jsonLexer lexer = new jsonLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		jsonParser parser = new jsonParser(tokens);
		ParseTree tree = parser.trace();
		return (TracePoint) new JsonVisitor().visit(tree);
	}

	private static simpleJavaParser javaCompile(ANTLRInputStream input) {
		simpleJavaLexer lexer = new simpleJavaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		simpleJavaParser parser = new simpleJavaParser(tokens);
		return parser;
	}
	
	/**
	 * Uses ANTLR 4 to get an AST for an entire single-file java program
	 * @param input - input stream to generate the parse tree and ast from
	 * @param target - Currently the function name containing the manipulation 
	 * 				   is passed in. Not sure why this is: TODO find out
	 * @return - Parse tree node representing the entire program (Usually an entire class)
	 */
	public static SketchObject javaSketchObjectCompile(ANTLRInputStream input, String target) {
		simpleJavaParser parser = javaCompile(input);
		return new JavaVisitor(target).visit(parser.compilationUnit());
	}
	
	/**
	 * Uses ANTLR 4 to get an AST for a single assert statement of a function call
	 * example: assert func1(4, 6) == 7;
	 * @param input - input stream to generate the parse tree and ast from
	 * @return - Parse tree node representing the assert statement
	 */
	public static StmtFuncAssert javaStatementCompile(ANTLRInputStream input) {
		simpleJavaParser parser = javaCompile(input);
		return (StmtFuncAssert) new JavaVisitor(null).visit(parser.assertStatement());
	}

	public String eval(String input) {

		ExprEvaluator util = new ExprEvaluator(false, 100);
		IExpr result = util.evaluate(input);
		return result.toString();
	}

}
