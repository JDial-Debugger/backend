package sketchobj.expr.binary;

import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

public class DivideTest {
	
	private CondenseInputGenerator<Divide> inputGenerator;

	@Before
	public void getCondenseInput() {
		this.inputGenerator = new CondenseInputGenerator<Divide>(Divide.class);
	}

	@Test
	public void testCondenseGoldenFlow()
		throws InstantiationException,
		IllegalAccessException,
		IllegalArgumentException,
		InvocationTargetException,
		NoSuchMethodException,
		SecurityException {

		int sampleLeftVal = 55;
		int sampleRightVal = 11;

		int expectedVal = sampleLeftVal / sampleRightVal;

		Divide divideToTest
			= this.inputGenerator.getBothSidesAreExprConstInt(sampleLeftVal, sampleRightVal);

		CondensibleExpression result = divideToTest.condense();

		CondenseAssertionGenerator.assertExpressionIsAnExprConstInt(result).andEquals(expectedVal);
	}

	@Test
	public void testCondenseOneSided()
		throws InstantiationException,
		IllegalAccessException,
		IllegalArgumentException,
		InvocationTargetException,
		NoSuchMethodException,
		SecurityException {

		int sampleLeftVal = 9;

		Divide divideToTest = this.inputGenerator.getOneSideIsExprConstInt(sampleLeftVal);

		CondensibleExpression result = divideToTest.condense();

		CondenseAssertionGenerator.assertExpressionIsNotCondensible(result);
	}

	@Test
	public void testCondenseNoSides()
		throws InstantiationException,
		IllegalAccessException,
		IllegalArgumentException,
		InvocationTargetException,
		NoSuchMethodException,
		SecurityException {

		Divide divideToTest = this.inputGenerator.getNoSidesAreExprConstInt();

		CondensibleExpression result = divideToTest.condense();

		CondenseAssertionGenerator.assertExpressionIsNotCondensible(result);
	}

	@Test
	public void testOneSideCondensible()
		throws InstantiationException,
		IllegalAccessException,
		IllegalArgumentException,
		InvocationTargetException,
		NoSuchMethodException,
		SecurityException {

		int sampleLeftVal = 24;
		int sampleRightVal = 3;
		int expectedVal = 8;

		Divide divideToTest = this.inputGenerator.getOneSideCondensible(sampleLeftVal, sampleRightVal);

		CondensibleExpression result = divideToTest.condense();

		CondenseAssertionGenerator.assertExpressionIsAnExprConstInt(result).andEquals(expectedVal);
	}
}
