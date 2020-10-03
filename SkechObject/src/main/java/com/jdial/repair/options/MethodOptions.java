package options;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MethodOptions extends Options {
	
	protected static final Logger logger = LoggerFactory.getLogger(Options.class);
	protected String destinationMethodName;
	
	public MethodOptions() {
		super();
		this.setDestinationMethodName();
	}
	
	protected abstract void setDestinationMethodName();

	@Override
	public void handleMissingRequiredOptions() {
		Set<String> missingOptionNames = this.getMissingRequiredFields();
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
