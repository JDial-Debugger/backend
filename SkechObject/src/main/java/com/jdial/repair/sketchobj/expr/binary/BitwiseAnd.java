package sketchobj.expr.binary;

public class BitwiseAnd extends BitwiseExprBinary {

	public BitwiseAnd(ExprBinaryOptions options) {
		super(options);
		this.operator = Operator.BITWISE_AND;
	}
}
