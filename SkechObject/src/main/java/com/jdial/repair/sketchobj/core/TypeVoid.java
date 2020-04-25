package sketchobj.core;

import java.util.List;

import sketch_input.Coefficient;

public class TypeVoid extends Type{
	public TypeVoid(){};
	public String toString(){
		return "void";
	}

	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {}
}
