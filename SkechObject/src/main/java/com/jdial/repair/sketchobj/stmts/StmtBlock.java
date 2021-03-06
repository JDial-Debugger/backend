package sketchobj.stmts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import coefficient.Coefficient;
import constraintfactory.ConstData;
import constraintfactory.ExternalFunction;
import sketchobj.core.Context;
import sketchobj.core.SketchObject;
import sketchobj.core.Type;

public class StmtBlock extends Statement {

	public List<Statement> stmts;

	public StmtBlock(List<Statement> stmts) {
		
		for(Statement s : stmts) {
			s.setParent(this);
		}
		this.stmts = new ArrayList<Statement>(stmts);
	}

	public StmtBlock() {
		this.stmts = new ArrayList<Statement>();
	}

	/** Create a new StmtBlock for a pair of statements. */
	public StmtBlock(Statement stmt1, Statement stmt2) {
		List<Statement> lst = new ArrayList<Statement>(2);
		lst.add(stmt1);
		lst.add(stmt2);
		stmt1.setParent(this);
		stmt2.setParent(this);

		//this.stmts = new ArrayList<>(lst);
		this.stmts = Collections.unmodifiableList(lst);
	}

	public void addStmt(Statement stmt) {
		this.stmts.add(stmt);
	}

	public String toString() {
		String result = "";
		Iterator<Statement> it = stmts.iterator();
		while (it.hasNext()) {
			String tmp = it.next().toString();
			result += tmp + "\n";
		}
		return result;
	}

	public boolean isBlock() {
		return true;
	}

	/** Returns the list of statements of this. */
	public List<Statement> getStmts() {
		return stmts;
	}

	@Override
	public int size() {
		int sz = 0;
		if (stmts != null) {
			for (Statement s : stmts) {
				sz += s.size();
			}
		}
		return sz;
	}

	@Override
	public ConstData replaceConst(int index) {
		List<SketchObject> l = new ArrayList<SketchObject>();
		l.addAll(stmts);
		return new ConstData(null, l, index, 0,null, this.getLineNumber());
	}
	
	@Override
	public ConstData replaceConst_Exclude_This(int index,List<Integer> repair_range) {
		List<SketchObject> l = new ArrayList<SketchObject>();
		l.addAll(stmts);
		return new ConstData(null, l, index, 0,null, this.getLineNumber());
	}

	@Override
	public Context buildContext(Context prectx, int sposition) {
		prectx = new Context(prectx);
		Context postctx = new Context(prectx);
		for (int i = 1; i < this.stmts.size(); i++) {
			postctx = stmts.get(i - 1).buildContext(postctx, sposition);
		}
		postctx = this.stmts.get(this.stmts.size()-1).buildContext(postctx, sposition);

		this.setPostctx(new Context(postctx));
		this.setPrectx(prectx);
		return postctx;
	}
	
	@Override
	public Statement insertRecordStmt(
			String funcName,
			Type funcType,
			int correctionLine,
			Set<String> correctionVars) {
		
		for(int i = 0; i < this.getStmts().size(); ++i) {
			this.getStmts().set(i, this.getStmts().get(i)
					.insertRecordStmt(
							funcName, 
							funcType, 
							correctionLine, 
							correctionVars));
		}
		return this;
	}

	@Override
	public Map<String, Type> addRecordStmt(StmtBlock parent, int index, Map<String, Type> m) {
		for (int i = 0; i < stmts.size(); i++) {
			/*if (Global.prime_mod) {
				if (ConstraintFactory.dupStmt.contains(stmts.get(i))) {
					//System.err.println("dup");
					continue;
				}
				//System.err.println("not");
			}
			if (ConstraintFactory.dupStmt.contains(stmts.get(i))) {
				continue;
			}*/
			m.putAll(stmts.get(i).addRecordStmt(this, i, m));
		}
		return m;
	}

	@Override
	public void insertCoeffs(List<Coefficient> coeffs){
		for (Statement stmt : this.getStmts()) {
			stmt.insertCoeffs(coeffs);
		}
	}

	@Override
	public boolean isBasic() {
		return false;
	}

	@Override
	public List<ExternalFunction> extractExternalFuncs(List<ExternalFunction> externalFuncNames) {
		for(Statement s: stmts){
			externalFuncNames = s.extractExternalFuncs(externalFuncNames);
		}
		return externalFuncNames;
	}

	@Override
	public Map<Integer, String> ConstructLineToString(Map<Integer, String> line_to_string) {		
		for(Statement s: stmts){
			line_to_string = s.ConstructLineToString(line_to_string);
	}
		return line_to_string;
	}

	@Override
	public String toString_Context() {
		String result = "";
		Iterator<Statement> it = stmts.iterator();
		while (it.hasNext()) {
			result += it.next().toString_Context() + "\n";
		}
		return result + ": "+ this.getPostctx().toString();
	}

	@Override
	public Set<String> getVarNames(int sideFlag) {
		Set<String> result = new HashSet<String>();
		for (Statement curStmt : this.getStmts()) {
			result.addAll(curStmt.getVarNames(sideFlag));
		}
		return result;
	}
	
	@Override
	public Set<String> getActiveVarNames(Set<Type> types) {
		
		Set<String> result = new HashSet<String>();
		
		if (this.getPrectx() != null) {
			result.addAll(this.getPrectx().getAllVarsFromTypes(types));
		}
		
		if (this.getPostctx() != null) {
			result.addAll(this.getPostctx().getAllVarsFromTypes(types));
		}
		
		for (Statement stmt : this.getStmts()) {
			result.addAll(stmt.getActiveVarNames(types));
		}
		
		return result;
	}
}
