package sketchobj.expr;

import java.util.List;

public interface ExpressionFactory {
	
	public ExprArrayInit getArrayInit(List<Expression> elements);
	public ExprArrayRange getArrayRange();
	public ExprBinary getBinary();
	public ExprConstant getConstant();
	public ExprConstInt getConstInt();
	public ExprField getField();
	public ExprFuncCall getFuncCall();
	public ExprSketchHole getSketchHole();
	public ExprString getString();
	public ExprUnary getUnary();
	public ExprVar getVar();
}
