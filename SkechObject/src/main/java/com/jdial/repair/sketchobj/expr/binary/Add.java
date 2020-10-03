package sketchobj.expr.binary;

import sketchobj.expr.ExprConstInt;
import sketchobj.expr.Expression;

public class Add extends ArithmeticExprBinary {

	public Add(ExprBinaryOptions options) {
		super(options);
		this.operator = Operator.ADD;
	}

	@Override
	public CondensibleExpression condense() {
		NumericVals<Integer> values
			= this.getValsFromExpressions(new LeftAndRightExpressions(this.left, this.right));
		if (values.hasVals) {
			Expression combinedExpression = new ExprConstInt(values.lhsVal + values.rhsVal);
			return new CondensibleExpression(combinedExpression);
		}
		return super.condense();
	}

}
