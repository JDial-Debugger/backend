package sketchobj.expr.binary;

import sketchobj.expr.ExprConstInt;
import sketchobj.expr.Expression;

public class Multiply extends ArithmeticExprBinary {

	public Multiply(Expression left, Expression right) {
		super(left, Operator.MULTIPLY, right);
	}

	@Override
	public CondensibleExpression condense() {
		NumericVals<Integer> values
			= this.getValsFromExpressions(new LeftAndRightExpressions(this.left, this.right));
		if (values.hasVals) {
			Expression combinedExpr = new ExprConstInt(values.lhsVal * values.rhsVal);
			return new CondensibleExpression(combinedExpr);
		}
		return super.condense();
	}

	@Override
	public void checkAtom() {
		this.setAtom(false);

	}

}
