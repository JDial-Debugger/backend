package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class CombineableExpression {

	public Expression combinedExpression;
	public boolean isCombineable;
	
	public CombineableExpression(Expression combinedExpression) {
		this.combinedExpression = combinedExpression;
		this.isCombineable = true;
	}
	
	public CombineableExpression() {
		this.isCombineable = false;
	}

}
