package sketchobj.expr.binary;

public class Or extends LogicalExprBinary {

	public Or(ExprBinaryOptions options) {
		super(options);
		this.operator = Operator.OR;
	}
}
