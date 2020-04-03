package json_input;

import java.util.List;

public class TracePoint {

	private String stdout;
	private Event event;
	private Integer line;
	private RenderStack stack;
	private String funcName;
	private VarList heap;
	private VarList locals;
	
	public TracePoint() {}
	
	public String toString(){
		return this.funcName
				+ "\nEvent: "
				+ event
				+ "\n"
				+ "line: "
				+ line
				+ "\n"
				+ stack.toString()
				+ heap
				+ locals.toString();
	}

	public String getStdout() { return this.stdout; }
	public Event getEvent() { return this.event; }
	public Integer getLine() { return this.line; }
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
