package sketchobj.expr.binary;

import java.util.List;

import sketch.input.Coefficient;
import sketch.input.VectorCoefficient;
import sketchobj.core.TypePrimitive;
import sketchobj.expr.Expression;

public class ComparativeExprBinary extends NumericExprBinary {

	protected ComparativeExprBinary(Expression left, Operator operator, Expression right) {
		super(left, operator, right);
	}
	
	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {
		VectorCoefficient valCoeff
			= new VectorCoefficient(
				coeffs.size(),
				new TypePrimitive(TypePrimitive.TYPE_INT32),
				this.lineNumber
			);
		coeffs.add(valCoeff);

		this.left
			= valCoeff.addToExpr(
				this.left,
				coeffs,
				new TypePrimitive(TypePrimitive.TYPE_INT32)
			);

		this.left.setCtx(this.getCtx());
		this.left.setType(new TypePrimitive(4));
	}

}
