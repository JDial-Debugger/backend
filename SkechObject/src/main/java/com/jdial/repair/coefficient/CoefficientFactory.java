package coefficient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import construction.InvalidClassException;
import construction.InvalidOptionTypeException;

public class CoefficientFactory {

	private static final Logger logger = LoggerFactory.getLogger(CoefficientFactory.class);

	public CoefficientFactory() {
	}

	public Coefficient getCoefficient(
		Class<? extends Coefficient> coeffClass,
		CoefficientOptions options
	) {
		if (coeffClass == ScalarCoefficient.class) {
			return this.getScalarCoefficient(options);
		} else if (coeffClass == VectorCoefficient.class) {
			return this.getVectorCoefficient(options);
		} else {
			throw new InvalidClassException(logger, coeffClass, CoefficientFactory.class);
		}
	}

	private ScalarCoefficient getScalarCoefficient(CoefficientOptions options) {

		if (options instanceof ScalarCoefficientOptions) {
			return new ScalarCoefficient((ScalarCoefficientOptions) options);
		}
		throw new InvalidOptionTypeException(
			options.getClass(),
			ScalarCoefficientOptions.class,
			ScalarCoefficient.class,
			logger
		);
	}

	private VectorCoefficient getVectorCoefficient(CoefficientOptions options) {

		if (options instanceof VectorCoefficientOptions) {
			return new VectorCoefficient((VectorCoefficientOptions) options);
		}
		throw new InvalidOptionTypeException(
			options.getClass(),
			VectorCoefficientOptions.class,
			VectorCoefficient.class,
			logger
		);
	}
}
