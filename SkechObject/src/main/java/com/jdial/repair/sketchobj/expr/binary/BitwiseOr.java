package sketchobj.expr.binary;

public class BitwiseOr extends BitwiseExprBinary {

	public BitwiseOr(ExprBinaryOptions options) {
		super(options);
		this.operator = Operator.BITWISE_OR;
	}
}
