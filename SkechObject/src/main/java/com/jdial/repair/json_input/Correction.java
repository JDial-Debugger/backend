package json_input;

/**
 * Used for function correction repairs, stores the trace
 * and the expected return value 
 *
 */
public class Correction {
	//The trace of the program
	private Trace trace;
	//What the function is expected to return but doesn't
	private int returnVal;
	//The line number the function is called on
	private int callLine;
	
	public Correction() {}
	public Trace getTrace() { return this.trace; }
	public int getReturnVal() { return this.returnVal; }
	public int getCallLine() { return this.callLine; }
}
