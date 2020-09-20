package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class GreaterEquals extends EqualitiveExprBinary {

	protected GreaterEquals(Expression left, Expression right) {
		super(left, Operator.GREATER_EQUALS, right);
	}
}
