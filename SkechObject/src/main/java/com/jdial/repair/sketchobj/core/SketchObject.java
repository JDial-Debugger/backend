package sketchobj.core;

import java.util.List;

import coefficient.Coefficient;
import constraintfactory.ConstData;

public abstract class SketchObject {
	
	/**
	 * To avoid constructing factory instances for every node in the ast, this service instance
	 * automatically creates them. Downside is that every node will have all factory instances
	 * regardless if they need them. Other option is creating static factory fields but I want to
	 * avoid that for now. 
	 * Open to ideas for a better design idea here.
	 *
	 */
	protected class Service {

		public Service() {

		}
	}
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
