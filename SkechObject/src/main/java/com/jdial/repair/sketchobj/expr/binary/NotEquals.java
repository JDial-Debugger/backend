package sketchobj.expr.binary;

public class NotEquals extends EqualitiveExprBinary {

	public NotEquals(ExprBinaryOptions options) {
		super(options);
		this.operator = Operator.NOT_EQUALS;
	}
}
