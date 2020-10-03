package sketchobj.expr.binary;

import java.util.List;

import coefficient.Coefficient;
import coefficient.CoefficientOptions;
import coefficient.VectorCoefficient;
import coefficient.VectorCoefficientOptions;
import sketchobj.core.TypePrimitive;

public class ComparativeExprBinary extends NumericExprBinary {

	protected ComparativeExprBinary(ExprBinaryOptions options) {
		super(options);
	}

	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {

		CoefficientOptions coeffOptions
			= new VectorCoefficientOptions().setIdx(coeffs.size())
				.setType(new TypePrimitive(TypePrimitive.TYPE_INT32))
				.setLineNumber(this.lineNumber);

		VectorCoefficient valCoeff
			= (VectorCoefficient) this.coefficientFactory.getCoefficient(
				VectorCoefficient.class,
				coeffOptions
			);
		coeffs.add(valCoeff);

		this.left
			= valCoeff.addToExpr(this.left, coeffs, new TypePrimitive(TypePrimitive.TYPE_INT32));

		this.left.setCtx(this.getCtx());
		this.left.setType(new TypePrimitive(4));
	}

}
