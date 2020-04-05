package json_input;

import java.util.ArrayList;
import java.util.List;


public class VarList {

	private List<Var> var;

	public VarList() {}
	
	public VarList(List<Var> var) {
		this.var = var;
	}
	
	public List<Var> getVar() { return this.var; }

	public List<String> toStringList() {
		List<String> result = new ArrayList<String>();
		for(Var v: var){
			result.add(v.getName());
		}
		return result;
	}
	
	public Var find(String s){
		for(Var v: var){
			if(v.getName().equals(s))
				return v;
		}
		return null;
	}
	
	public String toString(){
		String result = "";
		for(Var v: var){
			result+=v.toString();
		}
		return result;
	}
}
