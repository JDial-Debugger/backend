package sketchobj.core;

import java.util.List;

import constraintfactory.ConstData;
import sketch_input.Coefficient;

public abstract class SketchObject {
	
	protected int indentation;	// for toString
	private int line;
	
	public int getIndentation() {
		return indentation;
	}

	public void setIndentation(int indentation) {
		this.indentation = indentation;
	}

	public ConstData replaceConst(int index) {
		return new ConstData(index,this.line);
	}

	public ConstData replaceConst(int index, List<Integer> repair_range) {
		return null;
	}

	public abstract void insertCoeffs(List<Coefficient> coeffs);
}
