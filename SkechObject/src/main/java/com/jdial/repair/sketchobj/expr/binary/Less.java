package sketchobj.expr.binary;

public class Less extends ComparativeExprBinary {

	protected Less(ExprBinaryOptions options) {
		super(options);
		this.operator = Operator.LESS;
	}
}
