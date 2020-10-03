package sketchobj.expr.binary;

import coefficient.CoefficientFactory;
import options.ConstructorOptions;
import sketchobj.expr.Expression;

public class ExprBinaryOptions extends ConstructorOptions {

	private Expression left, right;
	private CoefficientFactory coefficientFactory;
	private ExprBinaryFactory exprBinaryFactory;
	private int lineNumber;

	public ExprBinaryOptions() {
	}
	
	@Override
	protected void setRequiredOptions() {
		this.addRequiredOption("left", this.left);
		this.addRequiredOption("right", this.right);
		this.addRequiredOption("coefficientFactory", this.coefficientFactory);
		this.addRequiredOption("exprBinaryFactory", this.exprBinaryFactory);
	}

	@Override
	protected void setDestinationClassName() {
		this.setDestinationClassName(ExprBinary.class);
	}

	public Expression getLeft() {
		return this.left;
	}

	public ExprBinaryOptions setLeft(Expression left) {
		this.left = left;
		return this;
	}

	public Expression getRight() {
		return this.right;
	}

	public ExprBinaryOptions setRight(Expression right) {
		this.right = right;
		return this;
	}

	public CoefficientFactory getCoefficientFactory() {
		return this.coefficientFactory;
	}

	public ExprBinaryOptions setCoefficientFactory(CoefficientFactory coefficientFactory) {
		this.coefficientFactory = coefficientFactory;
		return this;
	}

	public int getLineNumber() {
		return this.lineNumber;
	}

	public ExprBinaryOptions setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
		return this;
	}
}
