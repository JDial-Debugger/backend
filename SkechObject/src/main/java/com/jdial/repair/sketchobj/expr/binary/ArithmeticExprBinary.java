package sketchobj.expr.binary;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import coefficient.Coefficient;
import coefficient.CoefficientOptions;
import coefficient.ScalarCoefficient;
import coefficient.ScalarCoefficientOptions;
import coefficient.VectorCoefficient;
import coefficient.VectorCoefficientOptions;
import sketchobj.core.Type;
import sketchobj.core.TypeArray;
import sketchobj.core.TypePrimitive;
import sketchobj.expr.ExprVar;
import sketchobj.expr.Expression;

public abstract class ArithmeticExprBinary extends NumericExprBinary {

	public ArithmeticExprBinary(ExprBinaryOptions options) {
		super(options);
	}

	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {

		this.left.checkAtom();
		this.right.checkAtom();

		this.addScalarsToAtomChild(coeffs, this.right);
		this.addScalarsToAtomChild(coeffs, this.left);

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

					ScalarCoefficient changeCoeff = this.getChangeCoeff(coeffs);
					coeffs.add(changeCoeff);

					Expression addLiveVar
						= changeCoeff.modifyExpr(new ExprVar(liveVar.getKey(), this.getType()));

					this.right = new Add(right, addLiveVar, this.lineNumber);
				}
			}

			VectorCoefficient valCoeff = this.getValCoeff(coeffs);
			coeffs.add(valCoeff);
			this.right = valCoeff.addToExpr(this.right, coeffs, (TypePrimitive) this.getType());
		}
	}

	private void addScalarsToAtomChild(List<Coefficient> coeffs, Expression childToModify) {

		if (childToModify.isAtom()) {
			ScalarCoefficient changeCoeff = this.getChangeCoeff(coeffs);
			coeffs.add(changeCoeff);

			this.replaceChild(childToModify, changeCoeff.modifyExpr(childToModify));
		} else {

			childToModify.setCtx(this.getCtx());
			childToModify.setType(this.getType());
			childToModify.insertCoeffs(coeffs);
		}
	}

	private ScalarCoefficient getChangeCoeff(List<Coefficient> coeffs) {
		CoefficientOptions options
			= new ScalarCoefficientOptions().setIsAdditive(false)
				.setIdx(coeffs.size())
				.setType((TypePrimitive) this.getType())
				.setLineNumber(this.lineNumber);
		return (ScalarCoefficient) this.coefficientFactory.getCoefficient(
			ScalarCoefficient.class,
			options
		);
	}

	private void replaceChild(Expression originalChild, Expression newChild) {
		if (originalChild == this.left) {
			this.left = newChild;
		} else if (originalChild == this.right) {
			this.right = newChild;
		}
	}

	private VectorCoefficient getValCoeff(List<Coefficient> coeffs) {

		CoefficientOptions options
			= new VectorCoefficientOptions().setIdx(coeffs.size())
				.setType((TypePrimitive) this.getType())
				.setLineNumber(this.lineNumber);
		return (VectorCoefficient) this.coefficientFactory.getCoefficient(
			VectorCoefficient.class,
			options
		);
	}
}
