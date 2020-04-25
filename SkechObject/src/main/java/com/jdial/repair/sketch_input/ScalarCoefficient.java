package sketch_input;

import java.util.ArrayList;

import sketchobj.core.Function;
import sketchobj.core.Function.FcnType;
import sketchobj.core.Parameter;
import sketchobj.core.Type;
import sketchobj.expr.ExprBinary;
import sketchobj.expr.ExprConstInt;
import sketchobj.expr.ExprSketchHole;
import sketchobj.expr.ExprVar;
import sketchobj.expr.Expression;
import sketchobj.stmts.Statement;
import sketchobj.stmts.StmtBlock;
import sketchobj.stmts.StmtIfThen;
import sketchobj.stmts.StmtReturn;

/**
 * Represents a coeffecient that either inverts (-1 * x), negates (0 * x) or does
 * nothing (1 * x)
 *
 */
public class ScalarCoefficient extends Coefficient {

	/**
	 * Creates a scalar coefficient with the given unique index and type
	 * @param idx - an index to uniquely identify the coefficient
	 * @param type - the type of the coefficient
	 */
	public ScalarCoefficient(int idx, Type type) {
		super(idx, type);
	}
	
	public ScalarCoefficient(int idx, Type type, int lineNumber) {
		super(idx, type, lineNumber);
	}

	@Override
	public Function getDeclFunc() {
		
		//return if no change made to expr this coeff is attached to
		Expression keepCond = new ExprSketchHole();// ??
		StmtReturn keepReturn = new StmtReturn(new ExprConstInt(1));
		Statement keepIf = new StmtIfThen(keepCond, keepReturn);
		
		//remove the expr this coeff is attached to
		Expression removeCond = new ExprBinary(
				new ExprVar(super.name + Coefficient.CHANGE_SUFFIX), 
				"==", 
				new ExprConstInt(0), 0);
		StmtReturn removeReturn = new StmtReturn(new ExprConstInt(0));
		Statement removeIf = new StmtIfThen(removeCond, removeReturn);
		//return to invert the expr this coeff is attached to
		StmtReturn invertReturn = new StmtReturn(new ExprConstInt(-1));
		
		StmtBlock coeffFuncBody = new StmtBlock();
		coeffFuncBody.addStmt(keepIf);
		coeffFuncBody.addStmt(removeIf);
		coeffFuncBody.addStmt(invertReturn);
		
		return new Function(
				super.name, 
				type, 
				new ArrayList<Parameter>(), 
				coeffFuncBody, 
				FcnType.Static);
	}

	/**
	 * Multiples this coeff by the given expression
	 * @param toModify - the expression to multiply
	 * @return
	 */
	public ExprBinary modifyExpr(Expression toModify) {
		return new ExprBinary(
				this.getFuncCall(),
				ExprBinary.BINOP_MUL,
				toModify,
				this.lineNumber);
	}
}
