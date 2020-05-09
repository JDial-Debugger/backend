package sketchobj.stmts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import constraintfactory.ConstData;
import constraintfactory.ConstraintFactory;
import constraintfactory.ExternalFunction;
import sketch_input.Coefficient;
import sketchobj.core.Context;
import sketchobj.core.SketchObject;
import sketchobj.core.Type;
import sketchobj.expr.ExprConstant;
import sketchobj.expr.ExprFuncCall;
import sketchobj.expr.Expression;

public class StmtExpr extends Statement {
	
	private Expression expr;

	public StmtExpr(Expression expr, int lineNumber) {
		this.expr = expr;
		expr.setParent(this);
		this.setLineNumber(lineNumber);
	}
	
	public StmtExpr(Expression expr) {
		this(expr, 0);
	}
	
	// rp added auto-gen
	public Expression getExpr() {
		return expr;
	}

	public void setExpr(Expression expr) {
		this.expr = expr;
	}


	public String toString() {
		return expr.toString() + ";";
	}

	@Override
	public ConstData replaceConst(int index) {
		List<SketchObject> toAdd = new ArrayList<SketchObject>();
		if (expr instanceof ExprConstant) {
			int value = ((ExprConstant) expr).getVal();
			Type t = ((ExprConstant) expr).getType();
			expr = new ExprFuncCall("Const" + index, new ArrayList<Expression>());
			return new ConstData(t, toAdd, index + 1, value,null,this.getLineNumber());
		}
		return expr.replaceConst(index);
	}

	@Override
	public ConstData replaceConst_Exclude_This(int index,List<Integer> repair_range) {
		return new ConstData(null, new ArrayList<SketchObject>(), index, 0, null,this.getLineNumber());
	}
	
	@Override
	public Context buildContext(Context prectx, int sp) {
		Context postctx = new Context(prectx);
		prectx = new Context(prectx);
		postctx.setLinenumber(this.getLineNumber());
		prectx.setLinenumber(this.getLineNumber());
		
		this.setPostctx(new Context(postctx));
		this.setPrectx(new Context(prectx));
		return postctx;
	}

	@Override
	public Map<String, Type> addRecordStmt(StmtBlock parent, int index, Map<String, Type> m) {
		parent.stmts.set(index,
				new StmtBlock(ConstraintFactory.recordState(this.getPrectx().getLinenumber(), this.getPrectx().getAllVars()),this));
		m.putAll(this.getPostctx().getAllVars());
		return m;
	}

	@Override
	public boolean isBasic() {
		return true;
	}

	@Override
	public List<ExternalFunction> extractExternalFuncs(List<ExternalFunction> externalFuncNames) {
		return expr.extractExternalFuncs(externalFuncNames);
	}

	@Override
	public Map<Integer, String> ConstructLineToString(Map<Integer, String> line_to_string) {
		line_to_string.put(this.getLineNumber(), this.toString());
		return line_to_string;
	}

	@Override
	public StmtExpr clone() {
		return new StmtExpr(this.expr.clone(), this.getLineNumber());
	}

	@Override
	public Set<String> getVarNames(int sideFlag) {
		return this.getExpr().getVarNames();
	}

	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {
		
		int startingCoeffsSize = coeffs.size();
		this.getExpr().insertCoeffs(coeffs);
		//add this statement as a parent to all added coeffs
		for (int i = startingCoeffsSize; i < coeffs.size(); ++i) {
			coeffs.get(i).setParent(this);
		}
	}
}
