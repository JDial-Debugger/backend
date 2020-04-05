package json_input;

import java.util.List;
import java.util.Map;

public class TracePoint {

	private String stdout;
	private Event event;
	private Integer lineNumber;
	private RenderStack stack;
	private String funcName;
	//TODO: replace List<Object> type with custom heap object
	private Map<String, List<Object>> heap;
	
	public TracePoint() {}
	
	public TracePoint(String stdout,
						Event event,
						Integer lineNumber,
						RenderStack stack,
						String funcName,
						Map<String, List<Object>> heap) {
		this.stdout = stdout;
		this.event = event;
		this.lineNumber = lineNumber;
		this.stack = stack;
		this.funcName = funcName;
		this.heap = heap;
	}
	
	public String toString(){
		return this.funcName
				+ "\nEvent: "
				+ event
				+ "\n"
				+ "line: "
				+ lineNumber
				+ "\n"
				+ stack.toString()
				+ heap;
	}

	public String getStdout() { return this.stdout; }
	public Event getEvent() { return this.event; }
	public Integer getLine() { return this.lineNumber; }
	public RenderStack getRstack() { return this.stack; }
	public String getFuncName() { return this.funcName; }
	public VarList getHeap() { return this.heap; }
	
	public List<String> getOrderedLocals() { 
		return this.stack.getFrames().get(0).getOrderedLocals();
	}
	
	public VarList getLocals() { 
		return this.stack.getFrames().get(0).getEncodedLocals();
	}
}
