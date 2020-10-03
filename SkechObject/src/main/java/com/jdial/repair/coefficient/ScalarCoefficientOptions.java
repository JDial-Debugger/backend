package coefficient;

public class ScalarCoefficientOptions extends CoefficientOptions {

	private static final String isAdditive = "isAdditive";

	public ScalarCoefficientOptions() {
		super();
		this.addRequiredOption(isAdditive);
	}
	
	@Override
	public void setDestinationClassName() {
		super.setDestinationClassName(ScalarCoefficient.class);
	}

	public Boolean getIsAdditive() {
		return (Boolean) this.getOption(isAdditive);
	}

	public ScalarCoefficientOptions setIsAdditive(Boolean isAdditiveVal) {
		this.setOption(isAdditive, isAdditiveVal);
		return this;
	}

}
