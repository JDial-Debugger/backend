package sketchobj.expr.binary;

import sketchobj.expr.Expression;

public class Modulo extends ArithmeticExprBinary {

	Modulo(Expression left, Expression right) {
		super(left, Operator.MODULO, right);
	}

}
