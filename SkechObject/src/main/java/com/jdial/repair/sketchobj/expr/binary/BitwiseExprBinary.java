package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public abstract class BitwiseExprBinary extends ExprBinary{

	public BitwiseExprBinary(Expression left, Operator op, Expression right) {
		super(left, op, right);
	}

}
