package coefficient;

import java.util.List;

import sketch.input.SketchScript;
import sketchobj.core.TypePrimitive;
import sketchobj.expr.ExprConstInt;
import sketchobj.expr.ExprConstant;
import sketchobj.expr.ExprFuncCall;
import sketchobj.expr.ExprVar;
import sketchobj.expr.binary.ExprBinary;
import sketchobj.expr.binary.ExprBinaryFactory;
import sketchobj.stmts.Statement;

/**
 * Represents possible places to insert values into the orignial user's code. This is represented in
 * the input sketch script as a function call to a function containing sketch holes (???) that
 * sketch can then solve for Example: original user code: x = a + b; translates to: bit coeff5change
 * = ??; int Coeff5(){ if(coeff5change == 0){ return 0;}
 *
 * if(??){ return 1;}
 *
 * return -1; }
 *
 * int coeff6change = ??; int Coeff6(){ if(??){ return 0;}
 *
 * return coeff6change; }
 *
 * bit coeff7change = ??; int Coeff7(){ if(coeff7change == 0){ return 1;}
 *
 * if(??){ return 0;}
 *
 * return -1; }
 *
 * bit coeff8change = ??; int Coeff8(){ if(coeff8change == 0){ return 1;}
 *
 * if(??){ return 0;}
 *
 * return -1; }
 *
 * x = Coeff8() * a + Coeff7() * b + Coeff5() * Coeff6()
 *
 */
public abstract class Coefficient {

	public static final String CHANGE_SUFFIX = "_change";
	public static final String PREFIX = SketchScript.VAR_PREFIX + "coeff_";
	protected ExprBinaryFactory binaryExprFactory;
	protected int lineNumber;

	protected String name;
	// Expression closest in the AST that contains the expression that this
	// coefficient is in
	protected ExprBinary parentExpr;
	// Statement closest in the AST that contains the expression that this
	// coefficient is in
	private Statement parentStmt;
	// True if this coefficient should be added to the original source code
	protected boolean repaired;

	// Keeps track of what this coefficient is repaired to (if any at all)
	protected ExprConstant repairValue;
	protected TypePrimitive type;

	public Coefficient(CoefficientOptions options) {
		options.handleMissingRequiredOptions();
		this.name = PREFIX + options.getIdx();
		this.type = options.getType();
		this.lineNumber = options.getLineNumber();
		this.parentStmt = options.getParent();
		this.binaryExprFactory = options.getBinaryExprFactory();
	}

	/**
	 * Returns a declaration a boolean that holds whether this coeff will be included in the sketch
	 * result
	 */
	public abstract Statement getChangeDecl();

	/**
	 * Marks this coefficient as not being added to the original source code
	 */
	public abstract void removeFromSource();

	public abstract List<Statement> getDeclFunc();

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Coefficient other = (Coefficient) obj;
		if (this.lineNumber != other.lineNumber) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.parentStmt == null) {
			if (other.parentStmt != null) {
				return false;
			}
		} else if (!this.parentStmt.equals(other.parentStmt)) {
			return false;
		}
		if (this.repairValue == null) {
			if (other.repairValue != null) {
				return false;
			}
		} else if (!this.repairValue.equals(other.repairValue)) {
			return false;
		}
		if (this.type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!this.type.equals(other.type)) {
			return false;
		}
		return true;
	}

	/**
	 * Name for the variable that keeps track of whether this coefficient is added to the sketch
	 * repair
	 *
	 * @return - The name of the change variable in the sketch script
	 */
	public String getChangeName() {
		return this.name + Coefficient.CHANGE_SUFFIX;
	}

	/**
	 * Expression for the variable that keeps track of whether this coefficient is added to the
	 * sketch repair
	 *
	 * @return
	 */
	public ExprVar getChangeVar() {
		return new ExprVar(this.getChangeName());
	}

	public ExprFuncCall getFuncCall() {
		return new ExprFuncCall(this.name);
	}

	public int getLineNumber() {
		return this.lineNumber;
	}

	public String getName() {
		return this.name;
	}

	public Statement getParentStmt() {
		return this.parentStmt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.lineNumber;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.parentStmt == null) ? 0 : this.parentStmt.hashCode());
		result = prime * result + ((this.repairValue == null) ? 0 : this.repairValue.hashCode());
		result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
		return result;
	}

	public void setParentStmt(Statement stmt) {
		this.parentStmt = stmt;
	}

	/**
	 * Sets the value for this coefficient to be added to the original source code
	 *
	 * @param value - the value to go into the source code
	 */
	public void setRepairValue(int value) {
		if (this.type.getType() != TypePrimitive.TYPE_INT32) {
			throw new IllegalArgumentException(
				"Wrong repair value type: " + value + " for coefficient of type: " + this.type
			);
		}
		this.repairValue = new ExprConstInt(value);
		this.repaired = true;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
