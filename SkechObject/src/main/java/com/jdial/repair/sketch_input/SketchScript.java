package sketch_input;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import constraintfactory.ConstData;
import repair.CorrectionExample;
import sketchobj.core.Context;
import sketchobj.core.Function;
import sketchobj.core.Parameter;
import sketchobj.core.SketchObject;
import sketchobj.core.TypePrimitive;
import sketchobj.expr.ExprSketchHole;
import sketchobj.stmts.StmtBlock;
import sketchobj.stmts.StmtFunDecl;
import sketchobj.stmts.StmtVarDecl;
import sketchobj.stmts.Statement;

public class SketchScript {
	
	private String code;
	private List<CorrectionExample> examples;
	private Map<String, Function> relevantFuncs;
	private StmtBlock coeffDecls;
	//keeps track of the current index of coeffecients to name them uniquely
	private int coeffNameIdx;
	
	public SketchScript(String code, 
			List<CorrectionExample> examples, 
			Map<String, Function> relevantFuncs) {
		this.code = code;
		this.examples = examples;
		this.relevantFuncs = relevantFuncs;
	}
	
	public Statement insertCoeffs() {
		
		List<Statement> coeffDecls = new ArrayList<Statement>();
		for (Function curFunc : this.relevantFuncs.values()) {
			this.addContext(curFunc);
		}
		
		return new StmtBlock(coeffDecls);
	}
	
	/**
	 * Recursively inserts coeffecients into the given statement
	 * @param stmt - the statement to insert coeffecients into
	 * @return - Declaration functions for the coeffecients
	 */
	private Statement insertCoeffs(Statement stmt) {
		
		List<Statement> list = new ArrayList<Statement>();
		Stack<SketchObject> stmtStack = new Stack<SketchObject>();
		stmtStack.push(stmt);
		
		//iterates through each statement contained in @param s
		while (!stmtStack.empty()) {
			//current expression to try and coeffs to
			SketchObject target = stmtStack.pop();
			ConstData data = null;
			
			//Insert coeffs into the ast node and save info about the coeffs
			data = target.insertCoeffs(this.coeffNameIdx);

			if (data.getType() != null) {
				
				//add in the coeff declarations
				while (this.coeffNameIdx <= data.getPrimaryCoeffIndex()) {
					String coeffChangeName = "coeff" 
											+ this.coeffNameIdx 
											+ "change";
					list.add(new StmtVarDecl(
							new TypePrimitive(1), 
							coeffChangeName, 
							new ExprSketchHole(), 
							0));
					list.add(new StmtFunDecl(addCoeffFun(this.coeffNameIdx, 1, data.getType())));
					coeffIndexToLine.put(this.coeffNameIdx, data.getOriline());
					this.coeffNameIdx++;
				}
				
				if (data.getLiveVarsIndexSet() != null) {
					for (int ii : data.getLiveVarsIndexSet()) {
						list.add(coeffChangeDecl(ii, new TypePrimitive(1)));
						list.add(new StmtFunDecl(addCoeffFun(ii, 0, data.getType())));
						coeffIndexToLine.put(ii, data.getOriline());
					}

				}
				this.coeffNameIdx = data.getIndex();
				if (!data.isIfLC()) {
					list.add(coeffChangeDecl(this.coeffNameIdx - 2, new TypePrimitive(1)));//bit coeff0change = ??;
					list.add(new StmtFunDecl(addCoeffFun(this.coeffNameIdx - 2, 0, data.getType())));//Coeff0()
					list.add(coeffChangeDecl(this.coeffNameIdx - 1, new TypePrimitive(4)));
					list.add(new StmtFunDecl(addLCConstFun(this.coeffNameIdx - 1, data.getType())));
					coeffIndexToLine.put(this.coeffNameIdx - 1, data.getOriline());
					coeffIndexToLine.put(this.coeffNameIdx - 2, data.getOriline());
				}
			}
			this.coeffNameIdx = data.getIndex();
			pushAll(stmtStack, data.getChildren());
		}
		constNumber = this.coeffNameIdx;
		s.ConstructLineToString(line_to_string);

		return new StmtBlock(list);
	}
	
	/**
	 * Recursively adds context to each statement
	 * in the body of the function
	 * @param func
	 */
	private void addContext(Function func) {
		Context prectx = new Context();
		prectx.setLinenumber(func.getBody().getLineNumber());
		List<Parameter> params = func.getParams();
    	for (Parameter param : params) {
    		prectx.addVar(param.getName(), param.getType());
    	}
		func.getBody().buildContext(prectx, 0);
	}
}
