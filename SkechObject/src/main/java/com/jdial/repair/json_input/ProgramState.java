package json_input;

public abstract class ProgramState {
	
	public abstract void trimTracePoints(String targetFunc, Integer bound);
	public abstract void trimTracePoints(String targetFunc);
}
