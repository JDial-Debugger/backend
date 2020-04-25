package sketchobj.stmts;

import java.util.List;
import java.util.Map;
import java.util.Set;

import constraintfactory.ConstData;
import constraintfactory.ExternalFunction;
import sketchobj.core.Context;
import sketchobj.core.SketchNode;
import sketchobj.core.Type;
import sketch_input.Coefficient;

public abstract class Statement extends SketchNode {

	private int lineNumber;
	private boolean sorceCode;
	private Context postctx;
	private Context prectx;

	public abstract boolean isBasic();
	

	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public boolean isSorce() {
		return this.sorceCode;
	}

	public abstract ConstData replaceConst(int index);
	
	@Override
	public abstract void insertCoeffs(List<Coefficient> coeffs);

	public Context getPostctx() {
		return postctx;
	}

	public void setPostctx(Context ctx) {
		this.postctx = ctx;
	}


	public abstract Map<String, Type> addRecordStmt(
			StmtBlock parent, 
			int index, 
			Map<String, Type> m);

	public Context getPrectx() {
		return prectx;
	}

	public void setPrectx(Context prectx) {
		this.prectx = prectx;
	}

	@Override
	public ConstData replaceConst(int index, List<Integer> repair_range) {
		if (repair_range.contains(this.lineNumber)){
			return this.replaceConst(index);
			}
		else
			return this.replaceConst_Exclude_This(index, repair_range);
	}

	public abstract ConstData replaceConst_Exclude_This(
			int index, 
			List<Integer> repair_range);

	public abstract List<ExternalFunction> extractExternalFuncs(
			List<ExternalFunction> externalFuncNames);

	public abstract Map<Integer,String> ConstructLineToString(
			Map<Integer, String> line_to_string);
	
	//TODO get rid of this
	public abstract Statement clone();

	/**
	 * Gets variable names from the specified side in this statement
	 * if the statement does not contain any, returns an
	 * empty set. If the statement cannot be divided into two sides,
	 * returns all variable names
	 * @param sideFlag - set 1 for right side var names, -1 for
	 * left side var names, 0 for both
	 * @return - all variable names contained in the statement
	 */
	public abstract Set<String> getVarNames(int sideFlag);
	
	public String toString_Context(){
		return this.toString()+": "+this.postctx.toString();
	}

	public abstract Context buildContext(Context postctx2, int scopePosition);
}
