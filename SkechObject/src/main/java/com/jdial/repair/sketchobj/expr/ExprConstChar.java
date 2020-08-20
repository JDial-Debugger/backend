package sketchobj.expr;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import constraintfactory.ConstData;
import constraintfactory.ExternalFunction;
import sketch.input.Coefficient;
import sketchobj.core.Type;
import sketchobj.core.TypePrimitive;

public class ExprConstChar extends ExprConstant{
	
	private String val;
	public ExprConstChar(String s){
		this.setVal(s);
	}
	@Override
	public ExprConstChar clone() {
		return new ExprConstChar(val);
	}
	@Override
	public Type getType() {
		return new TypePrimitive(17);
	}

	@Override
	public int getVal() {
		// TODO Auto-generated method stub
		return 0;
	}
	
    public String toString()
    {
        return val;
    }
    
	@Override
	public ConstData replaceConst(int index, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Expression other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ExternalFunction> extractExternalFuncs(List<ExternalFunction> externalFuncNames) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setVal(String val) {
		this.val = val;
	}

	@Override
	public void checkAtom() {
		this.setAtom(false);
		
	}
	
	//TODO unimplemented
	@Override
	public Set<String> getVarNames() {
		return new HashSet<String>();
	}

	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {}
}
