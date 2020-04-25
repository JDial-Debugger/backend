package sketchobj.core;

import java.util.List;

import sketch_input.Coefficient;
import sketchobj.expr.Expression;

public class TypeArray extends Type {
    private final Type base;
    private Expression length;
    //private final List<SketchExpr> dims;
	public TypeArray(Type base, Expression length){
		this.base = base;
		this.length = length;
	}

	public void setLenghth(Expression length){
		this.length = length;
	}
	
    public String toString()
    {
        String s = "";
        if (length != null) {
            s = length.toString();
        }
        return this.getBase() + "[" + s + "]";
    }

	public Type getBase() {
		return base;
	}

	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {}
}