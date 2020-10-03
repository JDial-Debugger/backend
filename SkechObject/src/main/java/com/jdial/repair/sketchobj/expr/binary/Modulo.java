package sketchobj.expr.binary;

public class Modulo extends ArithmeticExprBinary {

	Modulo(ExprBinaryOptions options) {
		super(options);
		this.operator = Operator.MODULO;
	}

}
