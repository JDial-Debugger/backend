package sketch_input;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SketchResult {
	
	private static Logger logger = LoggerFactory.getLogger(SketchResult.class);
	
	public SketchResult() {}
	
	/**
	 * Reads from sketch output to determine which coefficients need to be
	 * added into the source code
	 * @param sketchOutput
	 * @return - Any coefficients that have repair values
	 */
	public static Set<Coefficient> getChangedCoeffs(
			InputStream sketchOutput, List<Coefficient> coeffs) {
		
		
		Set<Coefficient> repairedCoeffs = new HashSet<Coefficient>();
		LineNumberReader input = 
				new LineNumberReader(new InputStreamReader(sketchOutput));
		
		String line = null;
		logger.debug("  " +
						Coefficient.PREFIX + 
						"[0-9]*" + 
						Coefficient.CHANGE_SUFFIX + 
						"_s[0-9]* = ");
		Pattern coeffAssignPattern = Pattern.compile("  " + 
						Coefficient.PREFIX + 
						"[0-9]*" + 
						Coefficient.CHANGE_SUFFIX + 
						"_s[0-9]* = ");
		try {
			//search for lines of the form:   __jdial_coeff_8_change_s98 = 4;
			while ((line = input.readLine()) != null) {
				
				Matcher assignMatch = coeffAssignPattern.matcher(line);
				if(assignMatch.find()) {
					
					String[] splitLine = line.split("= ");
					int repairVal = Integer.parseInt(
							splitLine[1].substring(0, splitLine.length - 1));
					String coeffName = extractCoeffNameFromLine(line);
					//sketch has found a value for this coefficient
					if (coeffName != null) {
						
						Coefficient repairCoeff = coeffs.stream()
								.filter(coeff -> coeff.getName().equals(coeffName))
								.findFirst()
								.orElse(null);
						
						if (repairCoeff == null) {
							logger.warn("Unknown coefficient name found: " + coeffName);
						} else if (repairVal != 0) {
								
							logger.debug("Adding coefficient to repair list: " + coeffName);
							
							repairCoeff.setRepairValue(repairVal);
							repairedCoeffs.add(repairCoeff);
						//if no change for the coefficient, effectively remove it from ast
						} else {
							logger.debug("Unchanged coeff " + coeffName + " found on line:\n" + line);
							repairCoeff.removeFromSource();
						}
					}
				}
			}
		} catch (IOException e) {
			logger.warn("Error reading sketch output, see debug log for details");
			logger.debug("Error reading sketch output: " + e.toString());
		}
		return repairedCoeffs;
	}

	/**
	 * Extracts a coefficient name from a line of sketch output
	 * @param line - the line from sketch output
	 * @return - a name of the first coefficient that appears on the
	 * given line
	 */
	private static String extractCoeffNameFromLine(String line) {
		
		Pattern coeffRegex = Pattern.compile(Coefficient.PREFIX + "[0-9]+");
		Matcher coeffMatcher = coeffRegex.matcher(line);
		
		if (coeffMatcher.find()) {
			String repairCoeffName = coeffMatcher.group();
			return repairCoeffName;
		} else {
			logger.warn("Unable to extract coefficient name from sketch output");
		}
		return null;
	}

}
