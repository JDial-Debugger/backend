package sketchobj.expr.binary;

public class Greater extends ComparativeExprBinary {

	protected Greater(ExprBinaryOptions options) {
		super(options);
		this.operator = Operator.GREATER;
	}


}
