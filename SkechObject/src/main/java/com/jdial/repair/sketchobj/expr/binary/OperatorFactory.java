package sketchobj.expr.binary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperatorFactory {

	private static final Logger logger = LoggerFactory.getLogger(OperatorFactory.class);

	public OperatorFactory() {
	}

	public BinaryOp getBinaryOperator(String operator) {
		switch (operator) {
		case "+":
			return new Add();
		case "&&":
			return new And();
		case "&":
			return new BitwiseAnd();
		case "|":
			return new BitwiseOr();
		case "/":
			return new Divide();
		case "==":
			return new Equals();
		case ">":
			return new Greater();
		case ">=":
			return new GreaterEquals();
		case "<<":
			return new LeftShift();
		case "<":
			return new Less();
		case "<=":
			return new LessEquals();
		case "%":
			return new Modulo();
		case "*":
			return new Multiply();
		case "!=":
			return new NotEquals();
		case "||":
			return new Or();
		case ">>":
			return new RightShift();
		case "-":
			return new Subtract();
		case "^":
			return new Xor();
		default:
			throw new OperatorDoesNotExistException(logger, operator);
		}
	}

}
