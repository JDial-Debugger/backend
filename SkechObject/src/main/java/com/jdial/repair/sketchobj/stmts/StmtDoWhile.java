package sketchobj.stmts;

import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

import constraintfactory.ConstData;
import constraintfactory.ExternalFunction;
import sketch.input.Coefficient;
import sketchobj.core.Context;
import sketchobj.core.Type;
import sketchobj.expr.Expression;

public class StmtDoWhile extends Statement {
	Statement body;
	Expression cond;
	int line;

	/** Creates a new while loop. 
	 * @param i */
	public StmtDoWhile(Statement body, Expression cond, int i) {
		this.body = body;
		this.cond = cond;
		this.line = i;
	}

	@Override
	public int size() {
		return body == null ? 0 : body.size();
	}
	
	@Override
	public Statement insertRecordStmt(
			String funcName,
			Type funcType,
			int correctionLine,
			Set<String> correctionVars) {
		this.body = this.getBody().insertRecordStmt(
				funcName, funcType, correctionLine, correctionVars);
		return super.insertRecordStmt(
				funcName, funcType, correctionLine, correctionVars);
	}

	/** Returns the loop body. */
	public Statement getBody() {
		return body;
	}

	/** Returns the loop condition. */
	public Expression getCond() {
		return cond;
	}

	@Override
	public ConstData replaceConst(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Context buildContext(Context ctx, int sp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Type> addRecordStmt(StmtBlock parent, int index, Map<String, Type> m) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean isBasic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ConstData replaceConst_Exclude_This(int index,List<Integer> repair_range) {
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
	public Statement clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString_Context() {
		// TODO Auto-generated method stub
		return null;
	}

	//TODO: not implemented
	@Override
	public Set<String> getVarNames(int sideFlag) {
		return new HashSet<String>();
	}

	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {
		
		this.getBody().insertCoeffs(coeffs);
		
		int startingCoeffsSize = coeffs.size();
		this.getCond().insertCoeffs(coeffs);
		//add this statement as a parent to all added coeffs
		for (int i = startingCoeffsSize; i < coeffs.size(); ++i) {
			coeffs.get(i).setParentStmt(this);
		}
	}
	
	@Override
	public Set<String> getActiveVarNames(Set<Type> types) {
		Set<String> result = new HashSet<String>();
		result.addAll(this.getPrectx().getAllVarsFromTypes(types));
		result.addAll(this.getPostctx().getAllVarsFromTypes(types));
		result.addAll(this.getBody().getActiveVarNames(types));
		return result;
	}
}
