package json_input;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

public class TracePoint implements Frameable {

	private static final Logger logger = LoggerFactory.getLogger(TracePoint.class);
	
	private String stdout;
	private Event event;
	@SerializedName(value = "line")
	private Integer lineNumber;
	@SerializedName(value = "stack_to_render")
	@JsonAdapter(StackDeserializer.class)
	private List<Frame> stack;
	@SerializedName(value = "func_name")
	private String funcName;
	//TODO: replace List<Object> type with custom heap object
	@JsonAdapter(HeapDeserializer.class)
	private Map<String, List<Object>> heap;
	
	
	public TracePoint() {}
	
	public TracePoint(String stdout,
						Event event,
						Integer lineNumber,
						List<Frame> stack,
						String funcName,
						Map<String, List<Object>> heap) {
		this.stdout = stdout;
		this.event = event;
		this.lineNumber = lineNumber;
		this.stack = stack;
		this.funcName = funcName;
		this.heap = heap;
	}
	
	/**
	 * Performs a deep copy of the given tracepoint
	 * @param copy - the trace point to create a copy for
	 */
	public TracePoint(TracePoint copy) {
		this.stdout = copy.getStdout();
		this.event = copy.getEvent();
		this.lineNumber = copy.getLine();
		this.stack = new ArrayList<Frame>(copy.getRstack());
		this.funcName = copy.getFuncName();
		this.heap = new HashMap<String, List<Object>>();
		//TODO implement heap deep copy
		this.heap = copy.getHeap();
	}

	@Override
	public Set<String> getCalledFuncs(String callerFunc, int callLine) {
		
		Set<String> calledFuncs = new HashSet<String>();
		ListIterator<Frame> li = this.stack.listIterator(this.stack.size());
		
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
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((funcName == null) ? 0 : funcName.hashCode());
		result = prime * result + ((heap == null) ? 0 : heap.hashCode());
		result = prime * result + ((lineNumber == null) ? 0 : lineNumber.hashCode());
		result = prime * result + ((stack == null) ? 0 : stack.hashCode());
		result = prime * result + ((stdout == null) ? 0 : stdout.hashCode());
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
		TracePoint other = (TracePoint) obj;
		if (event != other.event)
			return false;
		if (funcName == null) {
			if (other.funcName != null)
				return false;
		} else if (!funcName.equals(other.funcName))
			return false;
		if (heap == null) {
			if (other.heap != null)
				return false;
		} else if (!heap.equals(other.heap))
			return false;
		if (lineNumber == null) {
			if (other.lineNumber != null)
				return false;
		} else if (!lineNumber.equals(other.lineNumber))
			return false;
		if (stack == null) {
			if (other.stack != null)
				return false;
		} else if (!stack.equals(other.stack))
			return false;
		if (stdout == null) {
			if (other.stdout != null)
				return false;
		} else if (!stdout.equals(other.stdout))
			return false;
		return true;
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
	public List<Frame> getRstack() { return this.stack; }
	public String getFuncName() { return this.funcName; }
	public Map<String, List<Object>> getHeap() { return this.heap; }
	
	public List<String> getOrderedLocals() { 
		return this.stack.get(0).getOrderedLocals();
	}
	
	public Map<String, Integer> getLocals() { 
		return this.stack.get(0).getEncodedLocals();
	}
	
	//https://futurestud.io/tutorials/gson-advanced-customizing-de-serialization-and-adding-instance-creators-via-jsonadapter
	private class StackDeserializer implements JsonDeserializer<List<Frame>> {
		@Override
		public List<Frame> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			JsonArray jsonArray = json.getAsJsonArray();
			List<Frame> stack = new ArrayList<Frame>();
			jsonArray.forEach(element -> {
				stack.add(context.deserialize(element, Frame.class));
			});
			return stack;
		}
	}
	//TODO doesn't deserialize heap values
	private class HeapDeserializer implements JsonDeserializer<Map<String, List<Object>>> {
		@Override
		public Map<String, List<Object>> deserialize(JsonElement json, 
												Type typeOfT, 
												JsonDeserializationContext context)
				throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();
			Map<String, List<Object>> heap = new HashMap<String, List<Object>>();
			jsonObject.entrySet().forEach(entry -> {
				//TODO insert custom heap type
				heap.put(entry.getKey(), null);
			});
			return null;
		}
	}
}
