package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class Greater extends ComparativeExprBinary {

	protected Greater(Expression left, Expression right) {
		super(left, Operator.GREATER, right);
	}


}
