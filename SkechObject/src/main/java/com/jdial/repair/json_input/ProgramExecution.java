package json_input;

public class ProgramExecution {
	private String stdin;
	private Trace trace;
	private String code;
	
	public ProgramExecution() {}
	
	public ProgramExecution(String stdin, Trace trace, String code) {
		this.stdin = stdin;
		this.trace = trace;
		this.code = code;
	}
	
	public Trace getTrace() { return this.trace; }
	public String getCode() { return this.code; }
}
