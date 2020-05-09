package sketchobj.stmts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

public class StmtWhile extends Statement {
	Expression cond;
	Statement body;
	
	public StmtWhile(Expression cond, Statement body, int i) {
		this.cond = cond;
		cond.setParent(this);
		this.body = body;
		body.setParent(this);
		this.setLineNumber(i);
	}

	@Override
	public StmtWhile clone() {
		return new StmtWhile(this.cond, this.body, this.getLineNumber());
	}
	
	/** Returns the loop condition. */
	public Expression getCond() {
		return this.cond;
	}

	/** Returns the loop body. */
	public Statement getBody() {
		return this.body;
	}

	public String toString() {
		return "while(" + getCond() + "){\n" + getBody() + "\n}";
	}
	public String toString_Context() {
		return "while(" + getCond() + "){\n" + getBody().toString_Context() + "\n}: " + this.getPostctx().toString();
	}
	@Override
	public ConstData replaceConst(int index) {
		List<SketchObject> toAdd = new ArrayList<SketchObject>();
		if (cond instanceof ExprConstant) {
			int value = ((ExprConstant) cond).getVal();
			Type t = ((ExprConstant) cond).getType();
			cond = new ExprFuncCall("Const" + index, new ArrayList<Expression>());
			toAdd.add(this.body);
			return new ConstData(t, toAdd, index + 1, value, null,this.getLineNumber());
		}
		toAdd.add(this.cond);
		toAdd.add(this.body);
		return new ConstData(null, toAdd, index, 0, null,this.getLineNumber());
	}
	@Override
	public ConstData replaceConst_Exclude_This(int index, List<Integer> repair_range) {
		List<SketchObject> toAdd = new ArrayList<SketchObject>();
		toAdd.add(cond);
		toAdd.add(body);
		return new ConstData(null, toAdd, index, 0, null,this.getLineNumber());
	}

	@Override
	public Context buildContext(Context prectx, int sp) {
		prectx = new Context(prectx);
		prectx.setLinenumber(this.getLineNumber());
		Context postctx  = new Context(prectx);
		this.setPrectx(prectx);
		this.setPostctx(new Context(postctx));
		postctx.pushVars(new HashMap<String, Type>());
		postctx = this.body.buildContext(postctx, sp+1);
		postctx.popVars();
		postctx.setVarsInScope(new ArrayList<String>());
		return postctx;
	}

	@Override
	public Map<String, Type> addRecordStmt(StmtBlock parent, int index, Map<String, Type> m) {
		parent.stmts.set(index,
				new StmtBlock(ConstraintFactory.recordState(this.getPrectx().getLinenumber(), this.getPrectx().getAllVars()),this));
		this.body = new StmtBlock(this.body,ConstraintFactory.recordState(body.getPostctx().getLinenumber(), body.getPostctx().getAllVars()));
		m.putAll(this.getPostctx().getAllVars());
		return ((StmtBlock)body).stmts.get(0).addRecordStmt((StmtBlock) body,0,m);
	}
	
	@Override
	public Statement insertRecordStmt(
			String funcName,
			Type funcType,
			int correctionLine,
			Set<String> correctionVars) {
		
		this.body = this.getBody().insertRecordStmt(
				funcName, funcType, correctionLine, correctionVars);
		return super.insertRecordStmt(funcName, funcType, correctionLine, correctionVars);
	}

	@Override
	public boolean isBasic() {
		return true;
	}

	@Override
	public List<ExternalFunction> extractExternalFuncs(List<ExternalFunction> externalFuncNames) {
		externalFuncNames = cond.extractExternalFuncs(externalFuncNames);
		externalFuncNames = body.extractExternalFuncs(externalFuncNames);
		return externalFuncNames;
	}

	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {
		
		int startingCoeffsSize = coeffs.size();
		this.getCond().insertCoeffs(coeffs);
		//add this statement as a parent to all added coeffs
		for (int i = startingCoeffsSize; i < coeffs.size(); ++i) {
			coeffs.get(i).setParentStmt(this);
		}
		
		this.getBody().insertCoeffs(coeffs);
	}

	@Override
	public Map<Integer, String> ConstructLineToString(Map<Integer, String> line_to_string) {
		line_to_string.put(this.getLineNumber(), "while(" + getCond() + ")");
		line_to_string = body.ConstructLineToString(line_to_string);
		return line_to_string;
	}

	//TODO: unimplemented
	@Override
	public Set<String> getVarNames(int sideFlag) {
		return new HashSet<String>();
	}



}