package constraintfactory;

import java.util.ArrayList;
import java.util.List;

import json_input.*;
import sketchobj.core.*;
import sketchobj.expr.*;
import sketchobj.stmts.*;

public class AuxMethods {

	/**
	 * For a point in the execution trace, finds the values of the arguments
	 * that the given method is called with.
	 * For example: If the point in the execution trace has a stack frame with
	 * a method called simpleJava somewhere in it, and we are looking for simpleJava
	 * and in this stack frame, simpleJava was called like simpleJava(4, 6), extractArguments 
	 * returns an Expression object for the values 4 and 6
	 * @param traces - the execution trace, in Java object form
	 * @param targetindex - the index in the trace to extract the current method arguments from
	 * @param targetName - the name of the method we are looking for
	 * @return - Each expression represents the value of an argument that was passed into the 
	 * 			method we are looking for. Returns an empty array if the function does not have
	 * 			any arguments.
	 */
	static public List<Expression> extractArguments(Trace traces, int targetindex, String targetName){
		List<Expression> result = new ArrayList<>();
		
		List<TracePoint> tracelist = traces.getTraces();
		//the function call of the function containing the correction
		TracePoint callTrace = null;
		
		//added to handle recursion, keep searching from back until Funcname changes
		/*for(int i = targetindex; i >=0; i--){
			if(tracelist.get(i).getEvent().equals("call")){
				callTrace = tracelist.get(i);
				// added
				//String name = callTrace.getFuncname();
				for (int j = i; j >=0; j--){
					if (tracelist.get(j).getFuncname().equals(name)){
						callTrace = tracelist.get(j);
					} else {
						break;
					}
				}
				break;
			}
		}*/
		
		//goes back thru each trace from point of correction to beginning
		//and finds the trace point that calls the function that the correction is in
		for(int i = targetindex; i >=0; i--){
			if(tracelist.get(i).getEvent().equals("call") && 
					tracelist.get(i).getFuncName().equals(targetName)){
				callTrace = tracelist.get(i);
			}
		}
		
		List<Var> args = callTrace.getLocals().getVar();
		List<Var> heapObjs = callTrace.getHeap().getVar();
		
		for(Var v: args){
			if(v.getType() == 0){
				result.add(new ExprString(v.getValue().toString()));
			}
			if(v.getType() == 1){
				Integer heapIndex = v.getValue();
				for(Var h: heapObjs){
					if(h.getName().equals(heapIndex.toString())){
						result.add(new ExprString(h.getListasString()));
						break;
					}
				}
				
			}
			if(v.getType() == 3){
				//TODO error handling
			}
		}
		
		
		return result;
	}
	
	static public String listToString(List l){
		String result = "[" + l.get(0).toString();
		for(int i = 1; i < l.size(); i++){
			result += "," +l.get(i);
		}
		return result + "]";
		
	}
}
