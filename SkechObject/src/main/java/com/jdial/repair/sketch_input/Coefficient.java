package sketch_input;

import java.util.ArrayList;

import sketchobj.core.Function;
import sketchobj.core.Function.FcnType;
import sketchobj.core.Parameter;
import sketchobj.core.Type;
import sketchobj.expr.ExprBinary;
import sketchobj.expr.ExprConstInt;
import sketchobj.expr.ExprFuncCall;
import sketchobj.expr.ExprSketchHole;
import sketchobj.expr.ExprVar;
import sketchobj.expr.Expression;
import sketchobj.stmts.Statement;
import sketchobj.stmts.StmtBlock;
import sketchobj.stmts.StmtIfThen;
import sketchobj.stmts.StmtReturn;

/**
 * Represents possible places to insert values into the orignial
 * user's code. This is represented in the input sketch script 
 * as a function call to a function containing sketch holes (???)
 * that sketch can then solve for
 * Example:
 * original user code:
 * x = a + b;
 * translates to:
bit coeff5change = ??;
 int Coeff5(){
if(coeff5change == 0){
return 0;}

if(??){
return 1;}

return -1;
}

int coeff6change = ??;
 int Coeff6(){
if(??){
return 0;}

return coeff6change;
}

bit coeff7change = ??;
 int Coeff7(){
if(coeff7change == 0){
return 1;}

if(??){
return 0;}

return -1;
}

bit coeff8change = ??;
 int Coeff8(){
if(coeff8change == 0){
return 1;}

if(??){
return 0;}

return -1;
}

x = Coeff8() * a + Coeff7() * b + Coeff5() * Coeff6()
 *
 */
public abstract class Coefficient {
	protected String name;
	protected int lineNumber;
	protected Type type;
	
	public static final String PREFIX = "Coeff";
	public static final String CHANGE_SUFFIX = "Change";

	public Coefficient(int idx, Type type) {
		this.name = PREFIX + idx;
		this.type = type;
	}
	
	public abstract Function getDeclFunc();
	
	public String getName() { return this.name; }
	
	public int getLineNumber() { return this.lineNumber; }
	public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }
	
	@Override
	public String toString() {
		return new ExprFuncCall(this.name).toString();
	}
}
