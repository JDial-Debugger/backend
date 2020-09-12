package sketchobj.expr;

import java.util.List;

import sketchobj.expr.ExprArrayRange.RangeLen;

public class ExpressionFactoryImpl implements ExpressionFactory {

	@Override
	public ExprArrayInit getArrayInit(List<Expression> elems) {
		return new ExprArrayInit(elems);
	}

	@Override
	public ExprArrayRange getArrayRange(String arrayVarName, String idxVarName) {
		return new ExprArrayRange(arrayVarName, idxVarName, 0);
	}
	
	@Override
	public ExprArrayRange getArrayRange(Expression arrayVarExpr, Expression idxExpr) {
		return new ExprArrayRange(arrayVarExpr, idxExpr, 0);
	}

	@Override
	public ExprBinary getBinary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExprConstant getConstant() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExprConstInt getConstInt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExprField getField() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExprFuncCall getFuncCall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExprSketchHole getSketchHole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExprString getString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExprUnary getUnary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExprVar getVar() {
		// TODO Auto-generated method stub
		return null;
	}

}
