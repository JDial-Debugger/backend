package sketchobj.expr.binary;

import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

public class SubtractTest {

	private CondenseInputGenerator<Subtract> inputGenerator;

	@Before
	public void getCondenseInput() {
		this.inputGenerator = new CondenseInputGenerator<Subtract>(Subtract.class);
	}

	@Test
	public void testCondenseGoldenFlow()
		throws InstantiationException,
		IllegalAccessException,
		IllegalArgumentException,
		InvocationTargetException,
		NoSuchMethodException,
		SecurityException {

		int sampleLeftVal = 8;
		int sampleRightVal = 3;

		int expectedVal = sampleLeftVal - sampleRightVal;

		Subtract subtractToTest
			= this.inputGenerator.getBothSidesAreExprConstInt(sampleLeftVal, sampleRightVal);

		CondensibleExpression result = subtractToTest.condense();

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

		Subtract subtractToTest = this.inputGenerator.getOneSideIsExprConstInt(sampleLeftVal);

		CondensibleExpression result = subtractToTest.condense();

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

		Subtract subtractToTest = this.inputGenerator.getNoSidesAreExprConstInt();

		CondensibleExpression result = subtractToTest.condense();

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

		int sampleLeftVal = 3;
		int sampleRightVal = 7;
		int expectedVal = -4;

		Subtract subtractToTest = this.inputGenerator.getOneSideCondensible(sampleLeftVal, sampleRightVal);

		CondensibleExpression result = subtractToTest.condense();

		CondenseAssertionGenerator.assertExpressionIsAnExprConstInt(result).andEquals(expectedVal);
	}

}
