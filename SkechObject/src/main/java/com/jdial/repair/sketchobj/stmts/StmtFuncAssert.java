package sketchobj.stmts;

import java.util.List;
import java.util.Map;

import constraintfactory.ConstData;
import constraintfactory.ExternalFunction;
import sketchobj.core.Context;
import sketchobj.core.Type;
import sketchobj.expr.ExprFunCall;
import sketchobj.expr.Expression;

/**
 * Used to represent an assertion for a function call. These should not be 
 * used for the parse tree of an actual program's code, but used to enforce
 * assertions for JDial's function assertion feature 
 *
 */
public class StmtFuncAssert extends Statement {
	private ExprFunCall exprFunCall;
	private Expression expression;

	/**
	 * Create a node to represent an assertion for a function's return
	 * @param exprFunCall - The function call expression
	 * @param expression - what expression the function should evaluate to
	 * 					   this can be any non function call expression
	 */
	public StmtFuncAssert(ExprFunCall exprFunCall, Expression expression) {
		this.exprFunCall = exprFunCall;
		this.expression = expression;
	}
	
	@Override
	public boolean isBasic() {
		return false;
	}
	
	@Override
	public String toString() {
		return "assert " + this.exprFunCall.toString() + " == " + this.expression.toString();
	}
	
	
	public StmtFuncAssert clone() {
		return new StmtFuncAssert(exprFunCall.clone(), expression.clone());
	}

	//Following methods not needed as this node is never in souce code
	@Override
	public ConstData replaceConst(int index) {
		return null;
	}

	@Override
	public ConstData replaceLinearCombination(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Type> addRecordStmt(StmtBlock parent, int index, Map<String, Type> m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConstData replaceConst_Exclude_This(int index, List<Integer> repair_range) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExternalFunction> extractExternalFuncs(List<ExternalFunction> externalFuncNames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, String> ConstructLineToString(Map<Integer, String> line_to_string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Context buildContext(Context postctx2, int scopePosition) {
		// TODO Auto-generated method stub
		return null;
	}

}