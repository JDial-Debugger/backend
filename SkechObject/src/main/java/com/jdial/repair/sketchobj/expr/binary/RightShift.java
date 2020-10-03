package sketchobj.expr.binary;

public class RightShift extends BitwiseExprBinary {

	public RightShift(ExprBinaryOptions options) {
		super(options);
		this.operator = Operator.SIGNED_RIGHT_SHIFT;
	}

}
