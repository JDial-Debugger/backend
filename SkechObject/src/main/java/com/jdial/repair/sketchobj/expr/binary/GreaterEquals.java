package sketchobj.expr.binary;

public class GreaterEquals extends EqualitiveExprBinary {

	protected GreaterEquals(ExprBinaryOptions options) {
		super(options);
		this.operator = Operator.GREATER_EQUALS;
	}
}
