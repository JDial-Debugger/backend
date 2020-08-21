package json_input;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class Parser {

	private class JsonPropName {
		private static final String CODE = "code";
		private static final String TARGET_FUNC = "targetFunc";
	}

	private Gson gson;
	private JsonObject repairJson;

	public Parser(Gson gson) {
		this.gson = gson;
	}

	public String parseCode(String jsonInput) {
		JsonObject repairJson = this.getRepairJson(jsonInput);
		return repairJson.get(JsonPropName.CODE).getAsString();
	}

	public String parseTargetFunc(String jsonInput) {
		JsonObject repairJson = this.getRepairJson(jsonInput);
		return repairJson.get(JsonPropName.TARGET_FUNC).getAsString();
	}

	public TracePointRepairInput parseTracePointRepairInput(String jsonInput) {

		JsonObject repairJson = this.getRepairJson(jsonInput);

		Trace trace = this.parseTrace(repairJson);

		Integer correctionIdx = this.getCorrectionIdx(repairJson);

		Map<String, Integer> expectedVars = this.getExpectedVars(repairJson);

		return new TracePointRepairInput(trace, correctionIdx, expectedVars);
	}

	private Trace parseTrace(JsonObject repairJson) {
		TracePoint[] tracePoints = this.gson.fromJson(repairJson.get("trace"), TracePoint[].class);
		return new Trace(Arrays.asList(tracePoints));
	}

	private int getCorrectionIdx(JsonObject repairJson) {
		return this.gson.fromJson(repairJson.get("correctionIdx"), Integer.class);
	}

	private Map<String, Integer> getExpectedVars(JsonObject repairJson) {
		Type expectedVarsType = new TypeToken<Map<String, Integer>>() {
		}.getType();
		return gson.fromJson(repairJson.get("expectedVars"), expectedVarsType);
	}

	public FuncRepairInput parseFuncRepairInput(String jsonInput) {

		JsonObject repairJson = this.getRepairJson(jsonInput);

		JsonElement targetFuncJson = repairJson.get("targetFunc");
		JsonElement correctionsJson = repairJson.get("corrections");
		String targetFunc = gson.fromJson(targetFuncJson, String.class);

		FuncCorrection[] corrections = gson.fromJson(correctionsJson, FuncCorrection[].class);

		return new FuncRepairInput(targetFunc, corrections);
	}

	private JsonObject getRepairJson(String jsonInput) {

		if (this.repairJson == null) {
			this.repairJson = JsonParser.parseString(jsonInput).getAsJsonObject();
		}
		return this.repairJson;
	}
}
