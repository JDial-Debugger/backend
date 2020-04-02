package json_input;

import java.util.ArrayList;
import java.util.List;

import jsonparser.jsonParser.TraceContext;

public class Trace extends JsonNode {

	private List<TracePoint> tracelist;
	private int length;

	public Trace(List<TracePoint> trace) {
		this.setTracelist(trace);
		this.length = trace.size();
	}

	public List<TracePoint> getTraces() {
		return tracelist;
	}

	public void setTracelist(List<TracePoint> tracelist) {
		this.tracelist = tracelist;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Removes all points in the execution trace that are not in the same
	 * call stack as the point with the given index in the trace list.
	 * @param targetFunc - the containing function name
	 * @param bound - the index in the execution trace at which to search
	 */
	public void findSubTraces(String targetFunc, int bound) {
		List<Integer> toRemove = new ArrayList<Integer>();
		int firstIndex = bound;
		for (int i = bound; i >= 0; i--) {
			if (tracelist.get(i).getFuncname().equals(targetFunc)) {
				firstIndex = i;
			}
			if (!tracelist.get(i).getEvent().equals("step_line") || 
					(i > 0 && tracelist.get(i-1).getEvent().equals("return"))) {
				toRemove.add(i);
				continue;
			}

		}
		for (int i = this.tracelist.size() - 1; i >= 0; i--) {
			if (toRemove.contains(i) || i > bound || i < firstIndex){
				tracelist.remove(i);
			}
		}
		this.length = this.tracelist.size();
	}

	public String toString() {
		String result = "";
		int i = 0;
		for (TracePoint t : this.tracelist) {
			result += "Trace " + i + ": " + t.toString();
		}
		return result;
	}
}
