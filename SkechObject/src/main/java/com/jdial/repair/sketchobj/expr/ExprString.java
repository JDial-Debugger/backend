package sketchobj.expr;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import constraintfactory.ConstData;
import constraintfactory.ExternalFunction;
import sketch.input.Coefficient;

public class ExprString extends Expression{
	private String content;
	
	public ExprString(String content){
		this.content = content;
	}
	
	@Override
	public Expression clone() {
		// TODO Auto-generated method stub
		return null;
	}
	public String toString(){
		return this.content;
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
	public void checkAtom() {}

	//TODO unimplemented
	@Override
	public Set<String> getVarNames() {
		return new HashSet<String>();
	}

	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {}
}
