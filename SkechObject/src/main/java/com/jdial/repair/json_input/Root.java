package json_input;

public class Root extends JsonNode {

	private Code code;
	private Trace traces;
	private Stdin stdin;

	public Root(Code code, Trace traces, Stdin stdin) {
		this.setCode(code);
		this.setTraces(traces);
		this.setStdin(stdin);
	}

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public Trace getTraces() {
		return traces;
	}

	public void setTraces(Trace traces) {
		this.traces = traces;
	}

	public Stdin getStdin() {
		return stdin;
	}

	public void setStdin(Stdin stdin) {
		this.stdin = stdin;
	}

}
