package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public abstract class EqualitiveExprBinary extends ExprBinary {
	
	public EqualitiveExprBinary(Expression left, Operator op, Expression right) {
		super(left, op, right);
	}
}
