package sketchobj.expr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import constraintfactory.ConstData;
import constraintfactory.ExternalFunction;
import sketch.input.Coefficient;
import sketch.input.ScalarCoefficient;
import sketch.input.VectorCoefficient;
import sketchobj.core.SketchObject;
import sketchobj.core.Type;
import sketchobj.core.TypeArray;
import sketchobj.core.TypePrimitive;

public class ExprBinary extends Expression {
	
	public static final int BINOP_ADD = 1;
	public static final int BINOP_SUB = 2;
	public static final int BINOP_MUL = 3;
	public static final int BINOP_DIV = 4;
	public static final int BINOP_MOD = 5;
	public static final int BINOP_AND = 6;
	public static final int BINOP_OR = 7;
	public static final int BINOP_EQ = 8;
	public static final int BINOP_NEQ = 9;
	public static final int BINOP_LT = 10;
	public static final int BINOP_LE = 11;
	public static final int BINOP_GT = 12;
	public static final int BINOP_GE = 13;
	// These are bitwise AND/OR/XOR:
	public static final int BINOP_BAND = 14;
	public static final int BINOP_BOR = 15;
	public static final int BINOP_BXOR = 16;

	public static final int BINOP_LSHIFT = 17;
	public static final int BINOP_RSHIFT = 18;
	public static final int BINOP_SELECT = 19;
	public static final int BINOP_TEQ = 20;

	private int op;
	private Expression left, right;
	private ExprBinary alias;
	//	When removing unused coefficients from the AST, we ignore sides of
	//binary expressions
	private boolean ignoringLeft, ignoringRight;

	/**
	 * Create a new binary expression given the operation and the left and right
	 * child nodes. Requires that op is a valid operator code and that left and
	 * right are non-null.
	 *
	 * @param context
	 *            file and line number this expression corresponds to
	 * @param op
	 *            BINOP_ operator combining the two expressions
	 * @param left
	 *            expression on the left of the operator
	 * @param right
	 *            expression on the right of the operator
	 * @param i
	 */
	public ExprBinary(int op, Expression left, Expression right, int i) {
		this.op = op;
		this.left = left;
		left.setParent(this);
		this.right = right;
		right.setParent(this);
		alias = this;
		this.lineNumber = i;
	}

	@Override
	public ExprBinary clone() {
		return new ExprBinary(
				this.op, this.left.clone(), this.right.clone(), this.lineNumber);
	}
	
	public ExprBinary(Expression left, int op, Expression right, int line) {
		this.left = left;
		this.left.setParent(this);
		this.right = right;
		this.right.setParent(this);
		this.lineNumber = line;
		this.op = op;
		this.alias = this;
	}
	
	public ExprBinary(Expression left, int op, Expression right) {
		this(left, op, right, 0);
	}

	/**
	 * Create a new binary expression given the operation and the left and right
	 * child nodes. Requires that op is a valid operator code and that left and
	 * right are non-null.
	 *
	 * @param op
	 *            BINOP_ operator combining the two expressions
	 * @param left
	 *            expression on the left of the operator
	 * @param right
	 *            expression on the right of the operator
	 */
	public ExprBinary(Expression left, String sop, Expression right, int line) {
		this.left = left;
		left.setParent(this);
		this.right = right;
		right.setParent(this);
		int lop = -1;
		this.lineNumber = line;

		//TODO refractor this
		if (sop.equals("+")) {
			lop = BINOP_ADD;
		} else if (sop.equals("-")) {
			lop = BINOP_SUB;
		} else if (sop.equals("*")) {
			lop = BINOP_MUL;
		} else if (sop.equals("/")) {
			lop = BINOP_DIV;
		} else if (sop.equals("%")) {
			lop = BINOP_MOD;
		} else if (sop.equals("&&")) {
			lop = BINOP_AND;
		} else if (sop.equals("||")) {
			lop = BINOP_OR;
		} else if (sop.equals("==")) {
			lop = BINOP_EQ;
		} else if (sop.equals("!=")) {
			lop = BINOP_NEQ;
		} else if (sop.equals("<")) {
			lop = BINOP_LT;
		} else if (sop.equals("<=")) {
			lop = BINOP_LE;
		} else if (sop.equals(">")) {
			lop = BINOP_GT;
		} else if (sop.equals(">=")) {
			lop = BINOP_GE;
		} else if (sop.equals("&")) {
			lop = BINOP_BAND;
		} else if (sop.equals("|")) {
			lop = BINOP_BOR;
		} else if (sop.equals("^")) {
			lop = BINOP_BXOR;
		} else if (sop.equals("xor")) {
			lop = BINOP_BXOR;
		} else if (sop.equals(">>")) {
			lop = BINOP_RSHIFT;
		} else if (sop.equals("<<")) {
			lop = BINOP_LSHIFT;
		} else {
			throw new IllegalArgumentException("What is this operator: " + sop + " ??!!");
		}

		this.op = lop;
		alias = this;
	}


	/** */
	public ExprBinary getAlias() {
		return alias;
	}

	/**
	 * Returns the operator of this.
	 *
	 * @return BINOP_ operator code for this expression
	 */
	public int getOp() {
		return op;
	}

	/**
	 * Return the precedence of this expression's operator (higher means
	 * evaluate sooner).
	 */
	public int getOpPrec() {
		return opPrec(op);
	}

	/**
	 * Returns the left child expression of this.
	 *
	 * @return expression on the left-hand side of the operator
	 */
	public Expression getLeft() {
		return left;
	}

	
	/**
	 * Sets the left child expression of this.
	 *
	 * @param expression on the left-hand side of the operator
	 */
	public void setLeft(Expression left) {
		this.left = left;
	}
	
	/**
	 * Sets the right child expression of this.
	 *
	 * @param expression on the right-hand side of the operator
	 */
	public void setRight(Expression right) {
		this.right = right;
	}

	/**
	 * Returns the right child expression of this.
	 *
	 * @return expression on the right-hand side of the operator
	 */
	public Expression getRight() {
		return right;
	}

	/** Returns true iff this expression is a comparison. */
	public boolean isComparison() {
		return op == BINOP_EQ || op == BINOP_NEQ || op == BINOP_LT || op == BINOP_LE || op == BINOP_GT || op == BINOP_GE
				|| op == BINOP_TEQ;
	}

	public int hashCode() {
		return left.hashCode() ^ right.hashCode() ^ new Integer(op).hashCode();
	}

	public String getOpString() {
		String theOp;
		switch (op) {
		case ExprBinary.BINOP_ADD:
			theOp = "+";
			break;
		case ExprBinary.BINOP_SUB:
			theOp = "-";
			break;
		case ExprBinary.BINOP_MUL:
			theOp = "*";
			break;
		case ExprBinary.BINOP_DIV:
			theOp = "/";
			break;
		case ExprBinary.BINOP_MOD:
			theOp = "%";
			break;
		case ExprBinary.BINOP_AND:
			theOp = "&&";
			break;
		case ExprBinary.BINOP_OR:
			theOp = "||";
			break;
		case ExprBinary.BINOP_EQ:
			theOp = "==";
			break;
		case ExprBinary.BINOP_TEQ:
			theOp = "===";
			break;
		case ExprBinary.BINOP_NEQ:
			theOp = "!=";
			break;
		case ExprBinary.BINOP_LT:
			theOp = "<";
			break;
		case ExprBinary.BINOP_LE:
			theOp = "<=";
			break;
		case ExprBinary.BINOP_GT:
			theOp = ">";
			break;
		case ExprBinary.BINOP_GE:
			theOp = ">=";
			break;
		case ExprBinary.BINOP_BAND:
			theOp = "&";
			break;
		case ExprBinary.BINOP_BOR:
			theOp = "|";
			break;
		case ExprBinary.BINOP_BXOR:
			theOp = "^";
			break;
		case ExprBinary.BINOP_LSHIFT:
			theOp = "<<";
			break;
		case ExprBinary.BINOP_RSHIFT:
			theOp = ">>";
			break;
		case ExprBinary.BINOP_SELECT:
			theOp = "{|}";
			break;
		default:
			theOp = "? (" + op + ")";
			break;
		}
		return theOp;
	}

	public String toString() {
		
		if (this.ignoringLeft && this.ignoringRight) {
			return "";
		} else if (this.ignoringLeft) {
			return this.right.toString();
		} else if (this.ignoringRight) {
			return this.left.toString();
		}
		
		String theOp = getOpString();
		StringBuilder result = new StringBuilder();
		
		//merge ops on constants together
		if (this.left instanceof ExprConstInt && this.right instanceof ExprConstInt) {
			ExprConstInt leftInt = (ExprConstInt) this.left;
			ExprConstInt rightInt = (ExprConstInt) this.right;
			switch (this.op) {
				case BINOP_ADD:
					return leftInt.getVal() + rightInt.getVal() + "";
				case BINOP_SUB:
					return leftInt.getVal() - rightInt.getVal() + "";
				case BINOP_MUL:
					return leftInt.getVal() * rightInt.getVal() + "";
				case BINOP_DIV:
					return leftInt.getVal() / rightInt.getVal() + "";
				case BINOP_MOD:
					return leftInt.getVal() % rightInt.getVal() + "";
				default:
					break;
			}
		}
			
		if (left instanceof ExprConstInt || left instanceof ExprVar) {
			result.append(this.left.toString() + " " + theOp + " ");
		} else {
			result.append("(" + this.left.toString() +")" + " " + theOp + " ");
		}
		if (right instanceof ExprConstInt || right instanceof ExprVar) {
			result.append(this.right.toString());
		} else {
			result.append("(" + this.right.toString() + ")");
		}
		return result.toString();
	}
	
	public void ignoreLeft() {
		this.ignoringLeft = true;
	}
	
	public void ignoreRight() {
		this.ignoringLeft = true;
	}

	/** Return the precedence of OP (higher means evaluated sooner). */
	public static int opPrec(int op) {
		switch (op) {
		case ExprBinary.BINOP_OR:
			return 1;
		case ExprBinary.BINOP_AND:
			return 2;

		case ExprBinary.BINOP_BOR:
			return 10;
		case ExprBinary.BINOP_BXOR:
			return 11;
		case ExprBinary.BINOP_BAND:
			return 12;
		// This is the bastard of the bunch -- we'll put it higher than the
		// other bitdiddling operators
		case ExprBinary.BINOP_SELECT:
			return 15;

		case ExprBinary.BINOP_EQ:
		case ExprBinary.BINOP_NEQ:
			return 20;
		case ExprBinary.BINOP_LT:
		case ExprBinary.BINOP_LE:
		case ExprBinary.BINOP_GT:
		case ExprBinary.BINOP_GE:
			return 21;
		case ExprBinary.BINOP_LSHIFT:
		case ExprBinary.BINOP_RSHIFT:
			return 22;

		case ExprBinary.BINOP_ADD:
		case ExprBinary.BINOP_SUB:
			return 30;
		case ExprBinary.BINOP_MUL:
		case ExprBinary.BINOP_DIV:
		case ExprBinary.BINOP_MOD:
			return 31;

		default:
			throw new IllegalArgumentException("unknown operator " + op);
		}
	}

	@Override
	public ConstData replaceConst(int index) {
		List<SketchObject> toAdd = new ArrayList<SketchObject>();
		if (left instanceof ExprConstant) {
			int value = ((ExprConstant) left).getVal();
			Type t = ((ExprConstant) left).getType();
			left = new ExprFuncCall("Const" + index, new ArrayList<Expression>());
			toAdd.add(this);
			return new ConstData(t, toAdd, index + 1, value, null, this.lineNumber);
		}
		if (right instanceof ExprConstant) {
			int value = ((ExprConstant) right).getVal();
			Type t = ((ExprConstant) right).getType();
			right = new ExprFuncCall("Const" + index, new ArrayList<Expression>());
			return new ConstData(t, toAdd, index + 1, value, null, this.lineNumber);
		}
		toAdd.add(left);
		toAdd.add(right);
		return new ConstData(null, toAdd, index, 0, null, this.lineNumber);
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
	public boolean equals(Expression other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ExternalFunction> extractExternalFuncs(List<ExternalFunction> externalFuncNames) {
		externalFuncNames = left.extractExternalFuncs(externalFuncNames);
		externalFuncNames = right.extractExternalFuncs(externalFuncNames);
		return externalFuncNames;
	}

	@Override
	public void checkAtom() {
		if (this.op != 3) {
			this.setAtom(false);
		} else if (this.left.isAtom() || this.right.isAtom()) {
			this.setAtom(true);
		} else {
			this.setAtom(false);
		}

	}
	/**
	 * Currently supports boolean, add, and subtract expressions
	 **/
	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {
		
		boolean isNumericCompare = this.op == BINOP_EQ 
				|| this.op == BINOP_NEQ 
				|| this.op == BINOP_LT 
				|| this.op == BINOP_LE 
				|| this.op == BINOP_GT
				|| this.op == BINOP_GE;
		
		//inserts scalar and vector coeff on left side
		if (this.isBoolean() && isNumericCompare) {
			
			VectorCoefficient valCoeff = new VectorCoefficient(
					coeffs.size(), new TypePrimitive(TypePrimitive.TYPE_INT32), this.lineNumber);
			coeffs.add(valCoeff);
			
			this.left = valCoeff.addToExpr(
					this.left, 
					coeffs, 
					new TypePrimitive(TypePrimitive.TYPE_INT32));
			
			this.left.setCtx(this.getCtx());
			this.left.setType(new TypePrimitive(4));
			return;
			
		//If this is part of a larger boolean expression, but itself not a 
		//numeric comparison, don't insert coeffecients into this expr but insert
		//them lhs and rhs individually if applicable
		} else if (this.isBoolean()){
				
				this.left.setBoolean(true);
				this.right.setBoolean(true);
				left.setCtx(this.getCtx());
				right.setCtx(this.getCtx());
				left.insertCoeffs(coeffs);
				right.insertCoeffs(coeffs);
				return;
		}
		
		if (this.op == BINOP_ADD || this.op == BINOP_SUB) {
			
			left.checkAtom();
			right.checkAtom();
			
			if (right.isAtom()) {
				ScalarCoefficient changeCoeff = new ScalarCoefficient(
						coeffs.size(), (TypePrimitive) this.getType(), this.lineNumber, false);
				coeffs.add(changeCoeff);
				
				this.right = changeCoeff.modifyExpr(this.right);
			} else {
				
				right.setCtx(this.getCtx());
				right.setType(this.getType());
				right.insertCoeffs(coeffs);
			}
			
			if (left.isAtom()) {
				
				ScalarCoefficient changeCoeff = new ScalarCoefficient(
						coeffs.size(), (TypePrimitive) this.getType(), this.lineNumber, false);
				coeffs.add(changeCoeff);
				
				this.left = changeCoeff.modifyExpr(this.left);
				
			} else {
				
				this.left.setCtx(this.getCtx());
				this.left.setType(this.getType());
				this.left.insertCoeffs(coeffs);
			}
			
			if (this.getType() instanceof TypeArray) {
				return;
			}
			
			if (this.isLCadded()) {
				
				left.setLCadded(true);
				right.setLCadded(true);
			} else {
				
				//For each live variable of the same type of this, consider adding
				//it to the expression
				for (Map.Entry<String, Type> liveVar 
						: this.getCtx().getAllVars().entrySet()) {
					
					TypePrimitive curVarType = (TypePrimitive) liveVar.getValue();
					
					if (curVarType.getType() == 
							((TypePrimitive) this.getType()).getType()) {
						
						ScalarCoefficient changeCoeff = new ScalarCoefficient(
								coeffs.size(), 
								(TypePrimitive) this.getType(), 
								this.lineNumber, true);
						coeffs.add(changeCoeff);
						
						Expression addLiveVar = 
								changeCoeff.modifyExpr(
									new ExprVar(liveVar.getKey(), this.getType()));
						
						this.right = new ExprBinary(
								right, BINOP_ADD, addLiveVar, this.lineNumber);
					}
				}
				
				VectorCoefficient valCoeff = new VectorCoefficient(
						coeffs.size(), 
						(TypePrimitive) this.getType(), 
						this.lineNumber);
				coeffs.add(valCoeff);
				this.right = valCoeff.addToExpr(
						this.right, coeffs, (TypePrimitive) this.getType());
			}
		}
	}

	@Override
	public Set<String> getVarNames() {
		Set<String> names = new HashSet<String>();
		names.addAll(this.getLeft().getVarNames());
		names.addAll(this.getRight().getVarNames());
		return names;
	}

}
