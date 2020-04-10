package json_input;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RenderStack implements Frameable {

	private List<Frame> frames;
	private static final Logger logger = LoggerFactory.getLogger(RenderStack.class);

	public RenderStack() {}
	
	public RenderStack(List<Frame> frames) {
		this.frames = frames;
	}
	
	/**
	 * Performs a deep copy of the given RenderStack
	 * @param copy - the RenderStack to create a copy of
	 */
	public RenderStack(RenderStack copy) {
		this.frames = new ArrayList<Frame>();
		for (Frame frame : copy.getFrames()) {
			this.frames.add(new Frame(frame));
		}
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
	public Set<String> getCalledFuncs(String callerFunc, int callLine) {
		
		Set<String> calledFuncs = new HashSet<String>();
		ListIterator<Frame> li = this.frames.listIterator(this.frames.size());
		
		boolean foundCaller = false;
		while (li.hasPrevious()) {
			
			Frame frame = li.previous();
			
			String curFunc = "";
			try {
				curFunc = frame.getName().split(":")[0];
			} catch (ArrayIndexOutOfBoundsException e) {
				logger.warn("Invalid frame function name: " + frame.getName(),
						"Is JSON input malformed?");
				continue;
			}
			
			if (foundCaller) {
				calledFuncs.add(curFunc);
				continue;
			}
			
			int curFuncCallLine = Integer.parseInt(frame.getName().split(":")[1]);
			
			if (curFunc.equals(callerFunc) && curFuncCallLine == callLine) {
				foundCaller = true;
			}
		}
		return calledFuncs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((frames == null) ? 0 : frames.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RenderStack other = (RenderStack) obj;
		if (frames == null) {
			if (other.frames != null)
				return false;
		} else if (!frames.equals(other.frames))
			return false;
		return true;
	}
}
