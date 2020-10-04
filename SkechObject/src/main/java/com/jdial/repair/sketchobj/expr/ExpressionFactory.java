package sketchobj.expr;

import java.util.List;

import sketchobj.expr.binary.ExprBinary2;

public interface ExpressionFactory {
	
	public ExprArrayInit getArrayInit(List<Expression> elements);
	public ExprArrayRange getArrayRange(String arrayVarName, String idxVarName);
	public ExprArrayRange getArrayRange(Expression arrayVarExpr, Expression idxExpr);
	public ExprConstant getConstant();
	public ExprConstInt getConstInt();
	public ExprField getField();
	public ExprFuncCall getFuncCall();
	public ExprSketchHole getSketchHole();
	public ExprString getString();
	public ExprUnary getUnary();
	public ExprVar getVar();
}
