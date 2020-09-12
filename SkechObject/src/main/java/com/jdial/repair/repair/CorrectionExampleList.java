package repair;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ast.ParserWrapper;
import ast.ParserWrapperFactory;
import collections.LimitedList;
import collections.LimitedMap;
import json_input.FuncCorrection;
import json_input.FuncRepairInput;
import json_input.Trace;
import json_input.TracePointRepairInput;
import sketchobj.core.Function;

public class CorrectionExampleList implements LimitedList<CorrectionExample> {

	private List<CorrectionExample> examples;
	private ParserWrapper javaParser;
	private json_input.Parser inputParser;

	private static final Logger logger = LoggerFactory.getLogger(RepairEngine.class);

	public CorrectionExampleList(
		List<CorrectionExample> examples,
		ParserWrapperFactory javaParserFactory,
		json_input.ParserFactory inputParserFactory
	) {
		this.examples = examples;
		this.javaParser = javaParserFactory.getParserWrapper();
		this.inputParser = inputParserFactory.getParser();
	}

	public void addExamplesFromInput(Input input) {

		if (input.repairType == RepairType.TRACE_POINT) {
			TracePointRepairInput tracePointInput
				= this.inputParser.parseTracePointRepairInput(input.json);
			this.addExampleFromTracePointRepair(
				tracePointInput.trace,
				tracePointInput.correctionIdx,
				tracePointInput.expectedVars
			);

			// Always Func correction as exception should have been thrown if anything else
		} else {
			FuncRepairInput funcRepairInput = inputParser.parseFuncRepairInput(input.json);
			this.addExamplesFromFuncRepair(funcRepairInput.targetFunc, funcRepairInput.corrections);
		}
	}

	/**
	 * Will always return a list of size 1
	 */
	private void addExampleFromTracePointRepair(
		Trace trace,
		int correctionIdx,
		Map<String, Integer> expectedVars
	) {
		trace.removeTracePointsOutsideCallStack(correctionIdx);
		this.examples.add(new CorrectionExample(trace, expectedVars));
	}

	private void addExamplesFromFuncRepair(
		String targetFuncName,
		FuncCorrection[] funcCorrections
	) {
		for (FuncCorrection correction : funcCorrections) {

			this.examples.add((correction.convertToExample(targetFuncName)));
		}
	}

	public void add(CorrectionExample example) {
		this.examples.add(example);
	}

	public LimitedMap<String, Function> getInvokedFuncs(String code) {

		Set<String> invokedFuncNames = this.getInvokedFuncNames();
		logger.debug("Functions found in Traces: " + invokedFuncNames);
		LimitedMap<String, Function> invokedFuncs
			= this.javaParser.parseFunctionsFromCode(invokedFuncNames, code);
		return invokedFuncs;
	}

	public Set<String> getInvokedFuncNames() {
		Set<String> funcs = new HashSet<String>();
		for (CorrectionExample example : this.examples) {
			funcs.addAll(example.getProgramTrace().getCalledFuncs());
		}
		return funcs;
	}
}
