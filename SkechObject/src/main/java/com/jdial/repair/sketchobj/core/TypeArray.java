package sketchobj.core;

import java.util.List;

import sketch.input.Coefficient;
import sketchobj.expr.Expression;

public class TypeArray extends Type {
	
    private final Type base;
    private Expression length;
    
	public TypeArray(Type base, Expression length){
		this.base = base;
		this.length = length;
	}
	
	public TypeArray(Type base) { 
		this(base, null);
	}

	public void setLenghth(Expression length){
		this.length = length;
	}
	
    public String toString() {
        String s = "";
        if (length != null) {
            s = length.toString();
        }
        return this.getBase() + "[" + s + "]";
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((base == null) ? 0 : base.hashCode());
		result = prime * result + ((length == null) ? 0 : length.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TypeArray other = (TypeArray) obj;
		if (base == null) {
			if (other.base != null)
				return false;
		} else if (!base.equals(other.base))
			return false;
		if (length == null) {
			if (other.length != null)
				return false;
		} else if (!length.equals(other.length))
			return false;
		return true;
	}

	public Type getBase() {
		return base;
	}

	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {}
}