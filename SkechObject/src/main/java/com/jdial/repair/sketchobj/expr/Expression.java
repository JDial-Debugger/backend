package sketchobj.expr;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import constraintfactory.ConstData;
import constraintfactory.ExternalFunction;
import sketch_input.Coefficient;
import sketchobj.core.Context;
import sketchobj.core.SketchNode;
import sketchobj.core.SketchObject;
import sketchobj.core.Type;

public abstract class Expression extends SketchNode{

	protected int lineNumber;
	private boolean isBoolean;
	private boolean isAtom;
	private Context ctx;
	private Type type;
	private boolean LCadded;
	
	@Override
	public ConstData replaceConst(int index, List<Integer> repair_range) {
		if (repair_range.contains(this.lineNumber)){
			return this.replaceConst(index);
			}
			List<SketchObject> toAdd = new ArrayList<SketchObject>();
			return new ConstData(null, toAdd, index, 0, null,this.lineNumber);
	}
	
	@Override
	public abstract void insertCoeffs(List<Coefficient> coeffs);
	
	//TODO get rid of this
	public abstract boolean equals(Expression other);
	
	public abstract List<ExternalFunction> extractExternalFuncs(
			List<ExternalFunction> externalFuncNames);
	
	public abstract Expression clone();
	
	public abstract Set<String> getVarNames();
	
	public abstract ConstData replaceConst(int index, String string);
	
	public abstract void checkAtom();
	
	public Integer getIValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getLineNumber() {
		return this.lineNumber;
	}
	
	public boolean isBoolean() {
		return isBoolean;
	}

	public void setBoolean(boolean isBoolean) {
		this.isBoolean = isBoolean;
	}

	public boolean isAtom() {
		return isAtom;
	}

	public void setAtom(boolean isAtom) {
		this.isAtom = isAtom;
	}

	public Context getCtx() {
		return ctx;
	}

	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(Type t) {
		this.type = t;
	}

	public boolean isLCadded() {
		return LCadded;
	}

	public void setLCadded(boolean lCadded) {
		LCadded = lCadded;
	}
}