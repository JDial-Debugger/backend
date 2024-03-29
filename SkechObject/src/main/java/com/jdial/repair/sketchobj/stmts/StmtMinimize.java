package sketchobj.stmts;

import java.util.List;
import java.util.Map;
import java.util.Set;

import coefficient.Coefficient;
import constraintfactory.ConstData;
import constraintfactory.ExternalFunction;
import sketchobj.core.Context;
import sketchobj.core.Type;
import sketchobj.expr.Expression;

/**
 * The minimize statement, to tell the backend that some integer value needs to be
 * minimized.
 */
public class StmtMinimize extends Statement {
    private final Expression minimizeExpr;
    public final boolean userGenerated;

    public StmtMinimize(Expression minimizeExpr, final boolean userWritten) {
        this.minimizeExpr = minimizeExpr;
        this.userGenerated = userWritten;
    }

    public Expression getMinimizeExpr() {
        return minimizeExpr;
    }

    public String toString() {
        return "minimize(" + minimizeExpr + ");";
    }

	@Override
	public ConstData replaceConst(int index) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
	public void insertCoeffs(List<Coefficient> coeffs) {}

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
	
	public Set<String> getVarNames(int sideFlag) {
		return this.getMinimizeExpr().getVarNames();
	}
}