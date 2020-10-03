package coefficient;

public class VectorCoefficientOptions extends CoefficientOptions {

	private static final String coefficientFactory = "coefficientFactory";

	public VectorCoefficientOptions() {
		super();
		this.addRequiredOption(coefficientFactory);
	}

	@Override
	public void setDestinationClassName() {
		super.setDestinationClassName(VectorCoefficient.class);
	}

	public CoefficientFactory getCoefficientFactory() {
		return (CoefficientFactory) this.getOption(coefficientFactory);
	}

	public VectorCoefficientOptions setCoefficientFactory(CoefficientFactory coefficientFactoryVal) {
		this.setOption(coefficientFactory, coefficientFactoryVal);
		return this;
	}

}
