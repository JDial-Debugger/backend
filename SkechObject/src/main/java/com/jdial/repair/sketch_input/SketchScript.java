package sketch_input;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import repair.CorrectionExample;
import sketchobj.core.Context;
import sketchobj.core.Function;
import sketchobj.core.Parameter;
import sketchobj.stmts.StmtBlock;
import sketchobj.stmts.Statement;

public class SketchScript {
	
	private String code;
	private List<CorrectionExample> examples;
	private Map<String, Function> relevantFuncs;
	private StmtBlock coeffDecls;
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
	
	public SketchScript(String code, 
			List<CorrectionExample> examples, 
			Map<String, Function> relevantFuncs) {
		this.code = code;
		this.examples = examples;
		
		List<Coefficient> coeffs = new ArrayList<Coefficient>();
		//Insert coefficients
		for (Map.Entry<String, Function> entry : relevantFuncs.entrySet()) {
			this.addContext(entry.getValue());
			entry.getValue().getBody().insertCoeffs(coeffs);
		}
		List<Statement> coeffDecls = coeffs.stream()
				.map(Coefficient::getDeclFunc)
				.collect(ArrayList<Statement>::new, 
						List<Statement>::addAll, 
						List<Statement>::addAll);
		
		this.coeffDecls = new StmtBlock(coeffDecls);
		this.relevantFuncs = relevantFuncs;
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
