package sketch_input;

import java.util.List;

import sketchobj.core.Type;
import sketchobj.core.TypePrimitive;
import sketchobj.expr.ExprConstInt;
import sketchobj.expr.ExprConstant;
import sketchobj.expr.ExprFuncCall;
import sketchobj.expr.ExprVar;
import sketchobj.stmts.Statement;

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
	protected TypePrimitive type;
	//Keeps track of what this coefficient is repaired to (if any at all)
	protected ExprConstant repairValue;
	
	public static final String PREFIX = SketchScript.VAR_PREFIX + "coeff_";
	public static final String CHANGE_SUFFIX = "_change";

	public Coefficient(int idx, TypePrimitive type) {
		this(idx, type, -1);
	}
	
	public Coefficient(int idx, TypePrimitive type, int lineNumber) {
		this.name = PREFIX + idx;
		this.type = type;
		this.lineNumber = lineNumber;
	}
	
	/**
	 * Sets the value for this coefficient to be added to the original source
	 * code
	 * @param value - the value to go into the source code
	 */
	public void setRepairValue(int value) {
		if (this.type.getType() != TypePrimitive.TYPE_INT32) {
			throw new IllegalArgumentException("Wrong repair value type: " + 
					value + " for coefficient of type: " + this.type);
		}
		this.repairValue = new ExprConstInt(value);
	}
	
	/**
	 * Name for the variable that keeps track of whether this
	 * coefficient is added to the sketch repair
	 * @return - The name of the change variable in the sketch script
	 */
	public String getChangeName() {
		return this.name + Coefficient.CHANGE_SUFFIX;
	}

	/**
	 * Expression for the variable that keeps track of whether this
	 * coefficient is added to the sketch repair
	 * @return
	 */
	public ExprVar getChangeVar() {
		return new ExprVar(this.getChangeName());
	}

	/**
	 * Returns a declaration a boolean that holds whether this
	 * coeff will be included in the sketch result
	 */
	public abstract Statement getChangeDecl();
	
	public abstract List<Statement> getDeclFunc();
	
	public String getName() { return this.name; }
	
	public int getLineNumber() { return this.lineNumber; }
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public ExprFuncCall getFuncCall() {
		return new ExprFuncCall(this.name);
	}
}
