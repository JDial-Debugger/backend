package sketchobj.stmts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import coefficient.Coefficient;
import coefficient.ScalarCoefficient;
import coefficient.VectorCoefficient;
import constraintfactory.ConstData;
import constraintfactory.ConstraintFactory;
import constraintfactory.ExternalFunction;
import sketchobj.core.Context;
import sketchobj.core.SketchObject;
import sketchobj.core.Type;
import sketchobj.core.TypePrimitive;
import sketchobj.expr.ExprConstant;
import sketchobj.expr.ExprFuncCall;
import sketchobj.expr.ExprVar;
import sketchobj.expr.Expression;

public class StmtVarDecl extends Statement {
	
	private List<Type> types;
	private List<String> names;
	private List<Expression> inits;

	/**
	 * Create a new variable declaration with corresponding lists of types,
	 * names, and initialization values. The three lists passed in are
	 * duplicated, and may be mutated after calling this constructor without
	 * changing the value of this object. The types and names must all be
	 * non-null; if a particular variable is uninitialized, the corresponding
	 * initializer value may be null.
	 *
	 * @param context
	 *            Context indicating what line and file this statement is
	 *            created at
	 * @param types
	 *            List of <code>Type</code> of the variables declared here
	 * @param names
	 *            List of <code>String</code> of the names of the variables
	 *            declared here
	 * @param inits
	 *            List of <code>Expression</code> (or <code>null</code>)
	 *            containing initializers of the variables declared here
	 * @param i
	 */
	public StmtVarDecl(
			List<Type> types, 
			List<String> names, 
			List<Expression> inits, 
			int lineNumber) {
		// TODO: check for validity, including types of object
		// in the lists and that all three are the same length.
		this.types = new ArrayList<Type>(types);
		this.names = new ArrayList<String>(names);
		this.inits = new ArrayList<Expression>(inits);
		for (Expression e : inits) {
			if (e != null)
				e.setParent(this);
		}
		this.setLineNumber(lineNumber);
	}
	
	public StmtVarDecl(
			List<Type> types, 
			List<String> names, 
			List<Expression> inits) {
		this(types, names, inits, 0);
	}
	
	public StmtVarDecl(
			List<Type> types, 
			List<String> names) {
		this(types, names, null, 0);
	}


	/**
	 * Create a new variable declaration with exactly one variable in it. If the
	 * variable is uninitialized, the initializer may be <code>null</code>.
	 *
	 * @param context
	 *            Context indicating what line and file this statement is
	 *            created at
	 * @param type
	 *            Type of the variable
	 * @param name
	 *            Name of the variable
	 * @param init
	 *            Expression initializing the variable, or <code>null</code> if
	 *            the variable is uninitialized
	 */
	public StmtVarDecl(Type type, String name, Expression init, int i) {
		this(Collections.singletonList(type), 
				Collections.singletonList(name), 
				Collections.singletonList(init), i);
	}
	
	public StmtVarDecl(Type type, String name, Expression init) {
		this(Collections.singletonList(type), 
				Collections.singletonList(name), 
				Collections.singletonList(init), 0);
	}
	
	public StmtVarDecl(Type type, String name) {
		this(Collections.singletonList(type), 
				Collections.singletonList(name), null, 0);
	}

	/*
	 * public StmtVarDecl(Vector<VarDeclEntry> next) { this.types = new
	 * Vector<Type>(next.size()); this.names = new Vector<String>(next.size());
	 * this.inits = new Vector<Expression>(next.size());
	 * 
	 * for (VarDeclEntry e : next) { this.types.add(e.getType());
	 * this.names.add(e.getName()); this.inits.add(e.getInit()); } }
	 */
	/**
	 * Get the type of the nth variable declared by this.
	 *
	 * @param n
	 *            Number of variable to retrieve (zero-indexed).
	 * @return Type of the nth variable.
	 */
	public Type getType(int n) {
		return (Type) types.get(n);
	}

	/**
	 * Set the nth type of this declaration to 't'.
	 *
	 * @param n
	 *            Index of variable whose type will change
	 * @param t
	 *            The type to change it to.
	 * @return
	 */
	public void setType(int n, Type t) {
		types.set(n, t);
	}

	/**
	 * Get a list of the types of all of the variables declared by this.
	 *
	 * @return list of <code>Type</code> of the variables in this
	 */
	public List<Type> getTypes() {
		return types;
	}

	/**
	 * Get the name of the nth variable declared by this.
	 *
	 * @param n
	 *            Number of variable to retrieve (zero-indexed).
	 * @return Name of the nth variable.
	 */
	public String getName(int n) {
		return (String) names.get(n);
	}
	
	// added
	public void SetName(String name) {
		this.names = new ArrayList<String>();
		names.add(name);
	}
	
	/**
	 * Get an immutable list of the names of all of the variables declared by
	 * this.
	 *
	 * @return Unmodifiable list of <code>String</code> of the names of the
	 *         variables in this
	 */
	public List<String> getNames() {
		return Collections.unmodifiableList(names);
	}

	/**
	 * Get the initializer of the nth variable declared by this.
	 *
	 * @param n
	 *            Number of variable to retrieve (zero-indexed).
	 * @return Expression initializing the nth variable, or <code>null</code> if
	 *         the variable is uninitialized.
	 */
	public Expression getInit(int n) {
		return (Expression) inits.get(n);
	}

	/**
	 * Set the initializer of the nth variable.
	 *
	 * @param n
	 *            Which initializer to set
	 * @param init
	 *            What to set it to
	 */
	public void setInit(int n, Expression init) {
		inits.set(n, init);
	}

	/**
	 * Get an immutable list of the initializers of all of the variables
	 * declared by this. Members of the list may be <code>null</code> if a
	 * particular variable is uninitialized.
	 *
	 * @return Unmodifiable list of <code>Expression</code> (or
	 *         <code>null</code>) of the initializers of the variables in this
	 */
	public List<Expression> getInits() {
		return Collections.unmodifiableList(inits);
	}

	/**
	 * Get the number of variables declared by this. This value should always be
	 * at least 1.
	 *
	 * @return Number of variables declared
	 */
	public int getNumVars() {
		// CLAIM: the three lists have the same length.
		return types.size();
	}

	public boolean equals(Object other) {
		if (!(other instanceof StmtVarDecl))
			return false;
		StmtVarDecl svd = (StmtVarDecl) other;
		if (svd.types.size() != types.size())
			return false;
		for (int i = 0; i < types.size(); i++) {
			if (!(types.get(i).equals(svd.types.get(i))))
				return false;
			if (!(names.get(i).equals(svd.names.get(i))))
				return false;
			if (inits.get(i) == null && svd.inits.get(i) != null)
				return false;
			if (inits.get(i) != null && !(inits.get(i).equals(svd.inits.get(i))))
				return false;
		}
		return true;
	}

	public int hashCode() {
		// just use the first type and name.
		return types.get(0).hashCode() ^ names.get(0).hashCode();
	}

	public String toString() {
		
		StringBuffer result = new StringBuffer();
		
		for (int i = 0; i < types.size(); i++) {
			
			if (i != 0) {
				result.append("; ");
			}
			
			result.append(types.get(i) + " " + names.get(i));
			
			if (inits.get(i) != null) {
				result.append(" = " + inits.get(i).toString());
			}
		}
		
		if (types.size() == 1) {
			result.append(";");
		}
		
		return result.toString();
	}

	public static abstract class VarDeclEntry {
		public abstract String getName();

		public abstract Type getType();

		public abstract Expression getInit();

		public ExprVar getVarRefToName() {
			return new ExprVar(getName());
		}

		public VarDeclEntry nextWithType(Type t) {
			return new VarDeclEntryWithNames(getName(), t, getInit());
		}

		public VarDeclEntry nextWithInit(Expression init) {
			return new VarDeclEntryWithNames(getName(), getType(), init);
		}
	}

	public class VarDeclEntryInst extends VarDeclEntry {
		protected final int idx;

		public VarDeclEntryInst(int idx) {
			this.idx = idx;
			if (idx >= getNumVars()) {
				throw new NoSuchElementException();
			}
		}

		public String getName() {
			return StmtVarDecl.this.getName(idx);
		}

		public Type getType() {
			return StmtVarDecl.this.getType(idx);
		}

		public Expression getInit() {
			return StmtVarDecl.this.getInit(idx);
		}
	}

	public static class VarDeclEntryWithNames extends VarDeclEntry {
		public final String name;
		public final Type type;
		public final Expression init;

		public VarDeclEntryWithNames(String name, Type type, Expression init) {
			this.name = name;
			this.type = type;
			this.init = init;
		}

		public String getName() {
			return this.name;
		}

		public Type getType() {
			return this.type;
		}

		public Expression getInit() {
			return this.init;
		}
	}

	public class VarDeclEntryIterator implements Iterator<VarDeclEntry> {
		int idx = 0;

		public boolean hasNext() {
			return idx < getNumVars();
		}

		public VarDeclEntry next() {
			final VarDeclEntry vde = new VarDeclEntryInst(idx);
			idx += 1;
			return vde;
		}

		public void remove() {
			// assertFalse();
		}
	}

	public Iterator<VarDeclEntry> iterator() {
		return new VarDeclEntryIterator();
	}

	@Override
	public ConstData replaceConst(int index) {
		List<SketchObject> toAdd = new ArrayList<SketchObject>();
		if (this.inits.size() != 0) {
			for (int i = 0; i < inits.size(); i++) {
				if (inits.get(i) instanceof ExprConstant) {
					int value = ((ExprConstant) inits.get(i)).getVal();
					Type t = ((ExprConstant) inits.get(i)).getType();
					inits.set(i, new ExprFuncCall("Const" + index, new ArrayList<Expression>(), null));

					return new ConstData(t, toAdd, index + 1, value, names.get(i), this.getLineNumber());
				} else {
					toAdd.add(inits.get(i));
				}
			}
		}
		return new ConstData(null, toAdd, index, 0, null, this.getLineNumber());
	}

	@Override
	public ConstData replaceConst_Exclude_This(int index, List<Integer> repair_range) {
		List<SketchObject> toAdd = new ArrayList<SketchObject>();
		return new ConstData(null, toAdd, index, 0, null, this.getLineNumber());
	}

	@Override
	public Context buildContext(Context prectx, int sp) {
		prectx = new Context(prectx);
		prectx.setLinenumber(this.getLineNumber());
		this.setPrectx(prectx);
		Context postctx = new Context(prectx);
		for (int i = 0; i < names.size(); i++) {
			postctx.addVar(names.get(i), types.get(i));
		}
		postctx.setLinenumber(this.getLineNumber());
		this.setPostctx(new Context(postctx));
		return postctx;
	}

	@Override
	public Map<String, Type> addRecordStmt(StmtBlock parent, int index, Map<String, Type> m) {
		parent.stmts = new ArrayList<Statement>(parent.stmts);
		
		/*if (Global.prime_mod) {
			parent.stmts.set(index, new StmtBlock(this,
					ConstraintFactory.recordState(this.getPrectx().getLinenumber(), this.getPrectx().getAllVars())));
		} else {*/
		if (!ConstraintFactory.dupStmt.contains(this))
			parent.stmts.set(index, new StmtBlock(
				ConstraintFactory.recordState(this.getPrectx().getLinenumber(), this.getPrectx().getAllVars()), this));
		//}
		m.putAll(this.getPostctx().getAllVars());
		return m;
	}

	@Override
	public boolean isBasic() {
		return true;
	}

	@Override
	public List<ExternalFunction> extractExternalFuncs(List<ExternalFunction> externalFuncNames) {
		for (int i = 0; i < inits.size(); i++) {
			if (inits.get(i) == null)
				continue;
			externalFuncNames = inits.get(i).extractExternalFuncs(externalFuncNames);
		}
		return externalFuncNames;
	}

	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {
		
		int startingCoeffsSize = coeffs.size();
		
		for (int i = 0; i < this.getInits().size(); i++) {
			
			Expression curInit = this.getInits().get(i);
			curInit.checkAtom();
			curInit.setLCadded(true);
			
			TypePrimitive initType = (TypePrimitive) this.getPostctx().getAllVars()
					.get(this.names.get(i).toString());
			
			if (curInit.isAtom()) {
				
				ScalarCoefficient coeff = new ScalarCoefficient(
						coeffs.size(), initType, curInit.getLineNumber(), false, this.exprBinaryFactory);
				coeffs.add(coeff);
				inits.set(i, coeff.modifyExpr(curInit));
				
			} else {
				
				curInit.setType(initType);
				curInit.setCtx(this.getPrectx());
				curInit.insertCoeffs(coeffs);
			}
			
			if ((initType instanceof TypePrimitive) && 
					((TypePrimitive) initType).getType() == TypePrimitive.TYPE_BIT) {
				inits.get(i).setBoolean(true);
				return;
			}
			//TODO figure out where this came from
			//List<String> vars = new ArrayList<String>(this.getPrectx().getAllVars().keySet());
/*			for (String v : vars) {
				// all 1 dimension array

				if (this.getPrectx().getAllVars().get(v) instanceof TypeArray) {
					if (((TypePrimitive) ((TypeArray) this.getPostctx().getAllVars().get(v)).getBase())
							.getType() != ((TypePrimitive) t).getType())
						continue;

					/*
					 * Expression newTerm = new ExprBinary( new
					 * ExprFunCall("Coeff" + index, new
					 * ArrayList<Expression>()), "*", new ExprArrayRange(v,
					 * new ExprStar(), this.getLineNumber())); inits.set(i,
					 * new ExprBinary(inits.get(i), "+", newTerm));
					 * liveVarsIndexSet.add(index); liveVarsNameSet.add(v);
					 * index++;
					 */
/*					continue;
				} else if (((TypePrimitive) this.getPrectx().getAllVars().get(v)).getType() != ((TypePrimitive) t)
						.getType())
					continue;
				Expression newTerm = new ExprBinary(new ExprFunCall("Coeff" + index, new ArrayList<Expression>()),
						"*", new ExprVar(v, t), this.getLineNumber());
				inits.set(i, new ExprBinary(inits.get(i), "+", newTerm, this.getLineNumber()));
				liveVarsIndexSet.add(index);
				liveVarsNameSet.add(v);
				index++;
			}
			// added
			/*if (ConstraintFactory.prime_mod)
				inits.set(i, new ExprBinary(inits.get(i), "+", new ExprBinary(new ExprFunCall("@2Coeff" + index), "*",
					new ExprFunCall("Coeff" + (index + 1), new ArrayList<Expression>()), this.getLineNumber()), this.getLineNumber()));
			else*/
			
			VectorCoefficient coeff = new VectorCoefficient(
					coeffs.size(), initType, curInit.getLineNumber(), this.exprBinaryFactory);
			coeffs.add(coeff);
			inits.set(i, coeff.addToExpr(curInit, coeffs, initType));
		}
		//add this statement as a parent to all added coeffs
		for (int i = startingCoeffsSize; i < coeffs.size(); ++i) {
			coeffs.get(i).setParentStmt(this);
		}
	}

	@Override
	public Map<Integer, String> ConstructLineToString(Map<Integer, String> line_to_string) {
		int tmpi = this.getLineNumber();
		String tmps = this.toString();
		line_to_string.put(tmpi, tmps);
		return line_to_string;
	}

	@Override
	public String toString_Context() {
		return this.toString() + ": " + this.getPostctx().toString();

	}

	@Override
	public Set<String> getVarNames(int sideFlag) {
		
		Set<String> names = new HashSet<String>();
		if (sideFlag == -1 || sideFlag == 0) {
			names.addAll(this.getNames());
		} 
		if (sideFlag == 1 || sideFlag == 0) {
			for (Expression expr : this.getInits()) {
				names.addAll(expr.getVarNames());
			}
		} 
		return names;
	}
}