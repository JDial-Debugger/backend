package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class BitwiseAnd extends BitwiseExprBinary {

	public BitwiseAnd(Expression left, Expression right) {
		super(left, Operator.BITWISE_AND, right);
	}
}
