package sketchobj.expr.binary;

import sketchobj.expr.ExprConstInt;
import sketchobj.expr.Expression;

public class Add extends ArithmeticExprBinary{

	public Add(Expression left, Expression right) {
		super(left, Operator.ADD, right);
	}
	
	public Add(Expression left, Expression right, int lineNumber) {
		super(left, Operator.ADD, right, lineNumber);
	}
	

	@Override
	public String toString() {
		return "+";
	}

	@Override
	public CombineableExpression combine() {
		NumericVals<Integer> values = this.getValsFromExpressions();
		if (values.hasVals) {
			Expression combinedExpression = new ExprConstInt(values.lhsVal + values.rhsVal);
			return new CombineableExpression(combinedExpression);
		}
		return super.combine();
	}

}
