package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class Less extends ComparativeExprBinary {

	protected Less(Expression left, Expression right) {
		super(left, Operator.LESS, right);
	}

	@Override
	public String toString() {
		return "<";
	}
}
