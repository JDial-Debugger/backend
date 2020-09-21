package sketchobj.expr.binary;

import sketchobj.expr.ExprConstInt;
import sketchobj.expr.ExprVar;
import sketchobj.expr.Expression;

import static org.mockito.Mockito.*;

import java.lang.reflect.InvocationTargetException;

public class CondenseInputGenerator<T extends ExprBinary> {

	private Class<T> classToTest;

	public CondenseInputGenerator(Class<T> classToTest) {
		this.classToTest = classToTest;
	}

	public T getBothSidesAreExprConstInt(int leftVal, int rightVal)
		throws InstantiationException,
		IllegalAccessException,
		IllegalArgumentException,
		InvocationTargetException,
		NoSuchMethodException,
		SecurityException {

		ExprConstInt leftMock = this.getConstIntMock(leftVal);
		ExprConstInt rightMock = this.getConstIntMock(rightVal);
		return this.classToTest.getConstructor(Expression.class, Expression.class)
			.newInstance(leftMock, rightMock);
	}
	
	private ExprConstInt getConstIntMock(int constValue) {
		ExprConstInt constIntMock = mock(ExprConstInt.class);
		when(constIntMock.getVal()).thenReturn(constValue);
		return constIntMock;
	}

	public T getOneSideIsExprConstInt(int leftVal)
		throws InstantiationException,
		IllegalAccessException,
		IllegalArgumentException,
		InvocationTargetException,
		NoSuchMethodException,
		SecurityException {

		ExprConstInt leftMock = this.getConstIntMock(leftVal);
		ExprVar rightMock = mock(ExprVar.class);
		return this.classToTest.getConstructor(Expression.class, Expression.class)
			.newInstance(leftMock, rightMock);
	}

	public T getNoSidesAreExprConstInt()
		throws InstantiationException,
		IllegalAccessException,
		IllegalArgumentException,
		InvocationTargetException,
		NoSuchMethodException,
		SecurityException {

		ExprVar leftMock = mock(ExprVar.class);
		ExprVar rightMock = mock(ExprVar.class);
		return this.classToTest.getConstructor(Expression.class, Expression.class)
			.newInstance(leftMock, rightMock);
	}

	public T getOneSideCondensible(int leftVal, int rightVal)
		throws InstantiationException,
		IllegalAccessException,
		IllegalArgumentException,
		InvocationTargetException,
		NoSuchMethodException,
		SecurityException {

		ExprBinary leftCondensibleMock = this.getCondensibleMock(leftVal);
		ExprConstInt rightMock = this.getConstIntMock(rightVal);
		return this.classToTest.getConstructor(Expression.class, Expression.class)
			.newInstance(leftCondensibleMock, rightMock);
	}
	
	private ExprBinary getCondensibleMock(int condensedVal) {
		ExprConstInt condensedMock = getConstIntMock(condensedVal);
		ExprBinary condensibleMock = mock(ExprBinary.class);
		when(condensibleMock.condense()).thenReturn(new CondensibleExpression(condensedMock));
		return condensibleMock;
		
	}

}
