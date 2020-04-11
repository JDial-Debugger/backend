package constants;

public class Errors {
	public static final String targetFuncNotFound(String targetFunc) {
		return "Target Function: " + targetFunc + " not found in Trace";
	}
	public static final String invalidRepairType(String repairType) {
		return "Repair type: " + repairType + " not recognized";
	}
	public static final String INVALID_JSON_INPUT = "JSON input malformed";
	public static final String USAGE = "usage: java RepairEngine <tracePointCorrection | funcCorrection> <json>";
	public static final String TRIM_ARGS = "Target function or call line not supplied";
}
