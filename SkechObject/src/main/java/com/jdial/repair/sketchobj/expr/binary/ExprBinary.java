package sketchobj.expr.binary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import constraintfactory.ConstData;
import constraintfactory.ExternalFunction;
import sketch.input.Coefficient;
import sketchobj.core.SketchObject;
import sketchobj.core.Type;
import sketchobj.expr.ExprConstInt;
import sketchobj.expr.ExprConstant;
import sketchobj.expr.ExprFuncCall;
import sketchobj.expr.ExprVar;
import sketchobj.expr.Expression;

public abstract class ExprBinary extends Expression {
	
	protected Expression left;
	protected Expression right;
	protected Operator operator;
	protected int lineNumber;
	private boolean doesIgnoreLeft;
	private boolean doesIgnoreRight;
	
	public ExprBinary(Expression left, Operator operator, Expression right) {
		this.left = left;
		this.operator = operator;
		this.right = right;
	}
	
	public ExprBinary(Expression left, Operator operator, Expression right, int lineNumber) {
		this(left, operator, right);
		this.lineNumber = lineNumber;
	}

	public Operator getOperator() {
		return this.operator;
	}

	public Expression getLeft() {
		return this.left;
	}

	public Expression getRight() {
		return this.right;
	}

	public void ignoreLeft() {
		this.doesIgnoreLeft = true;
	}

	public void ignoreRight() {
		this.doesIgnoreRight = true;
	}

	@Override
	public String toString() {

		if (this.doesIgnoreLeft && this.doesIgnoreRight) {
			return "";
		} else if (this.doesIgnoreLeft) {
			return this.right.toString();
		} else if (this.doesIgnoreRight) {
			return this.left.toString();
		}

		CombineableExpression combineableExpr = this.combine();
		if (combineableExpr.isCombineable) {
			return combineableExpr.combinedExpression.toString();
		}
		StringBuilder result = new StringBuilder();

		if (left instanceof ExprConstInt || left instanceof ExprVar) {
			result.append(this.left.toString() + " " + this.operator.toString() + " ");
		} else {
			result.append("(" + this.left.toString() + ")" + " " + this.operator.toString() + " ");
		}
		if (right instanceof ExprConstInt || right instanceof ExprVar) {
			result.append(this.right.toString());
		} else {
			result.append("(" + this.right.toString() + ")");
		}
		return result.toString();
	}
	
	@Override
	public ConstData replaceConst(int index) {
		String noStr = null;
		return this.replaceConst(index, noStr);
	}

	@Override
	public ConstData replaceConst(int index, String string) {
		List<SketchObject> toAdd = new ArrayList<SketchObject>();
		if (left instanceof ExprConstant) {
			int value = ((ExprConstant) left).getVal();
			Type t = ((ExprConstant) left).getType();
			left = new ExprFuncCall("Const" + index, new ArrayList<Expression>());
			toAdd.add(this);
			return new ConstData(t, toAdd, index + 1, value, string, this.lineNumber);
		}
		if (right instanceof ExprConstant) {
			int value = ((ExprConstant) right).getVal();
			Type t = ((ExprConstant) right).getType();
			right = new ExprFuncCall("Const" + index, new ArrayList<Expression>());
			return new ConstData(t, toAdd, index + 1, value, string, this.lineNumber);
		}
		toAdd.add(left);
		toAdd.add(right);
		return new ConstData(null, toAdd, index, 0, string, this.lineNumber);
	}
	
	@Override
	public List<ExternalFunction> extractExternalFuncs(List<ExternalFunction> externalFuncNames) {
		externalFuncNames = left.extractExternalFuncs(externalFuncNames);
		externalFuncNames = right.extractExternalFuncs(externalFuncNames);
		return externalFuncNames;
	}

	@Override
	public void checkAtom() {
		if (this.left.isAtom() || this.right.isAtom()) {
			this.setAtom(true);
		} else {
			this.setAtom(false);
		}
	}
	
	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {
		this.left.setBoolean(true);
		this.right.setBoolean(true);
		left.setCtx(this.getCtx());
		right.setCtx(this.getCtx());
		left.insertCoeffs(coeffs);
		right.insertCoeffs(coeffs);
	}
	
	@Override
	public Set<String> getVarNames() {
		Set<String> names = new HashSet<String>();
		names.addAll(this.getLeft().getVarNames());
		names.addAll(this.getRight().getVarNames());
		return names;
	}
	
	/*
	 * This is meant to be overridden for special cases
	 */
	public CombineableExpression combine() {
		return new CombineableExpression();
	}

	protected NumericVals<Integer> getValsFromExpressions() {
		if (this.bothSidesAreExprConstInts()) {
			return this.getConstIntVals((ExprConstInt) this.left, (ExprConstInt) this.right);
		}
		return new NumericVals<Integer>();
	}

	private boolean bothSidesAreExprConstInts() {
		if (this.left instanceof ExprConstInt && this.right instanceof ExprConstInt) {
			return true;
		}
		return false;
	}

	private NumericVals<Integer> getConstIntVals(ExprConstInt lhs, ExprConstInt rhs) {
		return new NumericVals<Integer>(lhs.getVal(), rhs.getVal());
	}
}
