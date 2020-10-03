package coefficient;

import options.ConstructorOptions;
import sketchobj.core.TypePrimitive;
import sketchobj.expr.binary.ExprBinaryFactory;
import sketchobj.stmts.Statement;

public class CoefficientOptions extends ConstructorOptions {

	private static final String lineNumber = "lineNumber";
	private static final String parent = "parent";
	private static final String idx = "idx";
	private static final String type = "type";
	private static final String binaryExprFactory = "binaryExprFactory";

	public CoefficientOptions() {
		super(
			new String[] { lineNumber, parent }, 
			new String[] { idx, type, binaryExprFactory }
		);
	}

	@Override
	protected void setDestinationClassName() {
		this.setDestinationClassName(Coefficient.class);
	}

	public Integer getIdx() {
		return (Integer) this.getOption(idx);
	}

	public CoefficientOptions setIdx(Integer idxVal) {
		this.setOption(idx, idxVal);
		return this;
	}

	public Integer getLineNumber() {
		return (Integer) this.getOption(lineNumber);
	}

	public CoefficientOptions setLineNumber(Integer lineNumberVal) {
		this.setOption(lineNumber, lineNumberVal);
		return this;
	}

	public TypePrimitive getType() {
		return (TypePrimitive) this.getOption(type);
	}

	public CoefficientOptions setType(TypePrimitive typeVal) {
		this.setOption(type, typeVal);;
		return this;
	}

	public ExprBinaryFactory getBinaryExprFactory() {
		return (ExprBinaryFactory) this.getOption(binaryExprFactory);
	}

	public CoefficientOptions setBinaryExprFactory(ExprBinaryFactory binaryExprFactoryVal) {
		this.setOption(binaryExprFactory, binaryExprFactoryVal);
		return this;
	}

	public Statement getParent() {
		return (Statement) this.getOption(parent);
	}

	public CoefficientOptions setParent(Statement parentVal) {
		this.setOption(parent, parentVal);
		return this;
	}
}
