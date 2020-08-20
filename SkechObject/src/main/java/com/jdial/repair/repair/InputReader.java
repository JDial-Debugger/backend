package repair;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constants.Errors;

public class InputReader {

	private static final Logger logger = LoggerFactory.getLogger(InputReader.class);
	private static final String TRACE_POINT_CORRECTION_TYPE = "tracePointCorrection";
	private static final String FUNC_CORRECTION_TYPE = "funcCorrection";

	private InputFileScannerFactory scnrFactory;

	public InputReader(InputFileScannerFactory scnrFactory) {
		this.scnrFactory = scnrFactory;
	}

	public Input readInput(String[] args) {

		if (args.length != 2) {
			throw new InvalidInputException(Errors.USAGE, logger);
		}

		RepairType type = this.parseRepairType(args[0]);
		String inputFileName = args[1];

		String json = this.readInputFile(inputFileName);
		return new Input(type, json);
	}

	private RepairType parseRepairType(String repairType) {

		RepairType type = null;
		if (repairType == TRACE_POINT_CORRECTION_TYPE) {
			type = RepairType.TRACE_POINT;
		} else if (repairType == FUNC_CORRECTION_TYPE) {
			type = RepairType.FUNC_CORRECTION;
		} else {
			throw new InvalidInputException(Errors.invalidRepairType(repairType), logger);
		}
		return type;

	}

	private String readInputFile(String inputFileName) {

		InputFileScanner scnr = this.scnrFactory.getInputFileScanner(inputFileName);
		return scnr.readContents();
	}

}
