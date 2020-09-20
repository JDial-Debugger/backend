package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class NotEquals extends EqualitiveExprBinary {

	public NotEquals(Expression left, Expression right) {
		super(left, Operator.NOT_EQUALS, right);
		// TODO Auto-generated constructor stub
	}
}
