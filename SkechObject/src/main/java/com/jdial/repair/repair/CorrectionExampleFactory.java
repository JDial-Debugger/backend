package repair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import json_input.FuncCorrection;
import json_input.FuncRepairInput;
import json_input.Parser;
import json_input.ParserFactory;
import json_input.Trace;
import json_input.TracePointRepairInput;

public class CorrectionExampleFactory {

	private ParserFactory inputParserFactory;

	public CorrectionExampleFactory(ParserFactory inputParserFactory) {
		this.inputParserFactory = inputParserFactory;
	}

	public List<CorrectionExample> getExampleList(Input input) {

		Parser inputParser = this.inputParserFactory.getParser();

		if (input.repairType == RepairType.TRACE_POINT) {
			TracePointRepairInput tracePointInput
				= inputParser.parseTracePointRepairInput(input.json);
			return this.getExampleListFromTracePointRepair(
				tracePointInput.trace,
				tracePointInput.correctionIdx,
				tracePointInput.expectedVars
			);

			// Always Func correction as exception should have been thrown if anything else
		} else {
			FuncRepairInput funcRepairInput = inputParser.parseFuncRepairInput(input.json);
			return this.getExampleListFromFuncRepair(
				funcRepairInput.targetFunc,
				funcRepairInput.corrections
			);
		}
	}

	/**
	 * Will always return a list of size 1
	 */
	private List<CorrectionExample> getExampleListFromTracePointRepair(
		Trace trace,
		int correctionIdx,
		Map<String, Integer> expectedVars
	) {
		trace.removeTracePointsOutsideCallStack(correctionIdx);
		return Arrays.asList(new CorrectionExample(trace, expectedVars));
	}

	private List<CorrectionExample> getExampleListFromFuncRepair(
		String targetFuncName,
		FuncCorrection[] funcCorrections
	) {
		List<CorrectionExample> examples = new ArrayList<CorrectionExample>();
		for (FuncCorrection correction : funcCorrections) {

			examples.add((correction.convertToExample(targetFuncName)));
		}
		return examples;
	}
}
