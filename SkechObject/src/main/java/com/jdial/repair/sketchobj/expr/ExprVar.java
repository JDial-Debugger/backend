package sketchobj.expr;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import constraintfactory.ConstData;
import constraintfactory.ExternalFunction;
import sketch_input.Coefficient;
import sketchobj.core.Type;

public class ExprVar extends Expression
{
    private String name;

	private int line;
	private Type t;

    /** Create a new ExprVar for a particular named variable. */
    public ExprVar( String name)
    {
        this.name = name;
    }
    public ExprVar(String name, Type t){
    	this.name = name;
    	this.t = t;
    }
    
	@Override
	public ExprVar clone() {
		return new ExprVar(this.name, this.t);
	}

    /** Return the name of the variable referenced. */
    public String getName() { return name; }

    public void setName(String newName) { name = newName; }
//    /** Accept a front-end visitor. */
//    public Object accept(FEVisitor v)
//    {
//        return v.visitExprVar(this);
//    }

    /**
     * Determine if this expression can be assigned to.  Variables can
     * generally be assigned to, particularly if they are local
     * variables.  Determining whether a variable is a (constant)
     * stream parameter is beyond the intended use of this function.
     *
     * @return always true
     */
    public boolean isLValue() {
        return true;
    }

    public String toString() {
        return name;
    }

    public boolean equals(Object o) {
        if (!(o instanceof ExprVar))
            return false;
        return name.equals(((ExprVar)o).name);
    }

	@Override
	public ConstData replaceConst(int index) {
		return new ConstData(index,this.line);
	}

	@Override
	public ConstData replaceConst(int index, String string) {
		return new ConstData(index,string,this.line);
	}

	@Override
	public List<ExternalFunction> extractExternalFuncs(List<ExternalFunction> externalFuncNames) {
		return externalFuncNames;
	}
	
	@Override
	public void checkAtom() {
		this.setAtom(true);
	}
	
	@Override
	public Set<String> getVarNames() {
		Set<String> names = new HashSet<String>();
		names.add(this.getName());
		return names;
	}
	
	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {}
	@Override
	public boolean equals(Expression other) {
		// TODO Auto-generated method stub
		return false;
	}
}