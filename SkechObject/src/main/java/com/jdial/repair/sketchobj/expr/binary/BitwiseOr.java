package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class BitwiseOr extends BitwiseExprBinary {

	public BitwiseOr(Expression left, Expression right) {
		super(left, Operator.BITWISE_OR, right);
	}

}
