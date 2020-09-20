package sketchobj.expr;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import constraintfactory.ConstData;
import constraintfactory.ExternalFunction;
import sketch.input.Coefficient;

/**
 * Represents a sketch hole (???) aka an expression for sketch to insert a value
 *
 */
public class ExprSketchHole extends Expression {
	public ExprSketchHole() {

	}

	public String toString() {
		return "??";
	}

	@Override
	public ConstData replaceConst(int index) {
		return null;
	}
	@Override
	public Expression clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConstData replaceConst(int index, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExternalFunction> extractExternalFuncs(List<ExternalFunction> externalFuncNames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkAtom() {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {}
	
	//TODO unimplemented
	@Override
	public Set<String> getVarNames() {
		return new HashSet<String>();
	}
}