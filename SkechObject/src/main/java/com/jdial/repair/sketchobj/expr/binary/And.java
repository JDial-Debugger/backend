package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class And extends LogicalExprBinary {

	public And(Expression left, Expression right) {
		super(left, Operator.AND, right);
	}
}
