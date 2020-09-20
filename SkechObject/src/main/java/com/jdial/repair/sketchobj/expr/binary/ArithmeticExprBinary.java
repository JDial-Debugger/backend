package sketchobj.expr.binary;

import java.util.List;
import java.util.Map;

import sketch.input.Coefficient;
import sketch.input.ScalarCoefficient;
import sketch.input.VectorCoefficient;
import sketchobj.core.Type;
import sketchobj.core.TypeArray;
import sketchobj.core.TypePrimitive;
import sketchobj.expr.ExprVar;
import sketchobj.expr.Expression;

public abstract class ArithmeticExprBinary extends NumericExprBinary {

	ArithmeticExprBinary(Expression left, Operator operator, Expression right) {
		super(left, operator, right);
	}

	public ArithmeticExprBinary(Expression left, Operator operator, Expression right, int lineNumber) {
		super(left, operator, right, lineNumber);
	}

	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {
		// TODO Auto-generated method stub

		this.left.checkAtom();
		this.right.checkAtom();

		if (right.isAtom()) {
			ScalarCoefficient changeCoeff
				= new ScalarCoefficient(
					coeffs.size(),
					(TypePrimitive) this.getType(),
					this.lineNumber,
					false
				);
			coeffs.add(changeCoeff);

			this.right = changeCoeff.modifyExpr(this.right);
		} else {

			right.setCtx(this.getCtx());
			right.setType(this.getType());
			right.insertCoeffs(coeffs);
		}

		if (left.isAtom()) {

			ScalarCoefficient changeCoeff
				= new ScalarCoefficient(
					coeffs.size(),
					(TypePrimitive) this.getType(),
					this.lineNumber,
					false
				);
			coeffs.add(changeCoeff);

			this.left = changeCoeff.modifyExpr(this.left);

		} else {

			this.left.setCtx(this.getCtx());
			this.left.setType(this.getType());
			this.left.insertCoeffs(coeffs);
		}

		if (this.getType() instanceof TypeArray) {
			return;
		}

		if (this.isLCadded()) {

			left.setLCadded(true);
			right.setLCadded(true);
		} else {

			// For each live variable of the same type of this, consider adding
			// it to the expression
			for (Map.Entry<String, Type> liveVar : this.getCtx().getAllVars().entrySet()) {

				TypePrimitive curVarType = (TypePrimitive) liveVar.getValue();

				if (curVarType.getType() == ((TypePrimitive) this.getType()).getType()) {

					ScalarCoefficient changeCoeff
						= new ScalarCoefficient(
							coeffs.size(),
							(TypePrimitive) this.getType(),
							this.lineNumber,
							true
						);
					coeffs.add(changeCoeff);

					Expression addLiveVar
						= changeCoeff.modifyExpr(new ExprVar(liveVar.getKey(), this.getType()));

					this.right = new Add(right, addLiveVar, this.lineNumber);
				}
			}

			VectorCoefficient valCoeff
				= new VectorCoefficient(
					coeffs.size(),
					(TypePrimitive) this.getType(),
					this.lineNumber
				);
			coeffs.add(valCoeff);
			this.right = valCoeff.addToExpr(this.right, coeffs, (TypePrimitive) this.getType());
		}
	}
}
