package coefficient;

import construction.Option;

public class VectorCoefficientOptions extends CoefficientOptions {

	private CoefficientFactory coefficientFactory;

	public VectorCoefficientOptions() {
		super();
	}

	@Override
	public void setDestinationClassName() {
		super.setDestinationClassName(VectorCoefficient.class);
	}

	@Override
	public void setRequiredOptions() {
		super.setRequiredOptions();
		this.requiredOptions.add(new Option("coefficientFactory", this.coefficientFactory));
	}

	public CoefficientFactory getCoefficientFactory() {
		return this.coefficientFactory;
	}

	public VectorCoefficientOptions setCoefficientFactory(CoefficientFactory coefficientFactory) {
		this.coefficientFactory = coefficientFactory;
		return this;
	}

}
