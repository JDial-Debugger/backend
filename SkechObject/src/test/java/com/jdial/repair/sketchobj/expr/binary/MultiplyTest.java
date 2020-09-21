package sketchobj.expr.binary;

import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

public class MultiplyTest {
	private CondenseInputGenerator<Multiply> inputGenerator;

	@Before
	public void getCondenseInput() {
		this.inputGenerator = new CondenseInputGenerator<Multiply>(Multiply.class);
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
		int sampleRightVal = 2;

		int expectedVal = sampleLeftVal * sampleRightVal;

		Multiply multiplyToTest
			= this.inputGenerator.getBothSidesAreExprConstInt(sampleLeftVal, sampleRightVal);

		CondensibleExpression result = multiplyToTest.condense();

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

		Multiply multiplyToTest = this.inputGenerator.getOneSideIsExprConstInt(sampleLeftVal);

		CondensibleExpression result = multiplyToTest.condense();

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

		Multiply multiplyToTest = this.inputGenerator.getNoSidesAreExprConstInt();

		CondensibleExpression result = multiplyToTest.condense();

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

		int sampleLeftVal = 6;
		int sampleRightVal = 7;
		int expectedVal = 42;

		Multiply multiplyToTest = this.inputGenerator.getOneSideCondensible(sampleLeftVal, sampleRightVal);

		CondensibleExpression result = multiplyToTest.condense();

		CondenseAssertionGenerator.assertExpressionIsAnExprConstInt(result).andEquals(expectedVal);
	}
}
