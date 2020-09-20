package sketchobj.expr.binary;

import sketchobj.expr.ExprConstInt;
import sketchobj.expr.Expression;

public class Divide extends ArithmeticExprBinary {

	public Divide(Expression left, Expression right) {
		super(left, Operator.DIVIDE, right);
	}

	@Override
	public CombineableExpression combine() {
		NumericVals<Integer> values = this.getValsFromExpressions();
		if (values.hasVals) {
			Expression combinedExpr = new ExprConstInt(values.lhsVal / values.rhsVal);
			return new CombineableExpression(combinedExpr);
		}
		return super.combine();
	}
}
