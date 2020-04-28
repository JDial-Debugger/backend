package sketchobj.stmts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import constraintfactory.ConstData;
import constraintfactory.ExternalFunction;
import sketchobj.core.Context;
import sketchobj.core.SketchNode;
import sketchobj.core.Type;
import sketchobj.core.TypeArray;
import sketchobj.core.TypeVoid;
import sketchobj.expr.ExprArrayRange;
import sketchobj.expr.ExprBinary;
import sketchobj.expr.ExprConstInt;
import sketchobj.expr.ExprSketchHole;
import sketchobj.expr.ExprString;
import sketchobj.expr.ExprUnary;
import sketchobj.expr.ExprVar;
import sketch_input.Coefficient;
import sketch_input.SketchScript;

public abstract class Statement extends SketchNode {

	private int lineNumber;
	private boolean sorceCode;
	private Context postctx;
	private Context prectx;
	

	public abstract boolean isBasic();
	

	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public boolean isSorce() {
		return this.sorceCode;
	}

	public abstract ConstData replaceConst(int index);
	
	@Override
	public abstract void insertCoeffs(List<Coefficient> coeffs);

	public Context getPostctx() {
		return postctx;
	}

	public void setPostctx(Context ctx) {
		this.postctx = ctx;
	}


	/**
	 * Inserts statements into the AST to record the state of the program 
	 * starting at the given trace point index. It records the state of the
	 * program by inserting array updates for each live variable at all trace
	 * points and returns back all live variables it inserts recording arrays for
	 * @param invokeIdx - Which function invokation to record the state of
	 * @param funcName - The name of the function that contains this statement
	 * @param funcType - The return type of the function that contains this statement
	 * @param correctionLine - The line number of the correction
	 * @param correctionVars - The names of the variables that must be corrected
	 * @return - A new statement containing this statement and all statements to
	 * record the state of the program at the current statement
	 */
	public Statement insertRecordStmt(
			int invokeIdx, 
			String funcName,
			Type funcType,
			int correctionLine,
			Set<String> correctionVars) {
		
		StmtBlock result = new StmtBlock();
		Map<String, Type> allVars = this.getPrectx().getAllVars();
		
		//increment trace point counter
		result.addStmt(new StmtExpr(new ExprUnary(
				5, new ExprVar(SketchScript.STATE_IDX), 0), 0));
		
		//Of the form: lineArray[count] = 3
		result.addStmt(
			new StmtAssign(
				new ExprArrayRange(
					new ExprVar(SketchScript.LINE_ARRAY),
					new ExprArrayRange.RangeLen(
						new ExprVar(SketchScript.STATE_IDX),
						null), 
					0),
				new ExprConstInt(this.getLineNumber()), 
			0));
		
		for (String liveVar : allVars.keySet()) {
			if (!(allVars.get(liveVar) instanceof TypeArray)) {
				
				//Of the form: funcNameVarNameArray[count] = varName
				String arrayName = SketchScript.VAR_PREFIX + 
						funcName + "_" + liveVar + SketchScript.VAR_STATE_SUFFIX;
				result.addStmt(new StmtAssign(
									new ExprArrayRange(
										new ExprVar(arrayName),
										Arrays.asList(
											new ExprArrayRange.RangeLen(
												new ExprVar(SketchScript.FUNC_INVOKE_COUNT), 
												null), 
											new ExprArrayRange.RangeLen(
												new ExprVar(SketchScript.STATE_IDX), 
												null) 
										),
										0), 
									new ExprVar(liveVar), 
									0));
			}
		}
		
		if (this.getLineNumber() == correctionLine) {
			
			//Of the form: (linehit)++;
			result.addStmt(new StmtExpr(new ExprUnary(
					5, new ExprVar(SketchScript.LINE_HIT), 0), 0));
			
			List<Statement> finalStates = new ArrayList<Statement>();
			
			for (String finalVar : correctionVars) {
				
				if (!(allVars.get(finalVar) instanceof TypeArray)) {
					//Of the form finalVarName = varName
					finalStates.add(new StmtAssign(
									new ExprVar(finalVar + SketchScript.VAR_FINAL_SUFFIX), 
									new ExprVar(finalVar), 
									0));
				}
			}
			//Of the form finalcount = count;
			finalStates.add(new StmtAssign(
							new ExprVar(SketchScript.STATE_IDX + 
									SketchScript.VAR_FINAL_SUFFIX), 
							new ExprVar(SketchScript.STATE_IDX), 
							0));
			
			if (funcType instanceof TypeVoid) {
				//of the form: return;
				finalStates.add(new StmtReturn(0));
			} else {
				//Of the form: return 0;
				finalStates.add(new StmtReturn(new ExprConstInt(0), 0));
				
			}
			Statement cons = new StmtBlock(finalStates);
			//Of the form: if (linehit == (??)) { <finalStates> }
			Statement ifLineHit = new StmtIfThen(
					new ExprBinary(
						new ExprVar(SketchScript.LINE_HIT), 
						"==", 
						new ExprSketchHole(), 
					0),
					cons, 
					null, 
					0);
			result.addStmt(ifLineHit);
			
		}
		result.addStmt(this);
		return result;
		
	}
	public abstract Map<String, Type> addRecordStmt(
			StmtBlock parent, 
			int index, 
			Map<String, Type> m);

	public Context getPrectx() {
		return prectx;
	}

	public void setPrectx(Context prectx) {
		this.prectx = prectx;
	}

	@Override
	public ConstData replaceConst(int index, List<Integer> repair_range) {
		if (repair_range.contains(this.lineNumber)){
			return this.replaceConst(index);
			}
		else
			return this.replaceConst_Exclude_This(index, repair_range);
	}

	public abstract ConstData replaceConst_Exclude_This(
			int index, 
			List<Integer> repair_range);

	public abstract List<ExternalFunction> extractExternalFuncs(
			List<ExternalFunction> externalFuncNames);

	public abstract Map<Integer,String> ConstructLineToString(
			Map<Integer, String> line_to_string);
	
	//TODO get rid of this
	public abstract Statement clone();

	/**
	 * Gets variable names from the specified side in this statement
	 * if the statement does not contain any, returns an
	 * empty set. If the statement cannot be divided into two sides,
	 * returns all variable names
	 * @param sideFlag - set 1 for right side var names, -1 for
	 * left side var names, 0 for both
	 * @return - all variable names contained in the statement
	 */
	public abstract Set<String> getVarNames(int sideFlag);
	
	public String toString_Context(){
		return this.toString()+": "+this.postctx.toString();
	}

	public abstract Context buildContext(Context postctx2, int scopePosition);
}
