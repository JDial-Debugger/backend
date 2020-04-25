package sketch_input;

import java.util.ArrayList;

import sketchobj.core.Function;
import sketchobj.core.Function.FcnType;
import sketchobj.core.Parameter;
import sketchobj.core.Type;
import sketchobj.expr.ExprConstInt;
import sketchobj.expr.ExprSketchHole;
import sketchobj.expr.ExprVar;
import sketchobj.expr.Expression;
import sketchobj.stmts.Statement;
import sketchobj.stmts.StmtBlock;
import sketchobj.stmts.StmtIfThen;
import sketchobj.stmts.StmtReturn;

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

	@Override
	public Function getDeclFunc() {
		
		Expression changeCond = new ExprSketchHole();
		StmtReturn noChangeReturn = new StmtReturn(new ExprConstInt(0), 0);
		StmtReturn changeReturn = new StmtReturn(
				new ExprVar(super.name + Coefficient.CHANGE_SUFFIX), 0);
		Statement changeIf = new StmtIfThen(changeCond, noChangeReturn, null, 0);
		
		StmtBlock coeffFuncBody = new StmtBlock();
		coeffFuncBody.addStmt(changeIf);
		coeffFuncBody.addStmt(changeReturn);
		return new Function(
				super.name, 
				super.type, 
				new ArrayList<Parameter>(), 
				coeffFuncBody, 
				FcnType.Static);
	}
}
