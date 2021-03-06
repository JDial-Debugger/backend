package sketchobj.core;

import java.util.*;

import sketch.input.SketchScript;
import sketchobj.expr.ExprConstInt;
import sketchobj.expr.ExprUnary;
import sketchobj.expr.ExprVar;
import sketchobj.stmts.Statement;
import sketchobj.stmts.StmtBlock;
import sketchobj.stmts.StmtExpr;
import sketchobj.stmts.StmtVarDecl;

public class Function extends SketchNode {
	
	//used for special sketch functions (read sketch docs)
	public static enum FcnType {
		// Uninterpreted Function
		Uninterp("uninterp"),
		// Async functions used in promela to model forks.
		Async("async"),
		// Static function, non-generator.
		Static(""),
		// Harness function. Also static.
		Harness("harness"),
		// Indicates an auto-generated wrapper function that initializes
		// globals, also
		// static.
		Wrapper(""),
		// Used for SMT solver, which is now unused. Email developers or look
		// for notes on
		// wiki.
		BuiltinHelper("builtin helper"),
		// Function that is inlined, producing more star values. Also helper
		// functions for
		// PROMELA.
		Generator("generator"),
		// Init("init");
		Model("model");

		/** identifier appearing in C code */
		public final String cCodeName;

		private FcnType(String cCodeName) {
			this.cCodeName = cCodeName;
		}
	}

	private String name; // or null
	private Type returnType;
	private List<Parameter> params;
	private Statement body;
	private FcnType fcnType;

	public Function(
			String name, 
			Type returnType, 
			List<Parameter> params, 
			Statement body, 
			FcnType fcnType) {
		
		this.name = name;
		this.returnType = returnType;
		this.params = params;
		this.body = body;
		this.fcnType = fcnType;
		body.setParent(this);
		this.setParent(null);
	}
	public Function(
			String name, 
			Type returnType, 
			List<Parameter> params, 
			Statement body) {
		this(name, returnType,params,body,FcnType.Static);
	}

	public Function(FcnHeader head, Statement body) {
		this.name = head.name;
		this.returnType = head.returnType;
		this.params = head.params;
		this.body = body;
		this.fcnType = FcnType.Static;
		body.setParent(this);
		this.setParent(null);
	}
	
	public Function(FcnHeader head, Statement body, FcnType ft) {
		this.name = head.name;
		this.returnType = head.returnType;
		this.params = head.params;
		this.body = body;
		body.setParent(this);
		this.setParent(null);
		this.fcnType = ft;
	}
	
	public Statement getBody(){
		return this.body;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Type getReturnType(){
		return this.returnType;
	}
	
	public List<Parameter> getParams(){
		return this.params;
	}

	public String printParams() {
		String s = "";
		boolean notf = false;
		if (params == null || params.isEmpty()) {
			return "";
		}
		for (Parameter p : params) {
			if (notf) {
				s += ", ";
			}
			if (p.isParameterOutput()) {
				s += "ref ";
			}
			if(p.getType()==null) continue;
			s += p.getType() + " " + p.getName();
			notf = true;
		}
		return s;
	}

	public String toString() {
		String tmp1 = fcnType.cCodeName + " " + returnType.toString()+ " " + name + "(" + printParams() + ")";
		String tmp2 = "";
		if(name.contains("Coeff")||name.contains("Constraint"))
		{
			tmp2 = "{\n"+body.toString()+"}\n";
		}
		else if(global.Global.rec_mod)
		{
			tmp2 = "{\n"+"funcCount++;\n"+body.toString()+"funcCount--;\n"+ "}\n";
		}
		else if(!global.Global.rec_mod)
		{
			tmp2 = "{\n"+body.toString()+ "}\n";
		}
		
		
		return tmp1 + tmp2;
		//added 11/26
	}

	/**
	 * Inserts statements to record the state of execution of the function
	 * @param funcName
	 * @param funcType
	 * @param correctionLine
	 * @param correctionVars
	 */
	public void insertRecordStmt(
			String funcName,
			Type funcType,
			int correctionLine,
			Set<String> correctionVars) {
		
		StmtBlock stateInits = new StmtBlock();
		//int __jdial_state_idx = -1
		stateInits.addStmt(new StmtVarDecl(
				Arrays.asList(new TypePrimitive(TypePrimitive.TYPE_INT32)),
				Arrays.asList(SketchScript.STATE_IDX),
				Arrays.asList(new ExprConstInt(-1))));
		//int __jdial_line_hit = 0
		stateInits.addStmt(new StmtVarDecl(
				Arrays.asList(new TypePrimitive(TypePrimitive.TYPE_INT32)),
				Arrays.asList(SketchScript.LINE_HIT),
				Arrays.asList(new ExprConstInt(0))));
		//++__jdial_invoke_count
		stateInits.addStmt(new StmtExpr(new ExprUnary(
				ExprUnary.UNOP_PREINC, 
				new ExprVar(SketchScript.FUNC_INVOKE_COUNT))));
		//recursively record the body
		stateInits.addStmt(this.getBody().insertRecordStmt(
				funcName, funcType, correctionLine, correctionVars));
		
		this.body = stateInits;
	}
}
