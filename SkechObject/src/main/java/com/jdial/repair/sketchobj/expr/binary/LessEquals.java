package sketchobj.expr.binary;

public class LessEquals extends ComparativeExprBinary {

	protected LessEquals(ExprBinaryOptions options) {
		super(options);
		this.operator = Operator.LESS_EQUALS;
	}
}
