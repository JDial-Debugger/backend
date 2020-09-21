package sketchobj.expr.binary;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class AddTest {

	private CondenseInputGenerator<Add> inputGenerator;

	@Before
	public void getCondenseInput() {
		this.inputGenerator = new CondenseInputGenerator<Add>(Add.class);
	}

	@Test
	public void testCondenseGoldenFlow()
		throws InstantiationException,
		IllegalAccessException,
		IllegalArgumentException,
		InvocationTargetException,
		NoSuchMethodException,
		SecurityException {

		int sampleLeftVal = 5;
		int sampleRightVal = -3;

		int expectedVal = sampleLeftVal + sampleRightVal;

		Add addToTest
			= this.inputGenerator.getBothSidesAreExprConstInt(sampleLeftVal, sampleRightVal);

		CondensibleExpression result = addToTest.condense();

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

		int sampleLeftVal = 5;

		Add addToTest = this.inputGenerator.getOneSideIsExprConstInt(sampleLeftVal);

		CondensibleExpression result = addToTest.condense();

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

		Add addToTest = this.inputGenerator.getNoSidesAreExprConstInt();

		CondensibleExpression result = addToTest.condense();

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
		int expectedVal = 10;

		Add addToTest = this.inputGenerator.getOneSideCondensible(sampleLeftVal, sampleRightVal);

		CondensibleExpression result = addToTest.condense();

		CondenseAssertionGenerator.assertExpressionIsAnExprConstInt(result).andEquals(expectedVal);
	}
}
