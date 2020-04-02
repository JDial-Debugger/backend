package json_input;

import java.util.Set;

public class ProgramExecution extends ProgramState implements Frameable {
	private String stdin;
	private Trace trace;
	private String code;
	
	public ProgramExecution() {}
	
	public Trace getTrace() { return this.trace; }
	public String getCode() { return this.code; }

	@Override
	public Set<String> getCalledFunctions(String callerFunction) {
		return this.trace.getCalledFunctions(callerFunction);
	}
	public void trimTracePoints(String targetFunction) {
		return this.trace.trimTracePoints(targetFunction);
	}

}
