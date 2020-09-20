package sketchobj.expr.binary;

import sketchobj.expr.ExprConstInt;
import sketchobj.expr.Expression;

public class Multiply extends ArithmeticExprBinary {

	Multiply(Expression left, Expression right) {
		super(left, Operator.MULTIPLY, right);
	}

	@Override
	public CombineableExpression combine() {
		NumericVals<Integer> values = this.getValsFromExpressions();
		if (values.hasVals) {
			Expression combinedExpr = new ExprConstInt(values.lhsVal * values.rhsVal);
			return new CombineableExpression(combinedExpr);
		}
		return super.combine();
	}
	
	@Override
	public void checkAtom() {
		this.setAtom(false);
		
	}

}
