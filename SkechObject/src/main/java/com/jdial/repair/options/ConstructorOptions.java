package options;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ConstructorOptions extends Options {

	protected static final Logger logger = LoggerFactory.getLogger(Options.class);
	// The class that this options object will be passed to
	private String destinationClassName;

	public ConstructorOptions(String[] optionNames, String[] requiredOptionNames) {
		super(optionNames, requiredOptionNames);
		this.setDestinationClassName();
	}

	protected abstract void setDestinationClassName();

	public void handleMissingRequiredOptions() {
		Set<String> missingOptionNames = this.getMissingRequiredOptionNames();
		if (missingOptionNames.size() > 0) {
			throw new ConstructorMissingRequiredOptionsException(
				logger,
				destinationClassName,
				missingOptionNames
			);
		}
	}

	protected void setDestinationClassName(Class<?> destinationClass) {
		this.destinationClassName = destinationClass.getName();
	}
}
