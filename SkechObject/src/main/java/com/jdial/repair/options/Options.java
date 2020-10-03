package options;

import java.util.HashSet;
import java.util.Set;

/**
 * For constructors/methods with more than one parameter and/or multiple overloads
 * this class serves to decouple dependencies between the caller and callee of the constructor/method
 * 	1) This order of parameters is itself a dependency, and this class removes that
 * 	2) This class abstracts the error handling of passing null parameters 
 */
public abstract class Options {

	protected Set<Option> requiredOptions;

	public Options() {
		this.requiredOptions = new HashSet<Option>();
		this.setRequiredOptions();
	}

	public abstract void handleMissingRequiredOptions();
	
	protected abstract void setRequiredOptions();

	/**
	 * @throws ConstuctorMissingRequiredOptionsException if at least one required option is null
	 */
	public Set<String> getMissingRequiredFields() {
		Set<String> nullOptionNames = new HashSet<String>();
		for (Option option : this.requiredOptions) {
			this.addNullOptionToSet(option, nullOptionNames);
		}
		return nullOptionNames;
	}

	private void addNullOptionToSet(Option option, Set<String> nullOptionNames) {
		if (option.value == null) {
			nullOptionNames.add(option.name);
		}
	}
}
