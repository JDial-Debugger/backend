package sketchobj.expr.binary;

public class Xor extends BitwiseExprBinary {

	public Xor(ExprBinaryOptions options) {
		super(options);
		this.operator = Operator.XOR;
	}
}
