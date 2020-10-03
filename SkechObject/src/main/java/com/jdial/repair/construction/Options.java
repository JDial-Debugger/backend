package construction;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Options {

	protected static final Logger logger = LoggerFactory.getLogger(Options.class);
	protected Set<Option> requiredOptions;
	protected String destinationClassName;

	public Options() {
		this.requiredOptions = new HashSet<Option>();
		this.setRequiredOptions();
		this.setDestinationClassName();
	}

	protected abstract void setRequiredOptions();

	protected abstract void setDestinationClassName();

	protected void setDestinationClassName(Class<?> destinationClass) {
		this.destinationClassName = destinationClass.getName();
	}

	/**
	 * @throws ConstuctorMissingRequiredOptionsException if at least one required option is null
	 */
	public void handleMissingRequiredFields() {
		Set<String> missingOptionNames = this.getNullOptionNames(this.requiredOptions);
		if (missingOptionNames.size() > 0) {
			throw new ConstructorMissingRequiredOptionsException(
				logger,
				destinationClassName,
				missingOptionNames
			);
		}
	}

	private Set<String> getNullOptionNames(Set<Option> options) {
		Set<String> nullOptionNames = new HashSet<String>();
		for (Option option : options) {
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
