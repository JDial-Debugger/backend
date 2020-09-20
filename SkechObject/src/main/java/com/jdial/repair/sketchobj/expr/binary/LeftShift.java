package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class LeftShift extends BitwiseExprBinary {

	public LeftShift(Expression left, Expression right) {
		super(left, Operator.LEFT_SHIFT, right);
	}
}
