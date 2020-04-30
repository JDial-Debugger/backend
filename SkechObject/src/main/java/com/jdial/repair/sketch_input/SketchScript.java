package sketch_input;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import repair.CorrectionExample;
import sketchobj.core.Context;
import sketchobj.core.Function;
import sketchobj.core.Parameter;
import sketchobj.core.Type;
import sketchobj.core.TypeArray;
import sketchobj.core.TypePrimitive;
import sketchobj.expr.ExprArrayInit;
import sketchobj.expr.ExprConstInt;
import sketchobj.expr.ExprConstant;
import sketchobj.expr.Expression;
import sketchobj.stmts.StmtBlock;
import sketchobj.stmts.StmtVarDecl;
import sketchobj.stmts.Statement;

public class SketchScript {
	
	private String code;
	private List<CorrectionExample> examples;
	private Set<Type> correctVarTypes;
	private Map<String, Function> relevantFuncs;
	//function decls for coefficients
	private StmtBlock coeffDecls;
	//array decls for record statements
	private StmtBlock stateDecls;
	//keeps track of the current index of coeffecients to name them uniquely
	private int coeffNameIdx;
	
	public static final String VAR_PREFIX = "__jdial_";
	public static final String VAR_FINAL_SUFFIX = "_final";
	//In the sketch script, keeps track of the current example
	//to measure program state for each example separately
	public static final String FUNC_INVOKE_COUNT = VAR_PREFIX + "invoke_count";
	//for each example, keeps track of the current index of execution in the
	//sketch script (similar to trace point index)
	public static final String STATE_IDX = VAR_PREFIX + "state_idx";
	public static final String LINE_HIT = VAR_PREFIX + "line_hit";
	public static final String LINE_ARRAY = VAR_PREFIX + "line_array";
	public static final String VAR_STATE_SUFFIX = "_state";
	
	private static final Logger logger = LoggerFactory.getLogger(SketchScript.class);
	
	public static String getVarStateName(String funcName, String varName) {
		return VAR_PREFIX + funcName + "_" + varName + VAR_STATE_SUFFIX;
	}
	
	/**
	 * 
	 * @param code
	 * @param examples
	 * @param relevantFuncs
	 */
	public SketchScript(String code, 
			List<CorrectionExample> examples, 
			Map<String, Function> relevantFuncs) {
		
		this.code = code;
		this.examples = examples;
		this.relevantFuncs = relevantFuncs;
		
		List<Coefficient> coeffs = new ArrayList<Coefficient>();
		//Insert coefficients
		for (Map.Entry<String, Function> entry : relevantFuncs.entrySet()) {
			
			this.addContext(entry.getValue());
			entry.getValue().getBody().insertCoeffs(coeffs);
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
		
		List<Statement> coeffDecls = coeffs.stream()
				.map(Coefficient::getDeclFunc)
				.collect(ArrayList<Statement>::new, 
						List<Statement>::addAll, 
						List<Statement>::addAll);
		this.coeffDecls = new StmtBlock(coeffDecls);
		
		this.setStateDecls();
		logger.debug(this.stateDecls.toString());
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
	 * program state
	 */
	private void setStateDecls() {
		this.stateDecls = new StmtBlock();
		int arrayLengths = this.getMaxTraceSize();
		for (Function func : this.relevantFuncs.values()) {
			for (String varName : func.getBody()
					.getActiveVarNames(this.correctVarTypes)) {
				
				List<Expression> invokeInits = new ArrayList<Expression>();
				for (int i = 0; i < this.examples.size(); ++i) {
					
					List<Expression> stateInits = new ArrayList<Expression>();
					for (int j = 0; j < arrayLengths; ++j) {
						stateInits.add(new ExprConstInt(0));
					}
					invokeInits.add(new ExprArrayInit(stateInits));
				}
				
				Type varStateType = new TypeArray(
						new TypeArray(
							TypePrimitive.int32type, 
							new ExprConstInt(this.examples.size())),
						new ExprConstInt(arrayLengths));
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
	 * Recursively adds context to each statement
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
