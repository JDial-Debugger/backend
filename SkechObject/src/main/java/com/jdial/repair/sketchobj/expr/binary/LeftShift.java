package sketchobj.expr.binary;

public class LeftShift extends BitwiseExprBinary {

	public LeftShift(ExprBinaryOptions options) {
		super(options);
		this.operator = Operator.LEFT_SHIFT;
	}
}
