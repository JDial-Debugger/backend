package sketchobj.expr.binary;

public class And extends LogicalExprBinary {

	public And(ExprBinaryOptions options) {
		super(options);
		this.operator = Operator.AND;
	}
}
