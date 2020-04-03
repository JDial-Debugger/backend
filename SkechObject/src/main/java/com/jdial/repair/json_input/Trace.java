package json_input;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jsonparser.jsonParser.TraceContext;

public class Trace extends ProgramState implements Frameable {

	private List<TracePoint> tracePoints;

	public Trace() {}



	public String toString() {
		String result = "";
		int i = 0;
		for (TracePoint t : this.tracePoints) {
			result += "Trace " + i + ": " + t.toString();
		}
		return result;
	}

	@Override
	public Set<String> getCalledFuncs(String callerFunction) {
		Set<String> calledFuncs = new HashSet<String>();
		// TODO LEFT OFF HERE
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Removes all points in the execution trace that are not in the same
	 * call stack as the point with the given index in the trace list.
	 * @param targetFunc - the containing function name
	 * @param bound - the index in the execution trace at which to search
	 */
	@Override
	public void trimTracePoints(String targetFunc, Integer bound) {
		
		List<Integer> toRemove = new ArrayList<Integer>();
		
		//if no bound, use return of targetFunc as bound
		if (bound == null) {
			for (int i = 0; i < tracePoints.size(); ++i) {
				TracePoint tracePoint = tracePoints.get(i);
				if (tracePoint.getFuncName().equals(targetFunc)
						&& tracePoint.getEvent() == Event.RETURN) {
						bound = i;
						break;
					}
			}
		}
		int firstIndex = bound;
		
		for (int i = bound; i >= 0; i--) {
			
			if (this.tracePoints.get(i).getFuncName().equals(targetFunc)) {
				firstIndex = i;
			}
			
			//TODO: find out why this is necessary
			if (!this.tracePoints.get(i).getEvent().equals("step_line") || 
					(i > 0 && tracePoints.get(i-1).getEvent().equals("return"))) {
				toRemove.add(i);
				continue;
			}

		}
		
		for (int i = this.tracePoints.size() - 1; i >= 0; i--) {
			if (toRemove.contains(i) || i > bound || i < firstIndex){
				
				this.tracePoints.remove(i);
			}
		}
	}
	
	@Override
	public void trimTracePoints(String targetFunc) {
		this.trimTracePoints(targetFunc, null);
	}
}
