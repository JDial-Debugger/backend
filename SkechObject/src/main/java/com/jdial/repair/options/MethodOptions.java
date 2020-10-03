package options;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MethodOptions extends Options {
	
	protected static final Logger logger = LoggerFactory.getLogger(Options.class);
	private String destinationMethodName;
	
	public MethodOptions(String[] optionNames, String[] requiredOptionNames) {
		super(optionNames, requiredOptionNames);
		this.setDestinationMethodName();
	}
	
	protected abstract void setDestinationMethodName();

	@Override
	public void handleMissingRequiredOptions() {
		Set<String> missingOptionNames = this.getMissingRequiredOptionNames();
		if (missingOptionNames.size() > 0) {
			throw new MethodMissingRequiredOptionsException(
				logger,
				this.destinationMethodName,
				missingOptionNames
			);
		}
	}

	protected void setDestinationMethodName(Class<?> destinationClass, String methodName) {
		this.destinationMethodName = destinationClass.getName() + "." + methodName;
	}
}
