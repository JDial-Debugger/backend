package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class ExprBinaryOptions {

	public Expression left, right;
	public int lineNumber;

	public ExprBinaryOptions(Expression left, Expression right, int lineNumber) {
		this(left, right);
		this.lineNumber = lineNumber;
	}

	public ExprBinaryOptions(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
}
