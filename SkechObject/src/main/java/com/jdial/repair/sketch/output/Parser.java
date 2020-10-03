package sketch.output;

import java.io.InputStream;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import coefficient.Coefficient;

/**
 * Parses output of sketch using regex's to find repaired values of coefficients
 */
public class Parser {

	private static Logger logger = LoggerFactory.getLogger(Parser.class);
	private LineNumberReaderFactory lineNumberReaderFactory;
	private Set<Coefficient> repairedCoeffs;
	private List<Coefficient> inputCoeffs;
	private Pattern sketchCoeffChangeStmt;

	public Parser(
		List<Coefficient> inputCoeffs,
		LineNumberReaderFactory lineNumberReaderFactory
	) {
		this.inputCoeffs = inputCoeffs;
		this.lineNumberReaderFactory = lineNumberReaderFactory;
		this.repairedCoeffs = new HashSet<Coefficient>();
		// " __jdial_coeff423_change_s323 = "
		this.sketchCoeffChangeStmt
			= Pattern.compile(
				"  " + Coefficient.PREFIX + "[0-9]*" + Coefficient.CHANGE_SUFFIX + "_s[0-9]* = "
			);
	}

	/**
	 * Reads from sketch output to determine which coefficients need to be added
	 * into the source code
	 * 
	 * @param sketchOutput
	 * @return - Any coefficients that have repair values
	 */
	public Set<Coefficient> parseChangedCoeffs(InputStream sketchOutput) {
		this.processSketchOutputLines(sketchOutput);
		return this.repairedCoeffs;
	}

	private void processSketchOutputLines(InputStream sketchOutput) {
		LineNumberReader sketchOutputReader
			= this.lineNumberReaderFactory.getLineNumberReader(sketchOutput);

		String sketchOutputLine = null;
		try {
			while ((sketchOutputLine = sketchOutputReader.readLine()) != null) {
				this.processSketchOutputLine(sketchOutputLine);
			}
		} catch (java.io.IOException ex) {
			throw new IOException(ex, logger);
		}
	}

	private void processSketchOutputLine(String sketchOutputLine) {
		if (this.doesLineContainRepairedCoeffValue(sketchOutputLine)) {
			this.saveRepairedCoeffFromSketchOutput(sketchOutputLine);
		}
	}

	private boolean doesLineContainRepairedCoeffValue(String sketchOutputLine) {
		Matcher assignMatch = this.sketchCoeffChangeStmt.matcher(sketchOutputLine);
		return assignMatch.find();
	}

	private void saveRepairedCoeffFromSketchOutput(String sketchOutputLine) {

		Coefficient repairCoeff = this.findCoeffBySketchOutput(sketchOutputLine);
		int coeffRepairVal;
		try {
			coeffRepairVal = this.extractRepairedValFromLine(sketchOutputLine);
		} catch (ParseException ex) {
			return;
		}
		this.saveRepairedCoeff(repairCoeff, coeffRepairVal);
	}

	private Coefficient findCoeffBySketchOutput(String sketchOutputLine) {
		String coeffNameInSketchOutput = this.extractCoeffNameFromLine(sketchOutputLine);
		Coefficient repairCoeff = this.findCoeffByName(coeffNameInSketchOutput);
		return repairCoeff;
	}

	private String extractCoeffNameFromLine(String line) {

		Pattern coeffRegex = Pattern.compile(Coefficient.PREFIX + "[0-9]+");
		Matcher coeffMatcher = coeffRegex.matcher(line);

		if (coeffMatcher.find()) {
			String repairCoeffName = coeffMatcher.group();
			return repairCoeffName;
		} else {
			logger.warn("Unable to extract coefficient name from sketch output line: ", line);
		}
		return null;
	}

	private Coefficient findCoeffByName(String coeffName) {
		if (coeffName == null) {
			return null;
		}
		return this.inputCoeffs.stream()
			.filter(coeff -> coeff.getName().equals(coeffName))
			.findFirst()
			.orElse(null);
	}

	private int extractRepairedValFromLine(String line) {
		String[] splitLine = line.split("= ");

		if (splitLine.length != 2) {
			throw new ParseException(
				"Unable to parse repaired value assignment line",
				logger
			);
		}

		String repairVal = splitLine[1].substring(0, splitLine.length - 1);

		try {
			return Integer.parseInt(repairVal);
		} catch (NumberFormatException ex) {
			throw new ParseException(
				"Found non-int repair value: " + repairVal,
				logger
			);
		}
	}

	private void saveRepairedCoeff(Coefficient foundCoeff, int repairedValue) {
		if (foundCoeff == null) {
			return;
		}
		if (repairedValue != 0) {
			this.saveRepairedCoeffValue(foundCoeff, repairedValue);

		} else {
			this.removeUnchangedCoeff(foundCoeff);
		}
	}

	private void saveRepairedCoeffValue(Coefficient coeffToSave, int coeffValue) {

		logger.debug("Adding coefficient to repair list: " + coeffToSave.getName());

		coeffToSave.setRepairValue(coeffValue);
		this.repairedCoeffs.add(coeffToSave);
	}

	private void removeUnchangedCoeff(Coefficient coeffToRemove) {
		logger.debug("Unchanged coeff " + coeffToRemove.getName() + " found in sketch output");
		coeffToRemove.removeFromSource();
	}

}
