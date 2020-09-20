package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public abstract class NumericExprBinary extends ExprBinary {

	public NumericExprBinary(Expression left, Operator operator, Expression right) {
		super(left, operator, right);
	}

	public NumericExprBinary(Expression left, Operator operator, Expression right, int lineNumber) {
		super(left, operator, right, lineNumber);
	}
}
