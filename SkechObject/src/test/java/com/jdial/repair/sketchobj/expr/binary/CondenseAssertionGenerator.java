package sketchobj.expr.binary;

import static org.junit.Assert.*;

import sketchobj.expr.ExprConstInt;

public class CondenseAssertionGenerator {

	public static CondenseAssertionResult assertExpressionIsAnExprConstInt(CondensibleExpression result) {
		assertTrue(result.isCondensible);
		assertTrue(result.condensedExpression instanceof ExprConstInt);
		return new CondenseAssertionResult((ExprConstInt) result.condensedExpression);
	}
	
	public static void assertExpressionIsNotCondensible(CondensibleExpression result) {
		assertFalse(result.isCondensible);
	}

}
