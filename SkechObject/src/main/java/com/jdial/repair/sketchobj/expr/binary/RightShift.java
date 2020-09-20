package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class RightShift extends BitwiseExprBinary {

	public RightShift(Expression left, Expression right) {
		super(left, Operator.SIGNED_RIGHT_SHIFT, right);
	}

}
