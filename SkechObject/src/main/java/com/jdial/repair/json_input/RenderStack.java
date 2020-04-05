package json_input;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RenderStack implements Frameable {

	private List<Frame> frames;

	public RenderStack() {}
	
	public RenderStack(List<Frame> frames) {
		this.frames = frames;
	}

	public List<Frame> getFrames() { return this.frames; }
	
	public String toString(){
		String result = "";
		for(Frame f: this.getFrames()){
			result = "Frame: " + f.toString();
		}
		return result;
	}

	@Override
	public Set<String> getCalledFuncs(String callerFunc) {
		
		Set<String> calledFuncs = new HashSet<String>();
		
		for (Frame frame : this.getFrames()) {
			
			if (frame.getName().equals(callerFunc)) {
				break;
			}
			
			calledFuncs.add(frame.getName());
		}
		return calledFuncs;
	}
}
