package sketchobj.expr;

import java.util.List;

public class ExpressionFactoryImpl implements ExpressionFactory {

	@Override
	public ExprArrayInit getArrayInit(List<Expression> elems) {
		return new ExprArrayInit(elems);
	}

	@Override
	public ExprArrayRange getArrayRange() {
		// TODO Auto-generated method stub
		return null;
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
