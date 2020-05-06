package sketch_input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import sketchobj.stmts.StmtFuncDecl;
import sketchobj.stmts.StmtIfThen;
import sketchobj.stmts.StmtReturn;
import sketchobj.stmts.StmtVarDecl;

/*
int coeff6change = ??;
 int Coeff6(){
if(??){
return 0;}

return coeff6change;
}
*/

/**
 * A coefficient that can be any value
 *
 */
public class VectorCoefficient extends Coefficient {

	public VectorCoefficient(int idx, Type type) {
		super(idx, type);
	}
	
	public VectorCoefficient(int idx, Type type, int lineNumber) {
		super(idx, type, lineNumber);
	}

	@Override
	public List<Statement> getDeclFunc() {
		
		Expression changeCond = new ExprSketchHole();
		StmtReturn noChangeReturn = new StmtReturn(new ExprConstInt(0), 0);
		StmtReturn changeReturn = new StmtReturn(
				new ExprVar(super.name + Coefficient.CHANGE_SUFFIX), 0);
		Statement changeIf = new StmtIfThen(changeCond, noChangeReturn, null, 0);
		
		StmtBlock coeffFuncBody = new StmtBlock();
		coeffFuncBody.addStmt(changeIf);
		coeffFuncBody.addStmt(changeReturn);
		return Arrays.asList(
			this.getChangeDecl(),
			new StmtFuncDecl(new Function(
				super.name, 
				super.type, 
				new ArrayList<Parameter>(), 
				coeffFuncBody, 
				FcnType.Static)));
	}
	
	/**
	 * @return example: bit jdial_coeff4_change = ??
	 */
	@Override
	public Statement getChangeDecl() {
		return new StmtVarDecl(
				super.type, 
				this.getChangeName(),
				new ExprSketchHole(), 
				0);
	}
	
	/**
	 * Adds this times a scalar to the given expression
	 * example: in: int a = 5; out: int a = 5 + coeff1() * this;
	 * @param toModify - the expression to add the coefficient to
	 * @param coeffs - will add any created coefficients to this list
	 * @param type - the type of the expression to modify
	 * @param lineNumber - the line number of toModify
	 * @return - A new expression containing toModify added with this
	 * times a scalar
	 */
	public ExprBinary addToExpr(
			Expression toModify, 
			List<Coefficient> coeffs, 
			Type type) {
		
		ScalarCoefficient changeCoeff = 
				new ScalarCoefficient(coeffs.size(), type, this.lineNumber, true);
		coeffs.add(changeCoeff);
		
		ExprBinary coeffBinaryExpr = 
				changeCoeff.modifyExpr(this.getFuncCall());
		
		return new ExprBinary(
				toModify,
				ExprBinary.BINOP_ADD, 
				coeffBinaryExpr,
				this.lineNumber);
		
	}
}
