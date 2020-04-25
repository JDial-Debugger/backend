package sketchobj.core;

import java.util.ArrayList;
import java.util.List;

import sketch_input.Coefficient;

public class ExpressionTuple extends SketchObject{

	public List<Integer> l;
	
	public ExpressionTuple(List<Integer> le){
		this.l = le;
	}
	
	public ExpressionTuple(Integer i1, Integer i2){
		List<Integer> ll = new ArrayList<>();
		ll.add(i1);
		ll.add(i2);
		this.l = ll;
	}
	
	public boolean equals(ExpressionTuple other){
		if(other.l.size() != this.l.size())
			return false;
		
		for(int i = 0; i < l.size(); i++)
		{
			if(!l.get(i).equals(other.l.get(i))){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {}
}
