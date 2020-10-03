package coefficient;

import construction.Option;
import construction.Options;
import sketchobj.core.TypePrimitive;
import sketchobj.expr.binary.ExprBinaryFactory;
import sketchobj.stmts.Statement;

public class CoefficientOptions extends Options {

	private Integer idx;
	private Integer lineNumber;
	private TypePrimitive type;
	private ExprBinaryFactory binaryExprFactory;
	private Statement parent;

	public CoefficientOptions() {
		super();
	}

	@Override
	protected void setRequiredOptions() {
		this.requiredOptions.add(new Option("idx", this.idx));
		this.requiredOptions.add(new Option("type", this.type));
		this.requiredOptions.add(new Option("type", this.type));
	}

	@Override
	protected void setDestinationClassName() {
		this.setDestinationClassName(Coefficient.class);
	}

	public Integer getIdx() {
		return this.idx;
	}

	public CoefficientOptions setIdx(Integer idx) {
		this.idx = idx;
		return this;
	}

	public Integer getLineNumber() {
		return this.lineNumber;
	}

	public CoefficientOptions setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
		return this;
	}

	public TypePrimitive getType() {
		return this.type;
	}

	public CoefficientOptions setType(TypePrimitive type) {
		this.type = type;
		return this;
	}

	public ExprBinaryFactory getBinaryExprFactory() {
		return this.binaryExprFactory;
	}

	public CoefficientOptions setBinaryExprFactory(ExprBinaryFactory binaryExprFactory) {
		this.binaryExprFactory = binaryExprFactory;
		return this;
	}

	public Statement getParent() {
		return this.parent;
	}

	public CoefficientOptions setParent(Statement parent) {
		this.parent = parent;
		return this;
	}
}
