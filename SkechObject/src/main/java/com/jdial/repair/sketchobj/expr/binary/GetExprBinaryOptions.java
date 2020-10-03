package sketchobj.expr.binary;

import coefficient.CoefficientFactory;
import options.MethodOptions;
import sketchobj.expr.Expression;

public class GetExprBinaryOptions extends MethodOptions {

	private static final String 
		left = "left", 
		right = "right", 
		operator = "operator", 
		lineNumber = "lineNumber", 
		coefficientFactory = "coefficientFactory";

	public GetExprBinaryOptions() {
		super(
			new String[] { left, right, operator, coefficientFactory },
			new String[] { lineNumber }
		);
	}

	@Override
	protected void setDestinationMethodName() {
		this.setDestinationMethodName(ExprBinaryFactory.class, "getExprBinary");
	}

	public Expression getLeft() {
		return (Expression) this.getOption(left);
	}

	public GetExprBinaryOptions setLeft(Expression leftVal) {
		this.setOption(left, leftVal);
		return this;
	}

	public Expression getRight() {
		return (Expression) this.getOption(right);
	}

	public GetExprBinaryOptions setRight(Expression rightVal) {
		this.setOption(right, rightVal);
		return this;
	}

	public String getOperator() {
		return (String) this.getOption(operator);
	}

	public GetExprBinaryOptions setOperator(String operatorVal) {
		this.setOption(operator, operatorVal);
		return this;
	}

	public CoefficientFactory getCoefficientFactory() {
		return (CoefficientFactory) this.getOption(coefficientFactory);
	}

	public GetExprBinaryOptions setCoefficientFactory(CoefficientFactory coefficientFactoryVal) {
		this.setOption(coefficientFactory, coefficientFactoryVal);
		return this;
	}

	public int getLineNumber() {
		return (Integer) this.getOption(lineNumber);
	}

	public GetExprBinaryOptions setLineNumber(Integer lineNumberVal) {
		this.setOption(lineNumber, lineNumberVal);
		return this;
	}
}
