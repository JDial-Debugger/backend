package sketchobj.expr.binary;

import coefficient.CoefficientFactory;
import options.MethodOptions;
import sketchobj.expr.Expression;

public class GetExprBinaryOptions extends MethodOptions {

	private Expression left, right;
	private String operator;
	private int lineNumber;
	private CoefficientFactory coefficientFactory;

	public GetExprBinaryOptions() {
	}

	@Override
	protected void setDestinationMethodName() {
		this.setDestinationMethodName(ExprBinaryFactory.class, "getExprBinary");
	}

	@Override
	protected void setRequiredOptions() {
		this.addRequiredOption("left", this.left);
		this.addRequiredOption("right", this.right);
		this.addRequiredOption("operator", this.operator);
		this.addRequiredOption("coefficientFactory", coefficientFactory);
	}

	public Expression getLeft() {
		return this.left;
	}

	public GetExprBinaryOptions setLeft(Expression left) {
		this.left = left;
		return this;
	}

	public Expression getRight() {
		return this.right;
	}

	public GetExprBinaryOptions setRight(Expression right) {
		this.right = right;
		return this;
	}

	public String getOperator() {
		return this.operator;
	}

	public GetExprBinaryOptions setOperator(String operator) {
		this.operator = operator;
		return this;
	}

	public int getLineNumber() {
		return this.lineNumber;
	}

	public GetExprBinaryOptions setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
		return this;
	}

	public CoefficientFactory getCoefficientFactory() {
		return this.coefficientFactory;
	}

	public GetExprBinaryOptions setCoefficientFactory(CoefficientFactory coefficientFactory) {
		this.coefficientFactory = coefficientFactory;
		return this;
	}
}
