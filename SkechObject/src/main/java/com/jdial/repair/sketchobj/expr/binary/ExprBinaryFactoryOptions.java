package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class ExprBinaryFactoryOptions {

	public Expression left, right;
	public String operator;
	public int lineNumber;

	public ExprBinaryFactoryOptions(Expression left, String op, Expression right, int lineNumber) {
		this.left = left;
		this.right = right;
		this.operator = op;
		this.lineNumber = lineNumber;
	}
}
