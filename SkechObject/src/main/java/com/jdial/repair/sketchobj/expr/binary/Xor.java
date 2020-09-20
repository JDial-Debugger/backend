package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class Xor extends BitwiseExprBinary {

	public Xor(Expression left, Expression right) {
		super(left, Operator.XOR, right);
	}
}
