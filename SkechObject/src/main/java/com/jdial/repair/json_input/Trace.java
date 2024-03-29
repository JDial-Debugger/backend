package json_input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constants.Errors;

public class Trace implements Frameable {

	private List<TracePoint> tracePoints;
	
	private static final int DEFAULT_BOUND = -1;
	private static final Logger logger = LoggerFactory.getLogger(Trace.class);
	public static final String RETURN = "__return__";

	public Trace() {}
	
	public Trace(List<TracePoint> tracePoints) {
		this.tracePoints = tracePoints;
	}
	
	public Trace(TracePoint[] tracePoints) {
		this.tracePoints = Arrays.asList(tracePoints);
	}
	
	/**
	 * Performs a deep copy of the given trace
	 * @param trace - the trace to copy
	 */
	public Trace(Trace trace) {
		this.tracePoints = new ArrayList<TracePoint>();
		for (TracePoint tracePoint : trace.getTracePoints()) {
			this.tracePoints.add(new TracePoint(tracePoint));
		}
	}
	
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
	public Set<String> getCalledFuncs(String targetFunc, int callLine) {
		
		Set<String> calledFuncs = new HashSet<String>();
		//find the trace point where callerFunc is called in order to get
		//the name of the function that called targetFunc
		List<TracePoint> tracePoints = this.getTracePoints();
		String callerFunc = null;
		for (int i = 0; i < tracePoints.size(); ++i) {
			
			TracePoint tracePoint = tracePoints.get(i);
			
			if (callerFunc != null) {
				calledFuncs.addAll(tracePoint.getCalledFuncs(callerFunc, callLine));
			} else if (tracePoint.getFuncName().equals(targetFunc) 
					&& Integer.parseInt(tracePoint.getRstack()
							.get(1).getName().split(":")[1]) == callLine) {
				callerFunc = tracePoint.getRstack().get(1).getName().split(":")[0];
			}
		}
		
		logger.debug("Found called functions for caller function: " + callerFunc + ":" + callLine);
		logger.debug("Called functions: " + calledFuncs);
		return calledFuncs;
	}
	
	/**
	 * If no args specified, will use the beginning trace point to find the 
	 * target function and call line
	 * @return
	 */
	public Set<String> getCalledFuncs() {
		TracePoint start = this.getTracePoints().get(0);
		int callerLine = Integer.parseInt(start.getRstack().get(1)
				.getName().split(":")[1]);
		return this.getCalledFuncs(start.getFuncName(), callerLine);
	}

	
	/**
	 * Removes all points in the execution trace that are not in the same
	 * call stack as the point with the given index in the trace list.
	 * @param targetFunc - the containing function name
	 * @param bound - the upper index of the trace points to not trim
	 * @param targetFuncCallLine - The line number that the target function is called on
	 * @return 
	 */
	public void removeTracePointsOutsideCallStack(String targetFunc, Integer bound, Integer callLine) {
		
		if (targetFunc == null) {
			throw new IllegalArgumentException("Target function is null");
		}
		
		//if no bound, use call line to search for trace pt idx of the return
		//and call events of the target function
		//if the targetFunc is called multiple times on the same line, takes
		//the first one
		int[] bounds;
		if (bound == null) {
			
			logger.debug("Trimming Tracepoints from target function: " 
					+ targetFunc 
					+ ":" 
					+ callLine);
			bounds = this.getBoundsByCallLine(targetFunc, callLine);
		//back track to find call trace point index
		} else if (callLine == null) {
			logger.debug("Trimming Tracepoints from target function: " 
					+ targetFunc 
					+ " and bound" 
					+ bound);
			bounds = this.getBoundsByUpper(targetFunc, bound);
			
		} else {
			throw new IllegalArgumentException("Exactly one of bound "
										+ "and callLine should be null");
		}
		
		if (bounds[0] == -1) {
			throw new InvalidTraceException(
					Errors.targetFuncNotFound(targetFunc), this);
		}
		
		logger.debug("Bounds for trace points: [" 
					+ bounds[0] + "-" + bounds[1] + "]");
		logger.info((this.tracePoints.size() - bounds[1] - 1 + bounds[0]) 
				+ " trace points trimmed from trace");
		
		this.tracePoints = this.getTracePoints().subList(bounds[0], bounds[1] + 1);
	}

	/**
	 * For the first call of the given function in this trace, find the param
	 * values it is called with and returns them in the param list of the function
	 * definition
	 * @param targetFunc - the name of the function call to get params from
	 * @return maps each param name to it's value 
	 * TODO: add support for other types than ints
	 */
	public List<Integer> getParamVals(String targetFunc) {
		
		for (TracePoint tracePoint : this.getTracePoints()) {
			//finds first call
			if (tracePoint.getFuncName().equals(targetFunc)) {
				//assumes trace points save func params in correct order
				return new ArrayList<Integer>(tracePoint.getLocals().values());
			}
		}
		return new ArrayList<Integer>();
	}
	
	/**
	 * Uses function at bound for target function
	 * @param bound
	 */
	public void removeTracePointsOutsideCallStack(Integer bound) {
		String targetFunc = this.tracePoints.get(bound).getFuncName();
		this.removeTracePointsOutsideCallStack(targetFunc, bound, null);
	}
	
	/**
	 * Finds the trace points contained on the call of the given function on
	 * the given line
	 * @param targetFunc - the name of the function to get trace points for
	 * @param callLine - the line number that targetFunc is called on
	 * @return bounds, size 2, bounds[0] - inclusive starting trace point index,
	 * bounds[1] - inclusive ending trace point index
	 */
	private int[] getBoundsByCallLine(String targetFunc, Integer callLine) {
		
		int[] bounds = new int[] {DEFAULT_BOUND, DEFAULT_BOUND};
		StringBuilder lastFuncName = new StringBuilder();
		int lastLineNumber = DEFAULT_BOUND;
		StringBuilder targetFuncCallerFrame = new StringBuilder();
		
		for (int i = 0; i < this.getTracePoints().size(); ++i) {
			
			TracePoint tracePoint = this.getTracePoints().get(i);
		
			//found the call index
			if (!lastFuncName.toString().equals(targetFunc)
					&& lastLineNumber == callLine
					&& bounds[0] == -1
					&& tracePoint.getEvent() == Event.CALL
					&& tracePoint.getFuncName().equals((targetFunc))) {
				bounds[0] = i;
				targetFuncCallerFrame.append(
						tracePoint.getRstack().get(1).getName());
			}
			lastFuncName.setLength(0);
			lastFuncName.append(tracePoint.getFuncName());
			lastLineNumber = tracePoint.getLine();
			
			if (tracePoint.getFuncName().equals(targetFunc)
					&& tracePoint.getEvent() == Event.RETURN
					&& tracePoint.getRstack().get(1).getName()
						.equals(targetFuncCallerFrame.toString())) {
				
				bounds[1] = i;
				break;
			}
		}
		
		if (bounds[0] == DEFAULT_BOUND|| bounds[1] == DEFAULT_BOUND) {
			throw new IllegalArgumentException("Unable to locate target function: " + targetFunc + " in trace");
		}
		return bounds;
	}
	
	/**
	 * Finds the trace points contained on the call of the given function that
	 * contains trace point at index upperBound
	 * @param targetFunc - the name of the function to get trace points for
	 * @param callLine - the line number that targetFunc is called on
	 * @return bounds, size 2, bounds[0] - inclusive starting trace point index,
	 * bounds[1] - inclusive ending trace point index (upperBound)
	 */
	private int[] getBoundsByUpper(String targetFunc, Integer upperBound) {
		
		int[] bounds = new int[] {DEFAULT_BOUND, upperBound};
		int frameCounter = 0;
		//case when correction index is on a return point
		if (this.tracePoints.get(bounds[1]).getEvent() == Event.RETURN) {
			frameCounter = 1;
		}
		
		String lastFuncName = null;
		for (int i = bounds[1]; i >= 0; --i) {
			
			TracePoint tracePoint = this.getTracePoints().get(i);
			
			if (tracePoint.getEvent() == Event.RETURN
					&& tracePoint.getFuncName().equals(targetFunc)) {
				
				++frameCounter;
				lastFuncName = null;
				
			} else if (tracePoint.getEvent() == Event.CALL
					&& tracePoint.getFuncName().equals(targetFunc)) {
				
				lastFuncName = tracePoint.getFuncName();
			} else if (tracePoint.getEvent() == Event.STEP_LINE
					&& lastFuncName != null
					&& !tracePoint.getFuncName().equals(lastFuncName)) {
				
				if (frameCounter == 0) {
					bounds[0] = i + 1;
					break;
				} else {
					--frameCounter;
				}
			} else {
				lastFuncName = null;
			}
		}
		return bounds;
	}
}
