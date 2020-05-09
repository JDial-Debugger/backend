package sketchobj.stmts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import constraintfactory.ConstData;
import constraintfactory.ConstraintFactory;
import constraintfactory.ExternalFunction;
import global.Global;
import sketch_input.Coefficient;
import sketchobj.core.*;
import sketchobj.expr.ExprConstant;
import sketchobj.expr.ExprFuncCall;
import sketchobj.expr.Expression;

public class StmtFor extends Statement {
	
	private Expression cond;
	private Statement init, incr, body;

	public StmtFor(
			Statement init, 
			Expression cond, 
			Statement incr, 
			Statement body, 
			boolean isCanonical, 
			int lineNumber) {
		
		this.init = init;
		init.setParent(this);
		this.cond = cond;
		cond.setParent(this);
		this.incr = incr;
		incr.setParent(this);
		this.body = body;
		body.setParent(this);
		this.setLineNumber(lineNumber);
	}
	
	public StmtFor(
			Statement init, 
			Expression cond, 
			Statement incr, 
			Statement body) {
		this(init, cond, incr, body, false, 0);
	}

	// rp added
	public Expression getCond() {
		return cond;
	}

	public void setCond(Expression cond) {
		this.cond = cond;
	}

	public Statement getInit() {
		return init;
	}

	public void setInit(Statement init) {
		this.init = init;
	}

	public Statement getIncr() {
		return incr;
	}

	public void setIncr(Statement incr) {
		this.incr = incr;
	}

	public Statement getBody() {
		return body;
	}

	public void setBody(Statement body) {
		this.body = body;
	}

	@Override
	public StmtFor clone() {
		
		return new StmtFor(init.clone(), cond.clone(), incr.clone(), body.clone(), false, this.getLineNumber());
	}
	
	public String toString() {
		String result = null;
		if (incr.toString().endsWith(";"))
			result = "for(" + init.toString() + " " + cond.toString() + "; "
					+ incr.toString().substring(0, incr.toString().length() - 1) + "){\n";
		else
			result = "for(" + init.toString() + " " + cond.toString() + "; "
					+ incr.toString().substring(0, incr.toString().length() - 2) + "){\n";

		result += this.body + "}\n";
		return result;
	}

	@Override
	public ConstData replaceConst(int index) {
		List<SketchObject> toAdd = new ArrayList<SketchObject>();
		toAdd.add(init);
		toAdd.add(incr);
		toAdd.add(body);
		toAdd.add(cond);
		if (cond instanceof ExprConstant) {
			int value = ((ExprConstant) cond).getVal();
			Type t = ((ExprConstant) cond).getType();
			cond = new ExprFuncCall("Const" + index, new ArrayList<Expression>());
			return new ConstData(
					t, toAdd, index + 1, value, null, this.getLineNumber());
		}else
			toAdd.add(cond);
		return new ConstData(null, toAdd, index, 0, null,this.getLineNumber());
	}
	
	@Override
	public ConstData replaceConst_Exclude_This(
			int index, List<Integer> repair_range) {
		List<SketchObject> toAdd = new ArrayList<SketchObject>();
		toAdd.add(init);
		toAdd.add(incr);
		toAdd.add(body);
		return new ConstData(null, toAdd, index, 0, null,this.getLineNumber());
	}

	@Override
	public Context buildContext(Context prectx, int position) {
		
		Global.nestedVars.addAll(init.getVarNames(-1));
		
		prectx = new Context(prectx);
		prectx.setLinenumber(this.getLineNumber());
		this.setPrectx(prectx);
		this.setPostctx(new Context(prectx));
		Context postctx = new Context(prectx);
		postctx.setVarsInScope(new ArrayList<String>());
		
		postctx.pushNewVars();
		int temp = postctx.getLinenumber();
		postctx.setLinenumber(temp);
		postctx = init.buildContext(postctx, position);
		cond.setCtx(postctx);
		postctx = incr.buildContext(postctx, position);
		postctx = body.buildContext(postctx,position +1);
		postctx.popVars();
		postctx.setVarsInScope(new ArrayList<String>());
		return postctx;

	}

	@Override
	public Map<String, Type> addRecordStmt(StmtBlock parent, int index, Map<String, Type> m) {
		
		m.putAll(body.getPostctx().getAllVars());
		StmtBlock sb = new StmtBlock(ConstraintFactory.recordState(this.getPrectx().getLinenumber(), this.getPrectx().getAllVars()),this);
		sb = new StmtBlock(sb, ConstraintFactory.recordState(this.getPostctx().getLinenumber(), this.getPostctx().getAllVars()));
		parent.stmts.set(index, sb);
		body = new StmtBlock(ConstraintFactory.recordState(this.getPostctx().getLinenumber(),
				init.getPostctx().getAllVars()), body);
		body =  new StmtBlock(body,ConstraintFactory.recordState(this.getPostctx().getLinenumber(),
				init.getPostctx().getAllVars()));
		return ((StmtBlock)((StmtBlock) body).stmts.get(0)).stmts.get(((StmtBlock)((StmtBlock) body).stmts.get(0)).stmts.size()-1).addRecordStmt((StmtBlock) body, 1, m);
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

	@Override
	public boolean isBasic() {
		return true;
	}

	@Override
	public List<ExternalFunction> extractExternalFuncs(List<ExternalFunction> externalFuncNames) {
		externalFuncNames = cond.extractExternalFuncs(externalFuncNames);
		externalFuncNames = init.extractExternalFuncs(externalFuncNames);
		externalFuncNames = incr.extractExternalFuncs(externalFuncNames);
		externalFuncNames = body.extractExternalFuncs(externalFuncNames);
		return externalFuncNames ;
	}

	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {
		
		this.cond.setBoolean(true);
		init.insertCoeffs(coeffs);
		incr.insertCoeffs(coeffs);
		body.insertCoeffs(coeffs);
		int startingCoeffsSize = coeffs.size();
		cond.insertCoeffs(coeffs);
		//add this statement as a parent to all added coeffs
		for (int i = startingCoeffsSize; i < coeffs.size(); ++i) {
			coeffs.get(i).setParent(this);
		}
	}

	@Override
	public Map<Integer, String> ConstructLineToString(Map<Integer, String> line_to_string) {
		String result = null;
		if (incr.toString().endsWith(";"))
			result = "for(" + init.toString() + " " + cond.toString() + "; "
					+ incr.toString().substring(0, incr.toString().length() - 1) + "){";
		else
			result = "for(" + init.toString() + " " + cond.toString() + "; "
					+ incr.toString().substring(0, incr.toString().length() - 2) + ")";
		line_to_string.put(this.getLineNumber(), result);
		line_to_string = body.ConstructLineToString(line_to_string);
		return line_to_string;
	}
	
	@Override
	public String toString_Context(){
		String result = null;
		if (incr.toString().endsWith(";"))
			result = "for(" + init.toString_Context() + " " + cond.toString() + "; "
					+ incr.toString_Context() + "){\n";
		else
			result = "for(" + init.toString_Context() + " " + cond.toString() + "; "
					+ incr.toString_Context()+ "){\n";

		result += this.body.toString_Context() + "}\n";
		return result + ": "+this.getPostctx().toString();
	}

	//TODO: unimplemented
	@Override
	public Set<String> getVarNames(int sideFlag) {
		return new HashSet<String>();
	}
	
	@Override
	public Set<String> getActiveVarNames(Set<Type> types) {
		Set<String> result = new HashSet<String>();
		result.addAll(this.getPrectx().getAllVarsFromTypes(types));
		result.addAll(this.getPostctx().getAllVarsFromTypes(types));
		result.addAll(this.init.getActiveVarNames(types));
		result.addAll(this.getBody().getActiveVarNames(types));
		return result;
	}
}