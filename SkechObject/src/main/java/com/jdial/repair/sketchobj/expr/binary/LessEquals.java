package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class LessEquals extends ComparativeExprBinary {

	protected LessEquals(Expression left, Expression right) {
		super(left, Operator.LESS_EQUALS, right);
	}
	
	@Override
	public String toString() {
		return "<=";
	}

}
