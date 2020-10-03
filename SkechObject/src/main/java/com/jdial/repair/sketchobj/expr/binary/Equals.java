package sketchobj.expr.binary;

public class Equals extends EqualitiveExprBinary {

	public Equals(ExprBinaryOptions options) {
		super(options);
		this.operator = Operator.EQUALS;
	}

}
