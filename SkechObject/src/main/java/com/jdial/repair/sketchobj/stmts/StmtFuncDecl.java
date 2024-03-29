package sketchobj.stmts;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import coefficient.Coefficient;
import constraintfactory.ConstData;
import constraintfactory.ExternalFunction;
import sketchobj.core.Context;
import sketchobj.core.Function;
import sketchobj.core.Type;

/**
 * This is used to define a function inside the scope of a function.
 */
public class StmtFuncDecl extends Statement {

    Function decl;

    public StmtFuncDecl( Function decl) {
        this.decl = decl;
    }

    public Function getDecl() {
        return decl;
    }

    public String toString() {
        return decl.toString();
    }

	@Override
	public ConstData replaceConst(int index) {
		return null;
	}

	@Override
	public Context buildContext(Context ctx, int sp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Type> addRecordStmt(StmtBlock parent, int index, Map<String, Type> m) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean isBasic() {
		return false;
	}

	@Override
	public ConstData replaceConst_Exclude_This(int index, List<Integer> repair_range) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExternalFunction> extractExternalFuncs(List<ExternalFunction> externalFuncNames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, String> ConstructLineToString(Map<Integer, String> line_to_string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//TODO: unimplemented
	@Override
	public Set<String> getVarNames(int sideFlag) {
		return new HashSet<String>();
	}

	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {}
}