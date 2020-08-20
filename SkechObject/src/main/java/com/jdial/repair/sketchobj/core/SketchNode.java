package sketchobj.core;

import java.util.List;

import sketch.input.Coefficient;

public class SketchNode extends SketchObject{

	private SketchNode parent;
	
	
	public SketchNode getParent() {
		return parent;
	}

	public void setParent(SketchNode parent) {
		this.parent = parent;
	}

	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {
	}
	
}
