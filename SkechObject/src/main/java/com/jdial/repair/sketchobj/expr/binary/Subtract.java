package sketchobj.expr.binary;

import sketchobj.expr.ExprConstInt;
import sketchobj.expr.Expression;

public class Subtract extends ArithmeticExprBinary {

	Subtract(Expression left, Expression right) {
		super(left, Operator.SUBTRACT, right);
	}

	@Override
	public CombineableExpression combine() {
		NumericVals<Integer> values = this.getValsFromExpressions();
		if (values.hasVals) {
			Expression combinedExpr = new ExprConstInt(values.lhsVal - values.rhsVal);
			return new CombineableExpression(combinedExpr);
		}
		return super.combine();
	}
}
