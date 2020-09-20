package sketchobj.expr.binary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sketchobj.expr.Expression;

public class ExprBinaryFactory {

	private static final Logger logger = LoggerFactory.getLogger(ExprBinaryFactory.class);
	
	public ExprBinaryFactory() {
	}
	
	public ExprBinary getExprBinary(Expression left, String operator, Expression right) {
		
		if (operator.equals(Operator.ADD.toString())) {
			return new Add(left, right);
		} else if (operator.equals(Operator.AND.toString())) {
			return new And(left, right);
		} else if (operator.equals(Operator.BITWISE_AND.toString())) {
			return new BitwiseAnd(left, right);
		} else if (operator.equals(Operator.BITWISE_OR.toString())) {
			return new BitwiseOr(left, right);
		} else if (operator.equals(Operator.DIVIDE.toString())) {
			return new Divide(left, right);
		} else if (operator.equals(Operator.EQUALS.toString())) {
			return new Equals(left, right);
		} else if (operator.equals(Operator.GREATER.toString())) {
			return new Greater(left, right);
		} else if (operator.equals(Operator.GREATER_EQUALS.toString())) {
			return new GreaterEquals(left, right);
		} else if (operator.equals(Operator.LESS.toString())) {
			return new Less(left, right);
		} else if (operator.equals(Operator.LESS_EQUALS.toString())) {
			return new LessEquals(left, right);
		} else if (operator.equals(Operator.LEFT_SHIFT.toString())) {
			return new LeftShift(left, right);
		} else if (operator.equals(Operator.MULTIPLY.toString())) {
			return new Multiply(left, right);
		} else if (operator.equals(Operator.MODULO.toString())) {
			return new Modulo(left, right);
		} else if (operator.equals(Operator.NOT_EQUALS.toString())) {
			return new NotEquals(left, right);
		} else if (operator.equals(Operator.OR.toString())) {
			return new Or(left, right);
		} else if (operator.equals(Operator.SUBTRACT.toString())) {
			return new Subtract(left, right);
		} else if (operator.equals(Operator.SIGNED_RIGHT_SHIFT.toString())) {
			return new RightShift(left, right);
		} else if (operator.equals(Operator.XOR.toString())) {
			return new Xor(left, right);
		}
		throw new OperatorDoesNotExistException(operator, logger);

	}
}
