package options;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * For constructors/methods with more than one parameter and/or multiple overloads this class serves
 * to decouple dependencies between the caller and callee of the constructor/method 1) This order of
 * parameters is itself a dependency, and this class removes that 2) This class abstracts the error
 * handling of passing null parameters
 */
public abstract class Options {

	private Map<String, Option> options;

	public Options(String[] optionNames, String[] requiredOptionNames) {
		this.options = new HashMap<String, Option>();
		this.addOptions(optionNames);
		this.addRequiredOptions(optionNames);
	}
	
	public abstract void handleMissingRequiredOptions();

	protected Object getOption(String name) {
		return this.options.get(name);
	}

	protected void setOption(String name, Object value) {
		this.options.get(name).value = value;
	}

	protected void addOptions(String[] optionNames) {
		this.addOptions(optionNames, false);
	}
	
	protected void addRequiredOptions(String[] optionNames) {
		this.addOptions(optionNames, true);
	}

	private void addOptions(String[] optionNames, boolean isRequired) {
		for (String optionName : optionNames) {
			this.addOption(optionName, isRequired);
		}
	}
	
	protected void addOption(String optionName) {
		this.addOption(optionName, false);
	}

	protected void addRequiredOption(String optionName) {
		this.addOption(optionName, true);
	}

	private void addOption(String optionName, boolean isRequired) {
		this.options.put(optionName, new Option(null, isRequired));
	}

	protected Set<String> getMissingRequiredOptionNames() {
		Set<String> missingRequiredOptionNames = new HashSet<String>();

		for (Map.Entry<String, Option> entry : this.options.entrySet()) {
			this.addNullOptionToSet(entry.getKey(), entry.getValue(), missingRequiredOptionNames);
		}
		return missingRequiredOptionNames;
	}

	private void addNullOptionToSet(String optionName, Option option, Set<String> nullOptionNames) {
		if (option.value == null) {
			nullOptionNames.add(optionName);
		}
	}
}
