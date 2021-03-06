package coefficient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sketchobj.core.Function;
import sketchobj.core.Function.FcnType;
import sketchobj.core.Parameter;
import sketchobj.core.TypePrimitive;
import sketchobj.expr.ExprConstInt;
import sketchobj.expr.ExprSketchHole;
import sketchobj.expr.ExprVar;
import sketchobj.expr.Expression;
import sketchobj.expr.binary.Equals;
import sketchobj.expr.binary.ExprBinary;
import sketchobj.expr.binary.ExprBinaryOptions;
import sketchobj.expr.binary.Multiply;
import sketchobj.stmts.Statement;
import sketchobj.stmts.StmtBlock;
import sketchobj.stmts.StmtFuncDecl;
import sketchobj.stmts.StmtIfThen;
import sketchobj.stmts.StmtReturn;
import sketchobj.stmts.StmtVarDecl;

/**
 * Represents a coeffecient that either inverts (-1 * x), negates (0 * x) or does nothing (1 * x)
 *
 */
public class ScalarCoefficient extends Coefficient {

	// true if this coefficient being 1 or -1 adds a new expression to the code
	// false if this coefficient being 1 or -1 removes an expression from the code
	private boolean isAdditive;

	public ScalarCoefficient(ScalarCoefficientOptions options) {
		super(options);
		this.isAdditive = options.getIsAdditive();
	}

	@Override
	public List<Statement> getDeclFunc() {

		ExprVar coeffExprVar = new ExprVar(super.name + Coefficient.CHANGE_SUFFIX);
		Expression changeCond
			= this.binaryExprFactory.getExprBinary(
				Equals.class,
				new ExprBinaryOptions(coeffExprVar, new ExprConstInt(0))
			);
		StmtReturn changeReturn = null;
		if (this.isAdditive) {
			changeReturn = new StmtReturn(new ExprConstInt(0));
		} else {
			changeReturn = new StmtReturn(new ExprConstInt(1));
		}
		Statement changeIf = new StmtIfThen(changeCond, changeReturn);

		// return if no change made to expr this coeff is attached to
		Expression holeCond = new ExprSketchHole();
		StmtReturn holeReturn = null;
		if (this.isAdditive) {
			holeReturn = new StmtReturn(new ExprConstInt(1));
		} else {
			holeReturn = new StmtReturn(new ExprConstInt(0));
		}
		Statement holeIf = new StmtIfThen(holeCond, holeReturn);

		// return to invert the expr this coeff is attached to
		StmtReturn invertReturn = new StmtReturn(new ExprConstInt(-1));

		StmtBlock coeffFuncBody = new StmtBlock();
		coeffFuncBody.addStmt(changeIf);
		coeffFuncBody.addStmt(holeIf);
		coeffFuncBody.addStmt(invertReturn);
		return Arrays.asList(
			this.getChangeDecl(),
			new StmtFuncDecl(
				new Function(
					super.name,
					type,
					new ArrayList<Parameter>(),
					coeffFuncBody,
					FcnType.Static
				)
			)
		);
	}

	/**
	 * @return example: bit jdial_coeff4_change = ??
	 */
	@Override
	public Statement getChangeDecl() {
		return new StmtVarDecl(
			TypePrimitive.bittype,
			this.getChangeName(),
			new ExprSketchHole(),
			0
		);
	}

	/**
	 * Multiples this coeff by the given expression
	 * 
	 * @param toModify - the expression to multiply
	 * @return - the resulting expression after toModify is multiplied by this coefficient
	 */
	public ExprBinary modifyExpr(Expression toModify) {
		this.parentExpr
			= this.binaryExprFactory.getExprBinary(
				Multiply.class,
				new ExprBinaryOptions(this.getFuncCall(), toModify)
			);
		return this.parentExpr;
	}

	@Override
	public void removeFromSource() {
		this.parentExpr.ignoreLeft();
	}

	@Override
	public void setRepairValue(int value) {
		super.setRepairValue(value);
		this.parentExpr.setLeft(this.repairValue);
	}
}
