package coefficient;

import construction.Option;

public class ScalarCoefficientOptions extends CoefficientOptions {

	private Boolean isAdditive;

	public ScalarCoefficientOptions() {
		super();
	}
	
	@Override
	public void setRequiredOptions() {
		super.setRequiredOptions();
		this.requiredOptions.add(new Option("isAdditive", this.isAdditive));
	}

	@Override
	public void setDestinationClassName() {
		super.setDestinationClassName(ScalarCoefficient.class);
	}

	public Boolean getIsAdditive() {
		return this.isAdditive;
	}

	public ScalarCoefficientOptions setIsAdditive(Boolean isAdditive) {
		this.isAdditive = isAdditive;
		return this;
	}

}
