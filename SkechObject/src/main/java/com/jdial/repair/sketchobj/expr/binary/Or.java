package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class Or extends LogicalExprBinary {

	public Or(Expression left, Expression right) {
		super(left, Operator.OR, right);
	}
}
