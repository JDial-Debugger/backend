package json_input;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jsonparser.jsonParser.TraceContext;

public class Trace implements Frameable {

	private List<TracePoint> tracePoints;

	public Trace() {}
	
	public List<TracePoint> getTracePoints() { return this.tracePoints; }

	public String toString() {
		
		String result = "";
		int i = 0;
		for (TracePoint t : this.tracePoints) {
			result += "Trace " + i + ": " + t.toString();
		}
		return result;
	}

	@Override
	public Set<String> getCalledFuncs(String callerFunc) {
		
		Set<String> calledFuncs = new HashSet<String>();
		
		for(TracePoint tracePoint : this.getTracePoints()) {
			calledFuncs.addAll(tracePoint.getRstack().getCalledFuncs(callerFunc));
		}
		return calledFuncs;
	}

	
	/**
	 * Removes all points in the execution trace that are not in the same
	 * call stack as the point with the given index in the trace list.
	 * @param targetFunc - the containing function name
	 * @param bound - the upper index of the trace points to not trim
	 */
	public void trimTracePoints(String targetFunc, Integer bound) {
		
		List<Integer> toRemove = new ArrayList<Integer>();
		
		//if no bound, use return of targetFunc as bound
		if (bound == null) {
			for (int i = 0; i < this.getTracePoints().size(); ++i) {
				TracePoint tracePoint = this.getTracePoints().get(i);
				if (tracePoint.getFuncName().equals(targetFunc)
						&& tracePoint.getEvent() == Event.RETURN) {
						bound = i;
						break;
					}
			}
		}
		int firstIndex = bound;
		
		for (int i = bound; i >= 0; i--) {
			
			if (this.getTracePoints().get(i).getFuncName().equals(targetFunc)) {
				firstIndex = i;
			}
			
			//TODO: find out why this is necessary
			if (!this.getTracePoints().get(i).getEvent().equals("step_line") || 
					(i > 0 && this.getTracePoints().get(i - 1)
							.getEvent().equals("return"))) {
				toRemove.add(i);
				continue;
			}

		}
		
		for (int i = this.getTracePoints().size() - 1; i >= 0; i--) {
			if (toRemove.contains(i) || i > bound || i < firstIndex){
				
				this.getTracePoints().remove(i);
			}
		}
	}
	
	public void trimTracePoints(String targetFunc) {
		this.trimTracePoints(targetFunc, null);
	}
}
