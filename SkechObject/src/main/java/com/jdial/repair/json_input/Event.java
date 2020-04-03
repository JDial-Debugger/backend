package json_input;

import com.google.gson.annotations.SerializedName;

public enum Event {
	@SerializedName("step_line") STEP_LINE,
	@SerializedName("call") CALL,
	@SerializedName("return") RETURN,
	@SerializedName("exception") EXCEPTION,
	@SerializedName("uncaught_exception") UNCAUGHT_EXCEPTION,
	@SerializedName("instruction_limit_reached") INSTRUCTION_LIMIT_REACHED,
}
