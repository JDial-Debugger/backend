package sketchobj.expr.binary;

import sketchobj.expr.ExprConstInt;
import sketchobj.expr.Expression;

public class Subtract extends ArithmeticExprBinary {

	public Subtract(Expression left, Expression right) {
		super(left, Operator.SUBTRACT, right);
	}

	public Subtract(Expression left, Expression right, int lineNumber) {
		super(left, Operator.ADD, right, lineNumber);
	}

	@Override
	public CondensibleExpression condense() {
		NumericVals<Integer> values
			= this.getValsFromExpressions(new LeftAndRightExpressions(this.left, this.right));
		if (values.hasVals) {
			Expression combinedExpr = new ExprConstInt(values.lhsVal - values.rhsVal);
			return new CondensibleExpression(combinedExpr);
		}
		return super.condense();
	}
}
