package json_input;

import java.util.Set;

public class ProgramExecution implements Frameable {
	private String stdin;
	private Trace trace;
	private String code;
	
	public ProgramExecution() {}
	
	public Trace getTrace() { return this.trace; }
	public String getCode() { return this.code; }

	@Override
	public Set<String> getCalledFuncs(String callerFunction) {
		return this.trace.getCalledFuncs(callerFunction);
	}
}
