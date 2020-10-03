package sketchobj.expr.binary;

import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import construction.InvalidClassException;

public class ExprBinaryFactory {

	private static final Logger logger = LoggerFactory.getLogger(ExprBinaryFactory.class);

	public ExprBinaryFactory() {
	}

	/**
	 * Strictly used by the parser/visitor to construct objects
	 */
	public ExprBinary getExprBinary(GetExprBinaryOptions factoryOptions) {
		
		ExprBinaryOptions options = this.copyMethodOptionsToConstructorOptions(factoryOptions);
		String operator = factoryOptions.getOperator();
		
		if (operator.equals(Operator.ADD.toString())) {
			return new Add(options);
		} else if (operator.equals(Operator.AND.toString())) {
			return new And(options);
		} else if (operator.equals(Operator.BITWISE_AND.toString())) {
			return new BitwiseAnd(options);
		} else if (operator.equals(Operator.BITWISE_OR.toString())) {
			return new BitwiseOr(options);
		} else if (operator.equals(Operator.DIVIDE.toString())) {
			return new Divide(options);
		} else if (operator.equals(Operator.EQUALS.toString())) {
			return new Equals(options);
		} else if (operator.equals(Operator.GREATER.toString())) {
			return new Greater(options);
		} else if (operator.equals(Operator.GREATER_EQUALS.toString())) {
			return new GreaterEquals(options);
		} else if (operator.equals(Operator.LESS.toString())) {
			return new Less(options);
		} else if (operator.equals(Operator.LESS_EQUALS.toString())) {
			return new LessEquals(options);
		} else if (operator.equals(Operator.LEFT_SHIFT.toString())) {
			return new LeftShift(options);
		} else if (operator.equals(Operator.MULTIPLY.toString())) {
			return new Multiply(options);
		} else if (operator.equals(Operator.MODULO.toString())) {
			return new Modulo(options);
		} else if (operator.equals(Operator.NOT_EQUALS.toString())) {
			return new NotEquals(options);
		} else if (operator.equals(Operator.OR.toString())) {
			return new Or(options);
		} else if (operator.equals(Operator.SUBTRACT.toString())) {
			return new Subtract(options);
		} else if (operator.equals(Operator.SIGNED_RIGHT_SHIFT.toString())) {
			return new RightShift(options);
		} else if (operator.equals(Operator.XOR.toString())) {
			return new Xor(options);
		}
		throw new OperatorDoesNotExistException(logger, operator);
	}

	private ExprBinaryOptions copyMethodOptionsToConstructorOptions(
		GetExprBinaryOptions methodOptions
	) {
		return new ExprBinaryOptions().setLeft(methodOptions.getLeft())
			.setRight(methodOptions.getRight())
			.setCoefficientFactory(methodOptions.getCoefficientFactory());
	}

	/**
	 * After the parser constructs the AST, this can be used to create additional expressions
	 */
	public ExprBinary getExprBinary(
		Class<? extends ExprBinary> exprBinaryClass,
		ExprBinaryOptions options
	) {
		try {
			return exprBinaryClass.getConstructor(ExprBinaryOptions.class).newInstance(options);
		} catch (
			InstantiationException |
			IllegalAccessException |
			IllegalArgumentException |
			InvocationTargetException |
			NoSuchMethodException |
			SecurityException e
		) {
			throw new InvalidClassException(logger, exprBinaryClass, this.getClass());
		}
	}
}
