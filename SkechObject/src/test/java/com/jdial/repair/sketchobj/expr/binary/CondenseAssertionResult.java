package sketchobj.expr.binary;

import sketchobj.expr.ExprConstInt;

import static org.junit.Assert.*;

public class CondenseAssertionResult {

	private ExprConstInt exprConstInt;
	
	public CondenseAssertionResult(ExprConstInt exprConstInt) {
		this.exprConstInt = exprConstInt;
	}
	
	public CondenseAssertionResult andEquals(int expectedVal) {
		assertEquals(expectedVal, this.exprConstInt.getVal());
		return this;
	}

}
