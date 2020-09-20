package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class Equals extends EqualitiveExprBinary {

	public Equals(Expression left, Expression right) {
		super(left, Operator.EQUALS, right);
	}

}
