package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class CondensibleExpression {

	public Expression condensedExpression;
	public boolean isCondensible;
	
	public CondensibleExpression(Expression combinedExpression) {
		this.condensedExpression = combinedExpression;
		this.isCondensible = true;
	}
	
	public CondensibleExpression() {
		this.isCondensible = false;
	}

}
