package sketch.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import json_input.TracePoint;
import repair.CorrectionExample;
import sketchobj.core.Context;
import sketchobj.core.Function;
import sketchobj.core.Parameter;
import sketchobj.core.Type;
import sketchobj.core.TypeArray;
import sketchobj.core.TypePrimitive;
import sketchobj.core.TypeVoid;
import sketchobj.expr.ExprArrayInit;
import sketchobj.expr.ExprArrayRange;
import sketchobj.expr.ExprBinary;
import sketchobj.expr.ExprConstInt;
import sketchobj.expr.ExprConstant;
import sketchobj.expr.ExprFuncCall;
import sketchobj.expr.ExprUnary;
import sketchobj.expr.ExprVar;
import sketchobj.expr.Expression;
import sketchobj.stmts.StmtBlock;
import sketchobj.stmts.StmtExpr;
import sketchobj.stmts.StmtFor;
import sketchobj.stmts.StmtVarDecl;
import sketchobj.stmts.Statement;
import sketchobj.stmts.StmtAssign;

public class SketchScript {
	
	private String code;
	private String targetFunc; 
	private List<CorrectionExample> examples;
	private Set<Type> correctVarTypes;
	private Map<String, Function> relevantFuncs;
	private List<Coefficient> coeffs;
	//statements that compare every active variable's values with the original
	//values in the trace to compute the semantic distance
	private StmtBlock semDistanceCompares;
	private StmtBlock synDistanceCompares;
	//function decls for coefficients
	private StmtBlock coeffDecls;
	//array decls for record statements
	private StmtBlock stateDecls;
	private Function constraintFunc;
	private StmtBlock originalStateDecls;
	//keeps track of the current index of coeffecients to name them uniquely
	private int coeffNameIdx;
	
	public static final String VAR_PREFIX = "__jdial_";
	public static final String VAR_FINAL_SUFFIX = "_final";
	//In the sketch script, keeps track of the current example
	//to measure program state for each example separately
	public static final String FUNC_INVOKE_COUNT = VAR_PREFIX + "invoke_count";
	
	//	Program State Variable Names
	//for each example, keeps track of the current index of execution in the
	//sketch script (similar to trace point index)
	public static final String STATE_IDX = VAR_PREFIX + "state_idx";
	public static final String LINE_HIT = VAR_PREFIX + "line_hit";
	public static final String LINE_ARRAY = VAR_PREFIX + "line_array";
	public static final String VAR_STATE_SUFFIX = "_state";
	//	Constraint Function Variable Names
	public static final String SEM_DISTANCE = VAR_PREFIX + "semantic_distance";
	public static final String SYN_DISTANCE = VAR_PREFIX + "syntactic_distance";
	public static final String ORIG_PREFIX = VAR_PREFIX + "original_";
	public static final String FINAL_PREFIX = VAR_PREFIX + "final_";
	public static final String MINIMIZE_FUNC = "minimize";
	//TODO add jdial prefix
	public static final String CONSTRAINT_FUNC = "Constraint";
	
	private static final Logger logger = LoggerFactory.getLogger(SketchScript.class);
	
	public static String getVarStateName(String funcName, String varName) {
		return VAR_PREFIX + funcName + "_" + varName + VAR_STATE_SUFFIX;
	}
	public static String getOriginalStateName(String funcName, String varName) {
		return ORIG_PREFIX + funcName + "_" + varName + VAR_STATE_SUFFIX;
	}
	public static String getFinalName(String funcName, String varName) {
		return FINAL_PREFIX + funcName + "_" + varName;
	}
	
	/**
	 * 
	 * @param code
	 * @param examples
	 * @param relevantFuncs
	 */
	public SketchScript(String code,
			String targetFunc,
			List<CorrectionExample> examples, 
			Map<String, Function> relevantFuncs) {
		
		this.code = code;
		this.targetFunc = targetFunc;
		this.examples = examples;
		this.relevantFuncs = relevantFuncs;
		
		this.coeffs = new ArrayList<Coefficient>();
		//Insert coefficients
		for (Map.Entry<String, Function> entry : relevantFuncs.entrySet()) {
			
			this.addContext(entry.getValue());
			entry.getValue().getBody().insertCoeffs(this.coeffs);
			entry.getValue().insertRecordStmt(
					entry.getKey(), 
					entry.getValue().getReturnType(), 
					examples.get(0).getCorrectionLine(), 
					examples.get(0).getCorrectVarValues().keySet());
		}
		
		//Find all types of vars corrected in any example so know what types
		//of expressions we can modify 
		this.correctVarTypes = new HashSet<Type>();
		for (CorrectionExample example : examples) {
			for (ExprConstant expr : example.getCorrectVarValues().values()) {
				this.correctVarTypes.add(expr.getType());
			}
		}
		
		List<Statement> coeffDecls = this.coeffs.stream()
				.map(Coefficient::getDeclFunc)
				.collect(ArrayList<Statement>::new, 
						List<Statement>::addAll, 
						List<Statement>::addAll);
		this.coeffDecls = new StmtBlock(coeffDecls);
		this.addSyntacticCompares(coeffs);
		
		logger.debug("Constructing record state declarations...");
		this.setStateDecls();
		logger.debug("Record State Declarations created:\n" + this.stateDecls);
		logger.debug("Constructing constraint function...");
	}
	
	public List<Coefficient> getCoefficients() { return this.coeffs; }

	/**
	 * Generates a script to input into Sketch based on the current
	 * examples and code in this script
	 * @return - A string containing the entire script to input to Sketch
	 */
	@Override
	public String toString() {
		
		StringBuilder scriptBuilder = new StringBuilder();
		
		scriptBuilder.append(this.coeffDecls);
		scriptBuilder.append(this.stateDecls);
		
		for (Function relevantFunc : this.relevantFuncs.values()) {
			scriptBuilder.append(relevantFunc);
		}
		
		scriptBuilder.append(this.getConstraintFunc());
		
		return scriptBuilder.toString();
	}
	
	/**
	 * Adds statements to calculate the syntactic distance based on how many
	 * coefficients have changed
	 * @param coeffDecls
	 */
	private void addSyntacticCompares(List<Coefficient> coeffs) {
		
		if (this.synDistanceCompares == null) {
			this.synDistanceCompares = new StmtBlock();
			this.synDistanceCompares.addStmt(new StmtVarDecl(
					TypePrimitive.int32type, SYN_DISTANCE, new ExprConstInt(0)));
		}
		
		for (Coefficient coeff: coeffs) {
			this.synDistanceCompares.addStmt(new StmtAssign(
				new ExprVar(SYN_DISTANCE),
				coeff.getChangeVar(),
				ExprBinary.BINOP_ADD,
				0));
		}
	}
	
	/**
	 * 
	 * Between all examples, finds the example with the longest
	 * trace and returns the size
	 * @return - size of the longest trace
	 */
	private int getMaxTraceSize() {
		int max = 0;
		for (CorrectionExample example : this.examples) {
			max = Math.max(max, example.getProgramTrace().getTracePoints().size());
		}
		return max;
	}
	
	/**
	 * Generates statements to initialize global arrays for recording
	 * program state and saves a set of variable names that will be recorded
	 */
	private void setStateDecls() {
		
		if (this.stateDecls == null) {
			this.stateDecls = new StmtBlock();
		}
		
		int arrayLengths = this.getMaxTraceSize();
		
		//initialize variables to hold final states of corrected variables
		for (String finalName : this.getFinalNames()) {
			this.stateDecls.addStmt(new StmtVarDecl(
					TypePrimitive.int32type, finalName, new ExprConstInt(0)));
		}
		
		//creates an array to hold state for every variable in every relevant function
		for (Function func : this.relevantFuncs.values()) {
			for (String varName : func.getBody()
					.getActiveVarNames(this.correctVarTypes)) {
				
				this.addOriginalState(varName, func.getName(), arrayLengths);
				
				List<Expression> invokeInits = new ArrayList<Expression>();
				for (int i = 0; i < this.examples.size(); ++i) {
					
					List<Expression> stateInits = new ArrayList<Expression>();
					for (int j = 0; j < arrayLengths; ++j) {
						stateInits.add(new ExprConstInt(0));
					}
					invokeInits.add(new ExprArrayInit(stateInits));
				}
				
				//WARNING: sketch has a bug where 2d array lengths are flipped 
				//for the declaration type. 
				//Example: int[4][2] = { {5, 4, 1, 9}, {2, 3, 2, 0} };
				Type varStateType = new TypeArray(
						new TypeArray(
							TypePrimitive.int32type, 
							new ExprConstInt(arrayLengths)),
						new ExprConstInt(this.examples.size()));
				this.stateDecls.addStmt(new StmtVarDecl(varStateType, 
						getVarStateName(func.getName(), varName),
						new ExprArrayInit(invokeInits)));
			}
		}
		this.stateDecls.addStmt(new StmtVarDecl(
				TypePrimitive.int32type,
				FUNC_INVOKE_COUNT,
				new ExprConstInt(-1)
				));
	}

	/**
	 * Searches every example and finds all variables that are corrected.
	 * @return - The name of a variable to hold the final value of each found 
	 * correct variable
	 */
	private Set<String> getFinalNames() {
		
		Set<String> result = new HashSet<String>();
		
		for (CorrectionExample example : this.examples) {
			for (String varName : example.getCorrectVarValues().keySet()) {
				
				result.add(getFinalName(this.targetFunc, varName));
			}
		}
		return result;
	}
	
	/**
	 * Sketch requires a 'constraint' function that specifies any constraints
	 * that must be followed when filling in the sketch holes. This function
	 * creates that function and places constraints for each example and 
	 * minimizes the semantic and syntactic distance.
	 */
	private Function getConstraintFunc() {
		
		List<Statement> body = new ArrayList<Statement>();
		//variables to keep track of each variables original state to compare
		//differences
		body.add(this.originalStateDecls);
		//invoke the target function for each example and assert final states
		for (CorrectionExample example : this.examples) {
			List<Expression> paramVals = 
					example.getProgramTrace().getParamVals(this.targetFunc)
					.stream()
					.map(val -> new ExprConstInt(val))
					.collect(Collectors.toList());
			body.add(new StmtExpr(new ExprFuncCall(this.targetFunc, paramVals)));
			body.add(example.getFinalAssertions(this.targetFunc));
		}
		
		body.add(this.semDistanceCompares);
		body.add(this.synDistanceCompares);
		body.add(new StmtExpr(new ExprFuncCall(MINIMIZE_FUNC, Arrays.asList(new ExprBinary(
				new ExprVar(SEM_DISTANCE),
				ExprBinary.BINOP_ADD,
				new ExprVar(SYN_DISTANCE))))));
		
		return new Function(
				CONSTRAINT_FUNC, 
				new TypeVoid(), 
				null, 
				new StmtBlock(body), 
				Function.FcnType.Harness);
		
	}
	
	/**
	 * Constructs an array for each active variable to hold its values
	 * in a trace for each example
	 * @param varName - the name of the variable
	 * @param funcName - the name of the function containing the variable
	 * @param traceLength - the size of the array for each example, if the
	 * current example has a length smaller than traceLength, it will pad the
	 * array with 0s
	 */
	private void addOriginalState(
			String varName, String funcName, int traceLength) {
		
		if (this.originalStateDecls == null) {
			this.originalStateDecls = new StmtBlock();
		}
		
		List<Expression> arrayInits = new ArrayList<Expression>();
		//search each example to find trace points where the given variable has
		//a value/is live
		for (int i = 0; i < this.examples.size(); ++i) {
			
			List<TracePoint> exampleTracePts = 
					this.examples.get(i).getProgramTrace().getTracePoints();
			
			List<Expression> stateInits = new ArrayList<Expression>();
			for (int j = 0; j < traceLength; ++j) {
				
				//pad with 0s if given size is larger
				if (j >= exampleTracePts.size()) {
					
					stateInits.add(new ExprConstInt(0));
				} else {
					
					TracePoint cur = exampleTracePts.get(j);
					//if the func name matches and the given var is in scope
					if (cur.getLocals().containsKey(varName) &&
							cur.getFuncName().equals(funcName)) {
						
						stateInits.add(new ExprConstInt(cur.getLocals().get(varName)));
					} else {
						stateInits.add(new ExprConstInt(0));
					}
				}
			}
			arrayInits.add(new ExprArrayInit(stateInits));
		}
		
		//WARNING: sketch has a bug where 2d array lengths are flipped 
		//for the declaration type. Example: int[4][2] = { {5, 4, 1, 9}, {2, 3, 2, 0} };
		Type varStateType = new TypeArray(
				new TypeArray(
					TypePrimitive.int32type, 
					new ExprConstInt(this.getMaxTraceSize())),
				new ExprConstInt(this.examples.size()));
		this.originalStateDecls.addStmt(new StmtVarDecl(varStateType, 
				getOriginalStateName(funcName, varName),
				new ExprArrayInit(arrayInits)));
		//add in statements to compare this original array with the values
		//created in the sketch script
		this.addSemanticCompares(varName, funcName, traceLength);
	}
	
	/**
	 * Add statements to compare a given varable's values in the sketch script 
	 * with the original values in the trace
	 * @param varName - the name of the variable to compute distance for
	 * @param funcName - the name of the function containing the variable
	 * (deals with case of same varirable name in multiple functions)
	 * @param origLength - the length of the original state array
	 */
	private void addSemanticCompares(
			String varName, String funcName, int origLength) {
		
		if (this.semDistanceCompares == null) {
			this.semDistanceCompares = new StmtBlock();
			this.semDistanceCompares.addStmt(new StmtVarDecl(
					TypePrimitive.int32type, SEM_DISTANCE, new ExprConstInt(0)));
		}
		
		String exampleCount = "i";
		String tracepointCount = "j";
		
		//semantic distance += funcNameVarnameState[i][j] != originalFuncNameVarName[i][j]
		Statement compare = new StmtAssign(
				new ExprVar(SEM_DISTANCE), //__jdial_semantic_distance
				new ExprBinary( 
					new ExprArrayRange( // __jdial_funcName_varName_state[i][j]
						new ExprVar(getVarStateName(funcName, varName)), 
						Arrays.asList(
							new ExprArrayRange.RangeLen(
								new ExprVar(exampleCount)), 
							new ExprArrayRange.RangeLen(
								new ExprVar(tracepointCount)))),
					ExprBinary.BINOP_NEQ, //	!=
					new ExprArrayRange(// __jdial_original_func_name_varName_state[i][j]
						new ExprVar(getOriginalStateName(funcName, varName)), 
						Arrays.asList(
							new ExprArrayRange.RangeLen(
								new ExprVar(exampleCount)), 
							new ExprArrayRange.RangeLen(
								new ExprVar(tracepointCount))))
					),
				ExprBinary.BINOP_ADD, //	+=
				0); 
		//Loop through every example and trace point and compare the variable's
		//state with it's original state from the trace
		StmtFor comparesLoop = new StmtFor(
				new StmtVarDecl(
						TypePrimitive.int32type, 
						tracepointCount, 
						new ExprConstInt(0)),
				new ExprBinary(
						new ExprVar(tracepointCount), 
						ExprBinary.BINOP_LT, 
						new ExprConstInt(origLength)),
				new StmtExpr(new ExprUnary(
						ExprUnary.UNOP_PREINC, new ExprVar(tracepointCount))),
				compare
				);
		StmtFor exampleLoop = new StmtFor(
				new StmtVarDecl(
						TypePrimitive.int32type, 
						exampleCount, 
						new ExprConstInt(0)),
				new ExprBinary(
						new ExprVar(exampleCount), 
						ExprBinary.BINOP_LT, 
						new ExprConstInt(this.examples.size())),
				new StmtExpr(new ExprUnary(
						ExprUnary.UNOP_PREINC, new ExprVar(exampleCount))),
				comparesLoop);
		
		//TODO: put all compares in one double loop, currently each compare
		//has its own double loop
		this.semDistanceCompares.addStmt(exampleLoop);
	}
	
	/**
	 * Recursively adds control flow information to each statement
	 * in the body of the function
	 * @param func
	 */
	private void addContext(Function func) {
		
		Context prectx = new Context();
		prectx.setLinenumber(func.getBody().getLineNumber());
		List<Parameter> params = func.getParams();
    	for (Parameter param : params) {
    		prectx.addVar(param.getName(), param.getType());
    	}
		func.getBody().buildContext(prectx, 0);
	}
	
	public StmtBlock getCoeffDecls() {
		return this.coeffDecls;
	}
}
