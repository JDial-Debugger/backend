package sketchobj.expr.binary;

import coefficient.CoefficientFactory;
import options.ConstructorOptions;
import sketchobj.expr.Expression;

public class ExprBinaryOptions extends ConstructorOptions {

	private static final String left = "left", right = "right", coefficientFactory
		= "coefficientFactory", exprBinaryFactory = "exprBinaryFactory", lineNumber = "lineNumber";

	public ExprBinaryOptions() {
		super(
			new String[] { lineNumber }, 
			new String[] { left, right, coefficientFactory, exprBinaryFactory }
		);
	}

	@Override
	protected void setDestinationClassName() {
		this.setDestinationClassName(ExprBinary.class);
	}

	public Expression getLeft() {
		return (Expression) this.getOption(left);
	}

	public ExprBinaryOptions setLeft(Expression leftVal) {
		this.setOption(left, leftVal);
		return this;
	}

	public Expression getRight() {
		return (Expression) this.getOption(right);
	}

	public ExprBinaryOptions setRight(Expression rightVal) {
		this.setOption(right, rightVal);
		return this;
	}

	public CoefficientFactory getCoefficientFactory() {
		return (CoefficientFactory) this.getOption(coefficientFactory);
	}

	public ExprBinaryOptions setCoefficientFactory(CoefficientFactory coefficientFactoryVal) {
		this.setOption(coefficientFactory, coefficientFactoryVal);
		return this;
	}

	public int getLineNumber() {
		return (Integer) this.getOption(lineNumber);
	}

	public ExprBinaryOptions setLineNumber(Integer lineNumberVal) {
		this.setOption(lineNumber, lineNumberVal);
		return this;
	}
	
	public ExprBinaryFactory getExprBinaryFactory() {
		return (ExprBinaryFactory) this.getOption(exprBinaryFactory);
	}

	public ExprBinaryOptions setExprBinaryFactory(ExprBinaryFactory exprBinaryFactoryVal) {
		this.setOption(exprBinaryFactory, exprBinaryFactoryVal);
		return this;
	}
}
