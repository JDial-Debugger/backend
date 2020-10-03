package sketchobj.expr.binary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import coefficient.Coefficient;
import constraintfactory.ConstData;
import constraintfactory.ExternalFunction;
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
	private static final Logger logger = LoggerFactory.getLogger(ExprBinary.class);

	public ExprBinary(ExprBinaryOptions options) {
		this.left = options.left;
		this.right = options.right;
		this.lineNumber = options.lineNumber;
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

		CondensibleExpression combineableExpr = this.condense();
		if (combineableExpr.isCondensible) {
			return combineableExpr.condensedExpression.toString();
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
	 * This is meant to be overridden for special cases that can condense
	 */
	public CondensibleExpression condense() {
		return new CondensibleExpression();
	}

	protected NumericVals<Integer> getValsFromExpressions(LeftAndRightExpressions expressions) {
		this.condenseExpressions(expressions);
		if (this.bothSidesAreExprConstInts(expressions)) {
			return this.getConstIntVals(
				(ExprConstInt) expressions.left,
				(ExprConstInt) expressions.right
			);
		}
		return new NumericVals<Integer>();
	}

	protected void condenseExpressions(LeftAndRightExpressions expressions) {
		CondensibleExpression leftCondensed = this.condenseExpressions(expressions.left);
		if (leftCondensed.isCondensible) {
			expressions.left = leftCondensed.condensedExpression;
		}
		CondensibleExpression rightCondensed = this.condenseExpressions(expressions.right);
		if (rightCondensed.isCondensible) {
			expressions.right = rightCondensed.condensedExpression;
		}

	}

	private CondensibleExpression condenseExpressions(Expression expr) {
		if (expr instanceof ExprBinary) {
			return ((ExprBinary) expr).condense();
		}
		return new CondensibleExpression();
	}

	private boolean bothSidesAreExprConstInts(LeftAndRightExpressions expressions) {
		if (expressions.left instanceof ExprConstInt && expressions.right instanceof ExprConstInt) {
			return true;
		}
		return false;
	}

	private NumericVals<Integer> getConstIntVals(ExprConstInt left, ExprConstInt right) {
		return new NumericVals<Integer>(left.getVal(), right.getVal());
	}

	protected class LeftAndRightExpressions {
		public Expression left, right;

		public LeftAndRightExpressions(Expression left, Expression right) {
			this.left = left;
			this.right = right;
		}
	}

	public void setLeft(Expression expr) {
		this.left = expr;
	}
	
	public void setRight(Expression expr) {
		this.right = expr;
	}

}
