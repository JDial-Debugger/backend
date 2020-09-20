package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class LogicalExprBinary extends ExprBinary {

	public LogicalExprBinary(Expression left, Operator op, Expression right) {
		super(left, op, right);
	}

}
