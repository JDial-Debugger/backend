package constraintfactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import jsonast.Trace;
import jsonast.Traces;

import org.apache.tools.ant.taskdefs.XSLTProcess;

import global.Global;
import sketchobj.core.*;
import sketchobj.core.Function.FcnType;
import sketchobj.expr.*;
import sketchobj.stmts.*;
import cfg.*;
/**
 * ConstraintFactory is responsible for translating the original
 * source code, the execution trace,  and the requirements of the 
 * manipulation as an input into the sketch synthesizer. 
 * It does this by taking every statement in the AST for the source code, 
 * and adding coefficients to every expression in these statements. 
 * It then uses an array for each local variable to keep track of its state
 * thoughout the program. Then, it adds a statement for computing a distance between
 * the original execution trace and the execution trace with the manipulation and tries
 * to minimize this.
 *
 */
public class ConstraintFactory {
	// TODO: repair arrayInit.replaceConst(), else statement, Expr.field, all

	// map from coeff to linenumber; map from linenumber to string of statement
	public static Map<Integer, Integer> coeffIndex_to_Line = new HashMap<Integer, Integer>();
	public static Map<Integer, String> line_to_string = new HashMap<Integer, String>();

	static int constNumber = 0;
	static Map<String, Set<Integer>> constMap = new HashMap<String, Set<Integer>>();
	static Map<String, String> varList = new HashMap<>();
	static List<String> funcVarList = new ArrayList<String>();
	static Map<String, Type> namesToType = new HashMap<String, Type>();
	static Map<Integer, Integer> constMapLine = new HashMap<Integer, Integer>();
	static List<Integer> noWeightCoeff = new ArrayList<Integer>();
	static Integer numLines = -1;

	//The original execution trace
	static Traces oriTrace;
	//The trace point of the manipulation
	static Trace finalState;

	//The enclosing function of the manipulation
	static FcnHeader fh; 
	//The line number of the manipulation
	static int hitline = 0;
	//The amount of points in the execution trace that occur on the line that
	//the manipulation takes place
	static int hitnumber = 0;
	static int length = 5;
	//length of the original execution trace
	static int originalLength = 5;
	//the arguments passed to the enclosing function of the manipulation at runtime
	static List<Expression> args = new ArrayList<>();
	public static int correctionIndex = 0;

	// configurations
	static boolean is_linearcombination = false;
	static int distance_type = 0;
	static int numberOfChange = 1;

	// specified range
	static boolean sign_limited_range = false;
	static List<Integer> repair_range = null;
	static Integer sign_distance = 0; // 0 : Hamming distance
										// 1 : edit distance
										// 2 : longest subsequence

	static Integer mod = 0; // 0: global minimization
							// 1: 1 line minimization
							// 2: 1 line fix before minimization

	//the manipulated variable
	static String final_var = null;

	// IOmod
	public static List<Traces> ori_trace_list;
	static List<Trace> target_trace_list;
	public static boolean iomod = false;
	// f(1,2,3) f(4,5,6) f(7,8,9) --> {{4,5,6}, {7,8,9}}
	public static List<List<Expression>> extra_args = new ArrayList<>();
	public int extra_index = 0;

	public static List<ExternalFunction> externalFuncs 
										= new ArrayList<ExternalFunction>();

    public static boolean prime_mod;
    //All functions in the source code that are not 
    //the enclosing function of the manipulation
    public List<Function> otherFunctions;
    //Incremented each time a new coeffecient is added. Used to uniquely name the
    //coeffecients
    public static int coeffIndex;
    public static Set<Statement> dupStmt = new HashSet<>();
    public Set<String> primeVars = new HashSet<>();

    /**
     * Initializes a ConstraintFactory to generate the sketch input
     * @param oriTrace - The opt execution trace converted to a AST
     * @param finalState - The trace point at which the manipulation occurs
     * @param fh - The function enclosing the manipulation
     * @param args - the arguments passed to the enclosing function at runtime
     * @param mod - unknown
     * @param prime_mod - unknown
     * @param otherFunctions - All other function in the source code that
     * 							are not the enclosing function
     */
    public ConstraintFactory(Traces oriTrace, 
    						Trace finalState, 
    						FcnHeader fh, 
    						List<Expression> args, 
    						Integer mod,
    						boolean prime_mod, 
    						List<Function> otherFunctions) {
    	
		ConstraintFactory.fh = fh;
		ConstraintFactory.oriTrace = oriTrace;
		ConstraintFactory.finalState = finalState;
		ConstraintFactory.hitline = finalState.getLine();
		this.otherFunctions = otherFunctions;
		ConstraintFactory.coeffIndex = 0;
		ConstraintFactory.prime_mod = prime_mod;
		
		//calculate how many points in the execution trace occur on the line
		//that the manipulation takes place
		hitnumber = 0;
		for (int i = 0; i < oriTrace.getLength(); i++) {
			if (oriTrace.getTraces().get(i).getLine() == ConstraintFactory.hitline) {
				hitnumber++;
			}
		}
		originalLength = oriTrace.getLength();
		length = originalLength * 2;

		constMap = new HashMap<String, Set<Integer>>();
		varList = new HashMap<>();
		funcVarList = new ArrayList<String>();

		this.ori_trace_list = new ArrayList();
		this.target_trace_list = new ArrayList();

		this.args = args;
		ConstraintFactory.mod = mod;

	}

	public ConstraintFactory(Traces oriTrace, Trace finalState, FcnHeader fh, List<Expression> args) {
		this(oriTrace, finalState, fh, args, 0, false, new ArrayList<Function>());
	}

	public ConstraintFactory(Traces oriTrace, Trace finalState, FcnHeader fh) {
		this(oriTrace, finalState, fh, new ArrayList<Expression>());
		// this.args = args;
	}

	public ConstraintFactory(Traces oriTrace, Trace finalState, FcnHeader fh, Expression parameter) {
		this(oriTrace, finalState, fh);
		List<Expression> l = new ArrayList<Expression>();
		l.add(parameter);
		// this.args = l;
	}

	// IOmode
	public void addOriTraces(Traces ori_list){
		this.ori_trace_list.add(ori_list);
	}
	public void addTargetTrace(Trace target_list){
		this.target_trace_list.add(target_list);
	}

	// set allowed ranges
	public void setRange(List<Integer> l) {
		ConstraintFactory.sign_limited_range = true;
		ConstraintFactory.repair_range = l;
	}

	/**
	 * Generates the input script into the sketch synthesizer.
	 * A script consists of three parts:
	 *	 1) coeff decl and guess functions decl
	 *	 2) the interpreted source function with statements recording program
	 *	 states and expressions rewrote
	 *	 3) the constrain function which compute the cost and search for the
	 *	 least cost rewrite
	 * @param source - The root statement of the source code AST
	 * @return - A string that can be used as input into the sketch synthesizer.
	 * 			This is script that satisfies the manipulation (if possible) while
	 * 			attempting to minimize the syntact and semantic distance from the
	 * 			original source code (provided in source param)
	 */
	public String getScript_linearCombination(Statement source) {

		Statement coeffFunDecls = null;

		String resv_funcs = ReservedFuncs();

		buildContext((StmtBlock) source);

		// replace all constants in source code and add coeffchange declaration
		if (!ConstraintFactory.sign_limited_range) {
			coeffFunDecls = ConstraintFactory.replaceLinearCombination(source);
			if (Global.prime_mod) {
				dupPrimes(source);
				for (String v : finalState.getOrdered_locals()) {
					for (int i : Global.primes) {
						Global.dupFinals.add(v + Integer.toString(i));
					}
				}
				buildContext((StmtBlock) source);
			}
		} else {
			//TODO Error handling
		}

		// add record stmts to source code and collect vars info
		Global.curFunc = ConstraintFactory.fh.getName();

		Map<String, Type> vars = ConstraintFactory.addRecordStmt((StmtBlock) source);
		for (String v : finalState.getOrdered_locals()) {
			vars.put(v, TypePrimitive.inttype);
		}
		
		ConstraintFactory.namesToType = vars;
		List<String> varsNames = new ArrayList<String>(vars.keySet());
		for (String str : varsNames) {
			varList.put(str, ConstraintFactory.fh.getName());
		}
		
		for(int i = 0;i<vars.keySet().size();i++)
		{
			funcVarList.add(Global.curFunc);
		}
		List<Type> varsTypes = new ArrayList<Type>();
		for (int i = 0; i < varsNames.size(); i++) {
			varsTypes.add(vars.get(varsNames.get(i)));
		}
		
		// add declare of <linehit> and <count> 
		source = new StmtBlock(new StmtVarDecl(new TypePrimitive(4), "linehit", new ExprConstInt(0), 0), source);
		source = new StmtBlock(new StmtVarDecl(new TypePrimitive(4), "count", new ExprConstInt(-1), 0), source);
		if (Global.prime_mod) {
			for (Map.Entry<String, Boolean> entry : Global.feasibleVars.entrySet()) {
				if (!finalState.getOrdered_locals().contains(entry.getKey()))
					source = new StmtBlock(new StmtVarDecl(new TypePrimitive(4), entry.getKey() + "ini"
						, new ExprConstInt(0), 0), source);
			}
		}

		Function f = new Function(ConstraintFactory.fh, source);

		List<Statement> stmts = new ArrayList<>();

		// 11/28 handle other functions
		Statement coeffFunDecls1 = null;
		StringBuilder st = new StringBuilder();
		//Statement globalVarDecls1 = null;
		for (int i = 0; i < this.otherFunctions.size(); i++) {
			Function cur = otherFunctions.get(i);
			Global.curFunc = cur.getName();
			FcnHeader fh1 = new FcnHeader(cur.getName(), cur.getReturnType(), cur.getParames());

			Statement s1 = cur.getBody();

		//	System.out.println(s1);

			buildContext((StmtBlock) s1);
		//	System.out.println(s1.toString_Context());
			// replace all constants in source code

			if (!ConstraintFactory.sign_limited_range) {
				coeffFunDecls1 = ConstraintFactory.replaceLinearCombination(s1);
				// constFunDecls = ConstraintFactory.replaceConst(s);
			} else {
			}

			// add declare of <linehit> and <count>
			s1 = new StmtBlock(new StmtVarDecl(new TypePrimitive(4), "linehit", new ExprConstInt(0), 0), s1);

			Function f1 = new Function(fh1, s1);


			st.append(f1.toString());
		}

		stmts.add(getGlobalDecl());
		//stmts.add(globalVarDecls);

		// add declare of const functions
		stmts.add(coeffFunDecls);
		if (coeffFunDecls1 != null)
			stmts.add(coeffFunDecls1);
		stmts.add(
				new StmtBlock(varArrayDecl("line", length, new TypePrimitive(4)), varArrayDecls(varsNames, varsTypes,
						f.getName())));

		//if(global.Global.rec_mod)
			//stmts.add(varArrayDecl("stack", length, new TypePrimitive(4)));

		// add final state
		if (!Global.test_mod)
			for (String v : finalState.getOrdered_locals()) {
				// added
				/*if (ConstraintFactory.prime_mod) {
					for (int i : Global.primes) {
						stmts.add(new StmtVarDecl(new TypePrimitive(4), v + Integer.toString(i), new ExprConstInt(0), 0));
						Global.dupFinals.add(v + Integer.toString(i));
					}
				}*/
				stmts.add(new StmtVarDecl(new TypePrimitive(4), v + "final", new ExprConstInt(0), 0));
			}

		// add final count
		stmts.add(new StmtVarDecl(new TypePrimitive(4), "finalcount", new ExprConstInt(0), 0));
		//stmts.add(new StmtVarDecl(new TypePrimitive(4), "count", new ExprConstInt(-1), 0));
		//if(global.Global.rec_mod)
			//stmts.add(new StmtVarDecl(new TypePrimitive(4), "funcCount", new ExprConstInt(-1), 0));

		if (Global.prime_mod) {
			for (Map.Entry<String, Boolean> entry : Global.feasibleVars.entrySet()) {
				String var = entry.getKey();
				if (Global.allvars.get(var))
					continue;
				for (int p : Global.primes)
					stmts.add(new StmtVarDecl(new TypePrimitive(4), var + Integer.toString(p),
							new ExprConstInt(0), 0));
			}
		}

		Statement block = new StmtBlock(stmts);

		String tmp1 = block.toString();
		String tmp2 = f.toString();
		// args of getAugFunctions() need change
		String tmp3 = "";
		if (Global.inc_mod) {
			int[] fixedPrimes = Global.primes.clone();
			for (int cur_p : fixedPrimes) {
				if (cur_p != fixedPrimes[fixedPrimes.length - 1]) {
					tmp3 += pconstraintFunction(cur_p).toString();
				    extra_index = 0;
				}
				else
					tmp3 += finalconstraintFunction(cur_p).toString();
			}
		} else {
			tmp3 = constraintFunction().toString();
		}
		System.err.println("tmp1: ");
		System.err.println(tmp1);
		System.err.println("tmp2: ");
		System.err.println(tmp2);
		System.err.println("tmp3: ");
		System.err.println(tmp3);
		return tmp1 + tmp2 + st + tmp3;
	}


	private Statement getGlobalDecl() {
		StmtBlock result = new StmtBlock();
		List<Integer> appeared = new ArrayList<Integer>();
		for (int line : ConstraintFactory.coeffIndex_to_Line.values()) {
			if (appeared.contains(line))
				continue;
			result.addStmt(new StmtVarDecl(new TypePrimitive(1), "line" + line + "change", new ExprConstInt(0), 0));
			appeared.add(line);
		}
		ConstraintFactory.numLines = appeared.size();
		return result;
	}

	// genearting reserved function such as abs min max
	private String ReservedFuncs() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Replaces a given normal code statement with a series of coefficients for sketch input
	 * Example: int x = 5 -> int x = coeff1 * 5 + coeff2 * coeff3
	 */
	private static Statement replaceLinearCombination(Statement s) {
		List<Statement> list = new ArrayList<Statement>();
		Stack<SketchObject> stmtStack = new Stack<SketchObject>();
		stmtStack.push(s);
		//iterates through each statement contained in @param s
		while (!stmtStack.empty()) {
			//current expression to try and coeffs to
			SketchObject target = stmtStack.pop();
			ConstData data = null;
			//for our current expression/stmt held in target, get each variable/const in target
			if (ConstraintFactory.sign_limited_range) {
				data = target.replaceLinearCombination(coeffIndex, ConstraintFactory.repair_range);
			} else {
				data = target.replaceLinearCombination(coeffIndex);//@2-------added here
			}

			if (data.getType() != null) {
				
				while (coeffIndex <= data.getPrimaryCoeffIndex()) {
					list.add(coeffChangeDecl(coeffIndex, new TypePrimitive(1)));
					list.add(new StmtFunDecl(addCoeffFun(coeffIndex, 1, data.getType())));
					coeffIndex_to_Line.put(coeffIndex, data.getOriline());
					coeffIndex++;
				}
				if (data.getLiveVarsIndexSet() != null) {
					for (int ii : data.getLiveVarsIndexSet()) {
						list.add(coeffChangeDecl(ii, new TypePrimitive(1)));
						list.add(new StmtFunDecl(addCoeffFun(ii, 0, data.getType())));
						coeffIndex_to_Line.put(ii, data.getOriline());
					}

				}
				coeffIndex = data.getIndex();
				if (!data.isIfLC()) {
					ConstraintFactory.noWeightCoeff.add(coeffIndex - 2);
					System.err.println("case2");
					System.err.println(coeffChangeDecl(coeffIndex - 2, new TypePrimitive(1)));
					System.err.println(new StmtFunDecl(addCoeffFun(coeffIndex - 2, 0, data.getType())));
					System.err.println(coeffChangeDecl(coeffIndex - 1, new TypePrimitive(4)));
					System.err.println(new StmtFunDecl(addLCConstFun(coeffIndex - 1, data.getType())));
					list.add(coeffChangeDecl(coeffIndex - 2, new TypePrimitive(1)));//bit coeff0change = ??;
					list.add(new StmtFunDecl(addCoeffFun(coeffIndex - 2, 0, data.getType())));//Coeff0()
					list.add(coeffChangeDecl(coeffIndex - 1, new TypePrimitive(4)));
					list.add(new StmtFunDecl(addLCConstFun(coeffIndex - 1, data.getType())));
					coeffIndex_to_Line.put(coeffIndex - 1, data.getOriline());
					coeffIndex_to_Line.put(coeffIndex - 2, data.getOriline());
				}
			}
			coeffIndex = data.getIndex();
			pushAll(stmtStack, data.getChildren());
		}
		constNumber = coeffIndex;

		return new StmtBlock(list);
	}
	
	/* if(mini == 0){
	m2 = m % 2;
	m3 = m % 3;
	...
	mini = 1;
	}*/
	private static List<Statement> recover(Statement s) {
		// to be initialize
		Set<String> tbi = new HashSet<>();
		List<Statement> sts;
		List<Statement> res = new ArrayList<>();
		/*for (Map.Entry<String, Boolean> entry : Global.allvars.entrySet()) {
			String var = entry.getKey();
			if (!Global.feasibleVars.get(var)) {
				if (!Global.facts.get(s.getLineNumber()).contains(var)) {
					tbi.add(var);
					Global.feasibleVars.put(var, true);
				}
			}
		}*/

		int line = s.getLineNumber();
		for (Map.Entry<String, Set<Integer>> entry : Global.inilocs.entrySet()) {
			if (entry.getValue().contains(line)) {
				//for (Parameter para :fh.getPara()) {
					//if (para.getName().equals(entry.getKey())) {
				if (!finalState.getOrdered_locals().contains(entry.getKey()))
					tbi.add(entry.getKey());
					//	break;
				//	}
				//}
			}
		}
		for (String str : tbi) {
			sts = new ArrayList<>();
			for (int p : Global.primes) {
				ExprBinary rhs = new ExprBinary(5, new ExprVar(str), new ExprConstInt(p), line);
				Statement assign = new StmtAssign(new ExprVar(str + Integer.toString(p)), rhs, line);
				sts.add(assign);
				dupStmt.add(assign);
			}
			Statement update = new StmtAssign(new ExprVar(str + "ini"), new ExprConstInt(1), line);
			sts.add(update);
			dupStmt.add(update);

			Expression cond = new ExprBinary(8, new ExprVar(str + "ini"), new ExprConstInt(0), line);
			Statement cons = new StmtBlock(sts);
			dupStmt.add(cons);
			Statement ifthen = new StmtIfThen(cond, cons, null, line);
			res.add(ifthen);
			dupStmt.add(ifthen);
		}
		return res;
	}

	private static void dupPrimes(Statement s) {
		if (s == null)
			return;
		Global.line = s.getLineNumber();
		if (s instanceof StmtBlock) {
			List<Statement> stmts = ((StmtBlock) s).stmts;
			for (int i = 0; i < stmts.size(); i++) {
				Statement si = stmts.get(i);
				
				Global.line = si.getLineNumber();
				
				List<Statement> rec = recover(si);
				if (rec.size() > 0) {
					((StmtBlock) s).stmts.add(i, new StmtBlock(rec));
					i++;
				}
				if (si instanceof StmtAssign) {
					boolean keepActual = true;
					boolean isAdd = ((StmtAssign) si).getOp() == ExprBinary.BINOP_ADD ||
							((StmtAssign) si).getOp() == ExprBinary.BINOP_SUB;
					Expression l = ((StmtAssign) si).getLHS();
					if (Global.facts.get(si.getLineNumber()) == null || 
						Collections.disjoint(Global.facts.get(si.getLineNumber()), CFG.extractAllVarExpr(l)))
						keepActual = false;
					if (!keepActual) {
						((StmtBlock) s).stmts.remove(i);
						i--;
					} else {
						continue;
					}

					/*for (String var : CFG.extractAllVarExpr(l)) {
						if (!Global.feasibleVars.get(var))
							Global.feasibleVars.put(var, true);
					}*/
					//Context con = si.getPostctx();
					//Map<String, Type> allvars = con.getAllVars();
					//dupVars(allvars);
					//con.setCurrentVars(allvars);
					//if (CFG.extractAllVarExpr(((StmtAssign) si).getLHS()).contains(final_var))
						//Global.finalTracked = true;
					for (int p : Global.primes) {
						StmtAssign newSt = ((StmtAssign) si).clone();
						Expression lhs = newSt.getLHS();
						changeExpr(lhs, p);
						Expression rhs = newSt.getRHS();
						//if (!frozenStmt.contains(si))
							//changeExpr(rhs, p);
						//else
							//Global.dupFinals.add(rhs.toString() + Integer.toString(i));
						rhs = ConvertExpr(rhs, p);
						newSt.setLhs(lhs);
						newSt.setRhs(rhs);
						//newSt.setPrectx(con);
						//newSt.setPostctx(con);
						i++;
						((StmtBlock) s).stmts.add(i, newSt);
						//System.err.println("newSt: " + newSt);
						if (isAdd) {
							i++;
							StmtAssign reset = new StmtAssign(lhs, new ExprBinary(
									ExprBinary.BINOP_MOD, lhs, new ExprConstInt(p), newSt.getLineNumber()),
									newSt.getLineNumber());
							((StmtBlock) s).stmts.add(i, reset);
							ConstraintFactory.dupStmt.add(reset);
							//System.err.println("reset: " + reset);
						}
						if (keepActual) {
							ConstraintFactory.dupStmt.add(newSt);
						} else if (p != Global.primes[0]) {
							ConstraintFactory.dupStmt.add(newSt);
						}
						//if (frozenStmt.contains(si))
							//ConstraintFactory.dupStmt.add(newSt);
					}
					//ConstraintFactory.dupStmt.add(si);
					continue;
				}
				if (si instanceof StmtVarDecl) {
					String declaredVar = ((StmtVarDecl) si).getName(0);
					boolean keepActual = false;
					//if (!Global.only_mod) {
						if (Global.facts.get(si.getLineNumber()) == null ||
								 Global.facts.get(si.getLineNumber()).contains(declaredVar)) {
							keepActual = true;
						}
					/*} else {
						if (Global.altfacts.contains(si.getLineNumber()))
							keepActual = true;
					}*/
					if (!keepActual) {
						((StmtBlock) s).stmts.remove(i);
						i--;
					} else {
						continue;
					}

					//if (!Global.feasibleVars.get(declaredVar))
						//Global.feasibleVars.put(declaredVar, true);

					String finalVar = finalState.getOrdered_locals().get(0);
					//System.err.println("final var is "+ finalVar);
					//System.err.println("var name is " + ((StmtVarDecl) si).getName(0));
					if (((StmtVarDecl) si).getName(0).equals(finalVar)) {
						//Global.finalTracked = true;
						//Context con = si.getPostctx();
						//Map<String, Type> allvars = con.getAllVars();
						//dupVars(allvars);
						//con.setCurrentVars(allvars);
						for (int p : Global.primes) {

							Expression init = ((StmtVarDecl) si).clone().getInit(0);
							//changeExpr(init, p);
							//if (!(init instanceof ExprArrayInit))
							//	init = modExpr(init, p);
							StmtAssign assign = new StmtAssign(new ExprVar(finalVar + Integer.toString(p)),
									ConvertExpr(init, p), si.getLineNumber());
							//assign.buildContext(con, 0);
							i++;
							((StmtBlock) s).stmts.add(i, assign);
							if (keepActual) {
								ConstraintFactory.dupStmt.add(assign);
							} else if (p != Global.primes[0]) {
								ConstraintFactory.dupStmt.add(assign);
							}
						}
						//ConstraintFactory.dupStmt.add(si);
						continue;
					} else {
						//Context con = si.getPostctx();
						//Map<String, Type> allvars = con.getAllVars();
						//dupVars(allvars);
						//con.setCurrentVars(allvars);
						for (int p : Global.primes) {
							StmtVarDecl newSt = ((StmtVarDecl) si).clone();
							newSt.SetName(newSt.getName(0) + Integer.toString(p));
							Expression init = newSt.getInit(0);
							//changeExpr(init, p);
							//if (!(init instanceof ExprArrayInit))
								//init = modExpr(init, p);
							newSt.setInit(0, ConvertExpr(init, p));
							//newSt.setPrectx(con);
							//newSt.setPostctx(con);
							i++;
							((StmtBlock) s).stmts.add(i, newSt);
							if (keepActual) {
								System.err.println("added dup: " + newSt);
								ConstraintFactory.dupStmt.add(newSt);
							} else if (p != Global.primes[0]) {
								System.err.println("added dup: " + newSt);
								ConstraintFactory.dupStmt.add(newSt);
							}
						}
						//ConstraintFactory.dupStmt.add(si);
						continue;
					}
				}
				// make single single stmt into a block
				/*if (si instanceof StmtIfThen) {
					boolean replace = false;
					Statement cons = ((StmtIfThen) si).getCons();
					if (cons != null && !(cons instanceof StmtBlock)) {
						cons = new StmtBlock(new ArrayList<>(Arrays.asList(cons)));
						replace = true;
					}
					Statement alt = ((StmtIfThen) si).getAlt();
					if (alt != null && !(alt instanceof StmtBlock)) {
						alt = new StmtBlock(new ArrayList<>(Arrays.asList(alt)));
						replace = true;
					}
					if (replace) {
						StmtIfThen newIfThen = new StmtIfThen(((StmtIfThen) si).getCond(), cons, alt,
								si.getLineNumber());
						((StmtBlock) s).stmts.remove(i);
						((StmtBlock) s).stmts.add(newIfThen);
					}
				}*/
				dupPrimes(si);
			}
			return;
		}
		if (s instanceof StmtDoWhile) {
			dupPrimes(((StmtDoWhile) s).getBody());
			return;
		}
		if (s instanceof StmtFor) {
			dupPrimes(((StmtFor) s).getBody());
			return;
		}
		if (s instanceof StmtIfThen) {
			Statement cons = ((StmtIfThen) s).getCons();
			//Context consCon = s.getPostctx();
			if (cons != null && !(cons instanceof StmtBlock)) {
				Statement newCons = new StmtBlock(new ArrayList<>(Arrays.asList(cons)));
				((StmtIfThen) s).setCons(newCons);
				//System.err.println("consCon is: " + ((StmtIfThen) s).getCons().getPostctx().getLinenumber()
					//	+ ((StmtIfThen) s).getCons().getPostctx().getAllVars());
				//System.err.println("sCon: " + s.getPostctx().getLinenumber() + s.getPostctx().getAllVars());
			}
			Statement alt = ((StmtIfThen) s).getAlt();
			//Context altCon = s.getPostctx();
			if (alt != null && !(alt instanceof StmtBlock)) {
				Statement newAlt = new StmtBlock(new ArrayList<>(Arrays.asList(alt)));
				//newAlt.setPrectx(altCon);
				//newAlt.setPostctx(altCon);
				((StmtIfThen) s).setAlt(newAlt);
			}

			if (((StmtIfThen) s).getCons() instanceof StmtBlock) {
				System.err.println("cons is a block");
			} else {
				System.err.println("not a block");
			}
			System.err.println(((StmtIfThen) s).getCons().getClass().getName());
			dupPrimes(((StmtIfThen) s).getCons());
			dupPrimes(((StmtIfThen) s).getAlt());
			return;
		}
		if (s instanceof StmtWhile) {
			dupPrimes(((StmtWhile) s).getBody());
			return;
		}
		if (s instanceof StmtReturn) {
			if (!Global.test_mod)
				((StmtReturn) s).setValue(new ExprConstInt(0));
			else {

			}
		}
	}

	private static Expression ConvertExpr(Expression e, int p) {
		if (e instanceof ExprArrayInit) {
			List<Expression> list = ((ExprArrayInit) e).getElements();
			for (int i = 0; i < list.size(); i++) {
				Expression expr = list.get(i);
				if (expr instanceof ExprConstInt) {
					list.remove(i);
					list.add(i, modExpr(expr, p));
				} else {
					list.remove(i);
					list.add(i, ConvertExpr(expr, p));
				}
			}
			return e;
		}
		if (e instanceof ExprArrayRange) {
			ExprArrayRange newExpr = ((ExprArrayRange) e).clone();
			changeExpr(newExpr.getBase(), p);
			return newExpr;
		}
		if (e instanceof ExprBinary) {
			Expression l = ConvertExpr(((ExprBinary) e).getLeft(), p);
			Expression r = ConvertExpr(((ExprBinary) e).getRight(), p);
			return modExpr(new ExprBinary(((ExprBinary) e).getOp(), l, r, e.lineNumber), p);
		}
		if (e instanceof ExprVar) {
			//System.err.println("var is " + ((ExprVar) e).getName());
			//System.err.println("nested var is " + Global.nestedVars);
			String varName = ((ExprVar) e).getName();
			if (!Global.nestedVars.contains(varName)) {
				//if (Global.feasibleVars.get(varName))
				if (!Global.facts.get(Global.line).contains(varName))
					((ExprVar) e).setName(varName + Integer.toString(p));
			} else {
				((ExprVar) e).setName("(" + varName + "%" + Integer.toString(p) + ")");
			}
			return modExpr(e, p);
		}
		if (e instanceof ExprFunCall) {
			return modExpr(e, p);
		}
		if (e instanceof ExprConstInt) {
			return modExpr(e, p);
		}
		return e;
	}

	private static void changeExpr(Expression e, int p) {
		if (e instanceof ExprArrayInit) {
			List<Expression> list = ((ExprArrayInit) e).getElements();
			for (int i = 0; i < list.size(); i++) {
				Expression expr = list.get(i);
				if (expr instanceof ExprConstInt) {
					list.remove(i);
					list.add(i, modExpr(expr, p));
				} else {
					changeExpr(expr, p);
				}
			}
		}
		if (e instanceof ExprArrayRange) {
			changeExpr(((ExprArrayRange) e).getBase(), p);
		}
		if (e instanceof ExprBinary) {
			changeExpr(((ExprBinary) e).getLeft(), p);
			changeExpr(((ExprBinary) e).getRight(), p);
			return;
		}
		if (e instanceof ExprVar) {
			//System.err.println("var is " + ((ExprVar) e).getName());
			//System.err.println("nested var is " + Global.nestedVars);
			String varName = ((ExprVar) e).getName();
			if (!Global.nestedVars.contains(varName)) {
				//if (Global.feasibleVars.get(varName))
				if (!Global.facts.get(Global.line).contains(varName))
					((ExprVar) e).setName(varName + Integer.toString(p));
			} else {
				//((ExprVar) e).setName("(" + varName + "%" + Integer.toString(p) + ")");
			}
		}
	}

	private static ExprBinary modExpr(Expression e, int p) {
		Expression left = e;
		int op = ExprBinary.BINOP_MOD;
		Expression right = new ExprConstInt(p);
		return new ExprBinary(op, left, right, left.lineNumber);
	}

	private static Function addLCConstFun(int i, Type type) {
		Expression condition_2 = new ExprStar();
		StmtReturn return_2 = new StmtReturn(new ExprConstInt(0), 0);
		StmtReturn return_3 = new StmtReturn(new ExprVar("coeff" + i + "change"), 0);

		Statement ifst_2 = new StmtIfThen(condition_2, return_2, null, 0);
		StmtBlock sb = new StmtBlock();
		sb.addStmt(ifst_2);
		sb.addStmt(return_3);
		return new Function("Coeff" + i, type, new ArrayList<Parameter>(), sb, FcnType.Static);
	}

	private static Function addCoeffFun(int index, int value, Type type) {
		Expression condition = new ExprBinary(new ExprVar("coeff" + index + "change"), "==", new ExprConstInt(0), 0);
		StmtReturn return_1 = new StmtReturn(new ExprConstInt(value), 0); 
		Expression condition_2 = new ExprStar();// ??
		StmtReturn return_2 = new StmtReturn(new ExprConstInt(1 - value), 0);
		Statement ifst = new StmtIfThen(condition, return_1, null, 0);
		Statement ifst_2 = new StmtIfThen(condition_2, return_2, null, 0);
		StmtReturn return_3 = new StmtReturn(new ExprConstInt(-1), 0);
		StmtBlock sb = new StmtBlock();
		sb.addStmt(ifst);
		sb.addStmt(ifst_2);
		sb.addStmt(return_3);
		return new Function("Coeff" + index, type, new ArrayList<Parameter>(), sb, FcnType.Static);
	}

	private static Statement coeffChangeDecl(int index, TypePrimitive typePrimitive) {
		return new StmtVarDecl(typePrimitive, "coeff" + index + "change", new ExprStar(), 0);
	}

	/*
	 * private static Statement replaceLinearCombination(Statement s,
	 * List<Integer> repair_range2) { List<Statement> list = new
	 * ArrayList<Statement>(); Stack<SketchObject> stmtStack = new
	 * Stack<SketchObject>(); int index = 0; stmtStack.push(s); while
	 * (!stmtStack.empty()) { SketchObject target = stmtStack.pop(); ConstData
	 * data = null; if (ConstraintFactory.sign_limited_range) { data =
	 * target.replaceLinearCombination(index, ConstraintFactory.repair_range); }
	 * else { data = target.replaceLinearCombination(index); } if
	 * (data.getType() != null) { while (index <= data.getPrimaryCoeffIndex()) {
	 * list.add(coeffChangeDecl(index, new TypePrimitive(1))); list.add(new
	 * StmtFunDecl(addCoeffFun(index, 1, data.getType()))); index++; } if
	 * (data.getLiveVarsIndexSet() != null) { for (int ii :
	 * data.getLiveVarsIndexSet()) { list.add(coeffChangeDecl(ii, new
	 * TypePrimitive(1))); list.add(new StmtFunDecl(addCoeffFun(ii, 0,
	 * data.getType()))); }
	 *
	 * } index = data.getIndex(); list.add(coeffChangeDecl(index - 1, new
	 * TypePrimitive(4))); list.add(new StmtFunDecl(addLCConstFun(index - 1,
	 * data.getType()))); } index = data.getIndex(); pushAll(stmtStack,
	 * data.getChildren()); } constNumber = index; //System.out.println(s);
	 * return new StmtBlock(list); }
	 */

	// ------------ Auxiliary functions

	static public Statement constChangeDecl(int index, Type t) {
		return new StmtVarDecl(t, "const" + index + "change", new ExprStar(), 0);
	}

	static public Statement constChangeDecls(int number, Type t) {
		StmtBlock result = new StmtBlock();
		for (int i = 0; i < number; i++) {
			result.addStmt(new StmtVarDecl(t, "const" + i + "change", new ExprStar(), 0));
		}
		return result;
	}

	// added 11/19
	static private void get_final_var_2dArray() {
		int bound = originalLength;
		String v = ConstraintFactory.final_var;
		List<Integer> arrayInit = new ArrayList<>();
		List<Integer> level = new ArrayList<>();
		for (int i = 0; i < bound; i++) {
			level.add(oriTrace.getTraces().get(i).getRstack().getFrams().size());
			if (oriTrace.getTraces().get(i).getOrdered_locals().contains(v)) {
				if (oriTrace.getTraces().get(i).getLocals().find(v).getType() == 0) {
					arrayInit.add(oriTrace.getTraces().get(i).getLocals().find(v).getValue());
				}
			} else {
				// TODO check if int can be null in Sketch
				arrayInit.add(0);
			}
		}
		System.err.println("final_var is " + v + "----------------------------------------9");
		int minLevel = 100;
		int maxLevel = 0;
		for (int i = 0; i < arrayInit.size(); i++) {
			int curLevel = level.get(i);
			if (curLevel < minLevel)
				minLevel = curLevel;
			if (curLevel > maxLevel)
				maxLevel = curLevel;
		}
		int numEachLevel [] = new int[maxLevel - minLevel + 1];

		for (int i = 0; i < arrayInit.size(); i++) {
			int curLevel = level.get(i);
			numEachLevel[curLevel - minLevel]++;
		}
		int max_num = 0;
		for (int i = 0; i < maxLevel - minLevel + 1; i++) {
			if (numEachLevel[i] > max_num)
				max_num = numEachLevel[i];
			System.err.println("numEachLevel is " + numEachLevel[i]);
		}

		int matrix [][] = new int[maxLevel - minLevel + 1][max_num];
		int lineIndex [] = new int[maxLevel - minLevel + 1];
		for (int i = 0; i < arrayInit.size(); i++) {
			matrix[level.get(i) - minLevel][lineIndex[level.get(i) - minLevel]] =  arrayInit.get(i);
			lineIndex[level.get(i) - minLevel]++;
		}

		for (int i = 0; i < matrix.length; i++) {
		    for (int j = 0; j < matrix[i].length; j++) {
		        System.out.print(matrix[i][j] + " ");
		    }
		    System.out.println();
		}

		for (int i = 0; i < arrayInit.size(); i++) {
			System.err.println("each value is " + arrayInit.get(i));
			System.err.println("each level is " + level.get(i));
		}
	}

	private Statement oneLineConstraint() {
		StmtBlock result = new StmtBlock();
		Map<Integer, Expression> assign = new HashMap<Integer, Expression>();
		List<Integer> appeared = new ArrayList<Integer>();
		for (int line : ConstraintFactory.coeffIndex_to_Line.values()) {
			if (appeared.contains(line))
				continue;
			assign.put(line, null);
			appeared.add(line);
		}

		for (int coeff : ConstraintFactory.coeffIndex_to_Line.keySet()) {
			int line = ConstraintFactory.coeffIndex_to_Line.get(coeff);
			Expression old = assign.get(line);
			if (old == null)
				old = new ExprBinary(new ExprVar("coeff" + coeff + "change"), "!=", new ExprConstInt(0), -1);
			else
				old = new ExprBinary(old, "||",
						new ExprBinary(new ExprVar("coeff" + coeff + "change"), "!=", new ExprConstInt(0), -1), -1);
			assign.remove(line);
			assign.put(line, old);
		}
		Expression sum = new ExprConstInt(0);
		for (int line : appeared) {
			sum = new ExprBinary(sum, "+", new ExprVar("line" + line + "change"), -1);
			result.addStmt(new StmtAssign(new ExprVar("line" + line + "change"), assign.get(line), -1));
		}
		result.addStmt(new StmtAssert(new ExprBinary(sum, "==", new ExprConstInt(1), -1)));
		return result;
	}

	public Function pconstraintFunction(int cur_p) {
		Global.primes = new int[1];
		Global.primes[0] = cur_p;
		List<Statement> stmts = new ArrayList<Statement>();

		int bound = (length < originalLength) ? length : originalLength;


		// TODO int distance = |finalcount-originalLength|;

		Set<String> addedVars = new HashSet<String>();
		for (Map.Entry<String, String> entry : varList.entrySet()) {
			String v = entry.getKey();
			if (ConstraintFactory.namesToType.get(v) instanceof TypeArray)
				continue;
			List<Expression> arrayInit = new ArrayList<>();
			boolean vIsOriginal = false;
			for (int i = 0; i < bound; i++) {
				if (oriTrace.getTraces().size() <= i) {
					arrayInit.add(new ExprConstInt(0));
					continue;
				}
				if (oriTrace.getTraces().get(i).getOrdered_locals().contains(v)) {
					vIsOriginal = true;
					if (oriTrace.getTraces().get(i).getLocals().find(v).getType() == 0)
						arrayInit.add(
								new ExprConstInt((int) oriTrace.getTraces().get(i).getLocals().find(v).getValue()));
				} else {
					// TODO check if int can be null in Sketch
					arrayInit.add(new ExprConstInt(0));
				}
			}
			for (int i = bound; i < originalLength; i++) {
				arrayInit.add(new ExprConstInt(0));
			}
			if (vIsOriginal) {
				stmts.add(new StmtVarDecl(new TypeArray(new TypePrimitive(4), new ExprConstInt(originalLength)),
					"oringianl" + v + "Array", new ExprArrayInit(arrayInit), 0));
				addedVars.add(v);
			}
		}

		for (String v : finalState.getOrdered_locals()) {
			if (finalState.getLocals().find(v) != null)
				stmts.add(new StmtVarDecl(new TypePrimitive(4), "correctFinal_" + v,
						new ExprConstInt(finalState.getLocals().find(v).getValue()), 0));
		}

		// f(args)
		if (Global.prime_mod) {
			stmts.add(new StmtExpr(new ExprFunCall(fh.getName(), args, fh.getName()), 0));

			for (int p : Global.primes) {
				for (String v : finalState.getOrdered_locals()) {
					String var = v + Integer.toString(p);
					stmts.add(new StmtExpr(new ExprVar(var + " = (" + var + " < 0)? " + var + "+" +
					Integer.toString(p) + " : " + var),0));
				}
			}
		}

		// hard constrain
		for (String v : finalState.getOrdered_locals()) {
			if (ConstraintFactory.prime_mod) {
				for (int i : Global.primes) {
					String num = Integer.toString(i);
					stmts.add(new StmtAssert( new ExprBinary(new ExprVar(v + num), "==",
							new ExprVar("correctFinal_" + v + " % " + num), 0)));
				}
				//stmts.add(new StmtAssert(this.genAssertPrime(v)));
			}
			else
				//stmts.add(new StmtAssert(
				//		new ExprBinary(new ExprVar(v + "final"), "==", new ExprVar("correctFinal_" + v), 0)));
				stmts.add(new StmtAssert(
								new ExprBinary(new ExprFunCall(fh.getName(), args, fh.getName()),
										"==", new ExprVar("correctFinal_" + v), 0)));
		}

		// IOmod
		if(this.iomod){
			for(int i = 0; i < this.ori_trace_list.size();i++){
				bound = this.ori_trace_list.get(i).getLength();
				stmts.addAll(pgenSemCompareIO(cur_p, bound, this.ori_trace_list.get(i), this.target_trace_list.get(i)));
			}
		}

		return new Function("Constraint" + Integer.toString(cur_p), new TypeVoid(), new ArrayList<Parameter>(), new StmtBlock(stmts),
				FcnType.Harness);
	}

	public Function finalconstraintFunction(int cur_p) {
		Global.primes = new int[1];
		Global.primes[0] = cur_p;
		List<Statement> stmts = new ArrayList<Statement>();

		int bound = (length < originalLength) ? length : originalLength;


		// TODO int distance = |finalcount-originalLength|;
		stmts.add(new StmtVarDecl(new TypePrimitive(4), "SyntacticDistance", new ExprConstInt(0), 0));
		//stmts.add(new StmtVarDecl(new TypePrimitive(4), "SemanticDistance", new ExprConstInt(0), 0));

		Set<String> addedVars = new HashSet<String>();
		System.err.println("varList is" + varList);
		for (Map.Entry<String, String> entry : varList.entrySet()) {
			String v = entry.getKey();
			if (ConstraintFactory.namesToType.get(v) instanceof TypeArray)
				continue;
			List<Expression> arrayInit = new ArrayList<>();
			boolean vIsOriginal = false;
			for (int i = 0; i < bound; i++) {
				if (oriTrace.getTraces().size() <= i) {
					arrayInit.add(new ExprConstInt(0));
					continue;
				}
				if (oriTrace.getTraces().get(i).getOrdered_locals().contains(v)) {
					vIsOriginal = true;
					if (oriTrace.getTraces().get(i).getLocals().find(v).getType() == 0)
						arrayInit.add(
								new ExprConstInt((int) oriTrace.getTraces().get(i).getLocals().find(v).getValue()));
				} else {
					// TODO check if int can be null in Sketch
					arrayInit.add(new ExprConstInt(0));
				}
			}
			for (int i = bound; i < originalLength; i++) {
				arrayInit.add(new ExprConstInt(0));
			}
			if (vIsOriginal) {
				stmts.add(new StmtVarDecl(new TypeArray(new TypePrimitive(4), new ExprConstInt(originalLength)),
					"oringianl" + v + "Array", new ExprArrayInit(arrayInit), 0));
				addedVars.add(v);
			}
		}

		for (String v : finalState.getOrdered_locals()) {
			if (finalState.getLocals().find(v) != null)
				stmts.add(new StmtVarDecl(new TypePrimitive(4), "correctFinal_" + v,
						new ExprConstInt(finalState.getLocals().find(v).getValue()), 0));
		}

		// f(args)
		if (Global.prime_mod) {
			stmts.add(new StmtExpr(new ExprFunCall(fh.getName(), args, fh.getName()), 0));

			for (int p : Global.primes) {
				for (String v : finalState.getOrdered_locals()) {
					String var = v + Integer.toString(p);
					stmts.add(new StmtExpr(new ExprVar(var + " = (" + var + " < 0)? " + var + "+" +
					Integer.toString(p) + " : " + var),0));
				}
			}
		}

		// hard constrain
		for (String v : finalState.getOrdered_locals()) {
			if (ConstraintFactory.prime_mod) {
				for (int i : Global.primes) {
					String num = Integer.toString(i);
					stmts.add(new StmtAssert( new ExprBinary(new ExprVar(v + num), "==",
							new ExprVar("correctFinal_" + v + " % " + num), 0)));
				}
				//stmts.add(new StmtAssert(this.genAssertPrime(v)));
			}
			else
				//stmts.add(new StmtAssert(
				//		new ExprBinary(new ExprVar(v + "final"), "==", new ExprVar("correctFinal_" + v), 0)));
				stmts.add(new StmtAssert(
								new ExprBinary(new ExprFunCall(fh.getName(), args, fh.getName()),
										"==", new ExprVar("correctFinal_" + v), 0)));
		}


		// IOmod
		if(this.iomod){
			for(int i = 0; i < this.ori_trace_list.size();i++){
				bound = this.ori_trace_list.get(i).getLength();
				stmts.addAll(genSemCompareIO(bound,this.ori_trace_list.get(i), this.target_trace_list.get(i)));
			}
		}


		// syntactic distance
		StmtBlock editsb = new StmtBlock();
		for (int i = 0; i < constNumber; i++) {
			// if (!ConstraintFactory.noWeightCoeff.contains(i))
			editsb.addStmt(new StmtAssign(new ExprVar("SyntacticDistance"), new ExprVar("coeff" + i + "change"), 1, 1));
		}
		stmts.add(editsb);

		//assertion--------added

		if (mod == 1) {
			stmts.add(oneLineConstraint());
		}
		// constrain on # of change
		Expression sumOfConstxchange = new ExprVar("const" + 0 + "change");
		// minimize cost statement
		stmts.add(new StmtMinimize(new ExprVar("SyntacticDistance"), true));

		// stmts.add(new StmtMinimize(new ExprVar("HammingDistance"), true));

		// added 11/19
		//ConstraintFactory.get_final_var_2dArray();

		return new Function("Constraint" + Integer.toString(cur_p), new TypeVoid(), new ArrayList<Parameter>(), new StmtBlock(stmts),
				FcnType.Harness);
	}

	public Function constraintFunction() {
		List<Statement> stmts = new ArrayList<Statement>();

		int bound = (length < originalLength) ? length : originalLength;


		// TODO int distance = |finalcount-originalLength|;
		stmts.add(new StmtVarDecl(new TypePrimitive(4), "SyntacticDistance", new ExprConstInt(0), 0));
		stmts.add(new StmtVarDecl(new TypePrimitive(4), "SemanticDistance", new ExprConstInt(0), 0));

		Set<String> addedVars = new HashSet<String>();
		System.err.println("varList is" + varList);
		for (Map.Entry<String, String> entry : varList.entrySet()) {
			String v = entry.getKey();
			if (ConstraintFactory.namesToType.get(v) instanceof TypeArray)
				continue;
			List<Expression> arrayInit = new ArrayList<>();
			boolean vIsOriginal = false;
			for (int i = 0; i < bound; i++) {
				if (oriTrace.getTraces().size() <= i) {
					arrayInit.add(new ExprConstInt(0));
					continue;
				}
				if (oriTrace.getTraces().get(i).getOrdered_locals().contains(v)) {
					vIsOriginal = true;
					if (oriTrace.getTraces().get(i).getLocals().find(v).getType() == 0)
						arrayInit.add(
								new ExprConstInt((int) oriTrace.getTraces().get(i).getLocals().find(v).getValue()));
				} else {
					// TODO check if int can be null in Sketch
					arrayInit.add(new ExprConstInt(0));
				}
			}
			for (int i = bound; i < originalLength; i++) {
				arrayInit.add(new ExprConstInt(0));
			}
			if (vIsOriginal) {
				stmts.add(new StmtVarDecl(new TypeArray(new TypePrimitive(4), new ExprConstInt(originalLength)),
					"oringianl" + v + "Array", new ExprArrayInit(arrayInit), 0));
				addedVars.add(v);
			}
		}

		for (String v : finalState.getOrdered_locals()) {
			if (finalState.getLocals().find(v) != null)
				stmts.add(new StmtVarDecl(new TypePrimitive(4), "correctFinal_" + v,
						new ExprConstInt(finalState.getLocals().find(v).getValue()), 0));
		}

		// f(args)
		stmts.add(new StmtExpr(new ExprFunCall(fh.getName(), args, fh.getName()), 0));
 		List<Statement> forBody = new ArrayList<Statement>();
		for (Map.Entry<String, String> entry : varList.entrySet()) {
			String v = entry.getKey();
			if (!addedVars.contains(v))
				continue;
			if (constMap.containsKey(v)) {
				List<Expression> subCondition = new ArrayList<Expression>();
				for (Integer indexOfv : constMap.get(v)) {
					subCondition.add(new ExprBinary(new ExprVar("const" + (indexOfv - 1) + "change"), "==",
							new ExprConstInt(0), 0));
				}
				Expression ifCondition;
				ifCondition = subCondition.get(0);
				if (subCondition.size() > 1) {
					for (int i = 1; i < subCondition.size(); i++) {
						ifCondition = new ExprBinary(ifCondition, "&&", subCondition.get(i), 0);
					}
				}
				forBody.add(
						new StmtIfThen(ifCondition,
								new StmtAssign(new ExprVar("SemanticDistance"),
										new ExprBinary(new ExprArrayRange(v + "Array", "i", 0), "!=",
												new ExprArrayRange("oringianl" + v + "Array", "i", 0), 0),
										1, 1),
								null, 0));
 			} else {
				if (!Global.prime_mod || Global.only_mod && !Global.alwaysVars.contains(v)) {
					forBody.add(new StmtAssign(new ExprVar("SemanticDistance"),
							new ExprBinary(new ExprArrayRange(varList.get(v) + v + "Array", "i", 0), "!=",
									new ExprArrayRange("oringianl" + v + "Array", "i", 0), 0),
							1, 1));
				} 
				else
					forBody.add(genSemDisPrime(v, varList.get(v)));
					
			}
 		}
		Statement forinit = new StmtVarDecl(new TypePrimitive(4), "i", new ExprConstInt(0), 0);
		Expression forcon = new ExprBinary(new ExprVar("i"), "<", new ExprConstInt(bound), 0);
		Statement forupdate = new StmtExpr(new ExprUnary(5, new ExprVar("i"), 0), 0);
		stmts.add(new StmtFor(forinit, forcon, forupdate, new StmtBlock(forBody), false, 0));
		if (Global.prime_mod) {
			//stmts.add(new StmtExpr(new ExprFunCall(fh.getName(), args, fh.getName()), 0));
			
			for (int p : Global.primes) {
				for (String v : finalState.getOrdered_locals()) {
					String var = v + Integer.toString(p);
					stmts.add(new StmtExpr(new ExprVar(var + " = (" + var + " < 0)? " + var + "+" +
					Integer.toString(p) + " : " + var),0));
				}
			}
		}

		// hard constrain
		for (String v : finalState.getOrdered_locals()) {
			if (ConstraintFactory.prime_mod) {
				for (int i : Global.primes) {
					String num = Integer.toString(i);
					stmts.add(new StmtAssert( new ExprBinary(new ExprVar(v + num), "==",
							new ExprVar("correctFinal_" + v + " % " + num), 0)));
				}
				//stmts.add(new StmtAssert(this.genAssertPrime(v)));
			}
			else
				if (!Global.test_mod)
					stmts.add(new StmtAssert(
						new ExprBinary(new ExprVar(v + "final"), "==", new ExprVar("correctFinal_" + v), 0)));
				else
					stmts.add(new StmtAssert(
								new ExprBinary(new ExprFunCall(fh.getName(), args, fh.getName()),
										"==", new ExprVar("correctFinal_" + v), 0)));
		}


		// IOmod
		if(this.iomod){
			for(int i = 0; i < this.ori_trace_list.size();i++){
				bound = this.ori_trace_list.get(i).getLength();
				stmts.addAll(genSemCompareIO(bound,this.ori_trace_list.get(i), this.target_trace_list.get(i)));
			}
		}


		// syntactic distance
		StmtBlock editsb = new StmtBlock();
		for (int i = 0; i < constNumber; i++) {
			// if (!ConstraintFactory.noWeightCoeff.contains(i))
			editsb.addStmt(new StmtAssign(new ExprVar("SyntacticDistance"), new ExprVar("coeff" + i + "change"), 1, 1));
		}
		stmts.add(editsb);

		//assertion--------added

		if (mod == 1) {
			stmts.add(oneLineConstraint());
		}
		// constrain on # of change
		Expression sumOfConstxchange = new ExprVar("const" + 0 + "change");
		// minimize cost statement
		stmts.add(new StmtMinimize(new ExprVar("SemanticDistance+2*SyntacticDistance"), true));

		// stmts.add(new StmtMinimize(new ExprVar("HammingDistance"), true));

		// added 11/19
		//ConstraintFactory.get_final_var_2dArray();

		return new Function("Constraint", new TypeVoid(), new ArrayList<Parameter>(), new StmtBlock(stmts),
				FcnType.Harness);
	}

	private List<Statement> pgenSemCompareIO(int cur_p, int bound, Traces ori, Trace tar){
		 List<Statement> stmts = new ArrayList<>();

		for (String v : tar.getOrdered_locals()) {
			if (tar.getLocals().find(v) != null)
				stmts.add(new StmtAssign(new ExprVar("correctFinal_" + v),
						new ExprConstInt(tar.getLocals().find(v).getValue()), 0));
		}

		// f(args)
		if (Global.prime_mod) {
			stmts.add(new StmtExpr(new ExprFunCall(fh.getName(), extra_args.get(extra_index++), fh.getName()), 0));

			for (int p : Global.primes) {
				for (String v : finalState.getOrdered_locals()) {
					String var = v + Integer.toString(p);
					stmts.add(new StmtExpr(new ExprVar(var + " = (" + var + " < 0)? " + var + "+" +
					Integer.toString(p) + " : " + var),0));
				}
			}

		}

		// hard constrain
		for (String v : tar.getOrdered_locals()) {
			if (ConstraintFactory.prime_mod) {
				for (int i : Global.primes) {
					String num = Integer.toString(i);
					stmts.add(new StmtAssert( new ExprBinary(new ExprVar(v + num), "==",
							new ExprVar("correctFinal_" + v + " % " + num), 0)));
				}
				//stmts.add(new StmtAssert(this.genAssertPrime(v)));
			}
			else
				//stmts.add(new StmtAssert(
				//		new ExprBinary(new ExprVar(v + "final"), "==", new ExprVar("correctFinal_" + v), 0)));
				stmts.add(new StmtAssert(
								new ExprBinary(new ExprFunCall(fh.getName(), extra_args.get(extra_index++), fh.getName()),
										"==", new ExprVar("correctFinal_" + v), 0)));
		}

		return stmts;
	}

	private List<Statement> genSemCompareIO(int bound, Traces ori, Trace tar){
		 List<Statement> stmts = new ArrayList<>();

		 stmts.add(new StmtAssign(new ExprVar("count"), new ExprConstInt(-1), 0));
			Set<String> addedVars = new HashSet<String>();
			System.err.println("varList is" + varList);
			for (Map.Entry<String, String> entry : varList.entrySet()) {
				String v = entry.getKey();
				if (ConstraintFactory.namesToType.get(v) instanceof TypeArray)
					continue;
				List<Expression> arrayInit = new ArrayList<>();
				boolean vIsOriginal = false;
				for (int i = 0; i < bound; i++) {
					if (ori.getLength() <= i) {
						arrayInit.add(new ExprConstInt(0));
						continue;
					}
					if (ori.getTraces().get(i).getOrdered_locals().contains(v)) {
						vIsOriginal = true;
						if (ori.getTraces().get(i).getLocals().find(v).getType() == 0)
							arrayInit.add(
									new ExprConstInt((int) ori.getTraces().get(i).getLocals().find(v).getValue()));
					} else {
						// TODO check if int can be null in Sketch
						arrayInit.add(new ExprConstInt(0));
					}
				}
				for (int i = bound; i < ori.getLength(); i++) {
					arrayInit.add(new ExprConstInt(0));
				}
				if (vIsOriginal) {
					stmts.add(new StmtAssign(new ExprVar("oringianl" + v + "Array"), new ExprArrayInit(arrayInit), 0));
					addedVars.add(v);
				}
			}
		for (String v : tar.getOrdered_locals()) {
			if (tar.getLocals().find(v) != null)
				stmts.add(new StmtAssign(new ExprVar("correctFinal_" + v),
						new ExprConstInt(tar.getLocals().find(v).getValue()), 0));
		}

		// f(args)
		stmts.add(new StmtExpr(new ExprFunCall(fh.getName(), extra_args.get(extra_index++), fh.getName()), 0));
 		List<Statement> forBody = new ArrayList<Statement>();
		for (Map.Entry<String, String> entry : varList.entrySet()) {
			String v = entry.getKey();
			if (!addedVars.contains(v))
				continue;
			if (constMap.containsKey(v)) {
				List<Expression> subCondition = new ArrayList<Expression>();
				for (Integer indexOfv : constMap.get(v)) {
					subCondition.add(new ExprBinary(new ExprVar("const" + (indexOfv - 1) + "change"), "==",
							new ExprConstInt(0), 0));
				}
				Expression ifCondition;
				ifCondition = subCondition.get(0);
				if (subCondition.size() > 1) {
					for (int i = 1; i < subCondition.size(); i++) {
						ifCondition = new ExprBinary(ifCondition, "&&", subCondition.get(i), 0);
					}
				}
				forBody.add(
						new StmtIfThen(ifCondition,
								new StmtAssign(new ExprVar("SemanticDistance"),
										new ExprBinary(new ExprArrayRange(v + "Array", "i", 0), "!=",
												new ExprArrayRange("oringianl" + v + "Array", "i", 0), 0),
										1, 1),
								null, 0));
 			} else {
				if (Global.prime_mod) {
					forBody.add(genSemDisPrime(v, varList.get(v)));
				}
				else
					forBody.add(new StmtAssign(new ExprVar("SemanticDistance"),
						new ExprBinary(new ExprArrayRange(varList.get(v) + v + "Array", "i", 0), "!=",
								new ExprArrayRange("oringianl" + v + "Array", "i", 0), 0),
						1, 1));
			}
 		}
		Statement forinit = new StmtVarDecl(new TypePrimitive(4), "i", new ExprConstInt(0), 0);
		Expression forcon = new ExprBinary(new ExprVar("i"), "<", new ExprConstInt(bound), 0);
		Statement forupdate = new StmtExpr(new ExprUnary(5, new ExprVar("i"), 0), 0);
		stmts.add(new StmtFor(forinit, forcon, forupdate, new StmtBlock(forBody), false, 0));
		if (Global.prime_mod) {
			stmts.add(new StmtExpr(new ExprFunCall(fh.getName(), extra_args.get(extra_index++), fh.getName()), 0));

			for (int p : Global.primes) {
				for (String v : finalState.getOrdered_locals()) {
					String var = v + Integer.toString(p);
					stmts.add(new StmtExpr(new ExprVar(var + " = (" + var + " < 0)? " + var + "+" +
					Integer.toString(p) + " : " + var),0));
				}
			}

		}

		// hard constrain
		for (String v : tar.getOrdered_locals()) {
			if (ConstraintFactory.prime_mod) {
				for (int i : Global.primes) {
					String num = Integer.toString(i);
					stmts.add(new StmtAssert( new ExprBinary(new ExprVar(v + num), "==",
							new ExprVar("correctFinal_" + v + " % " + num), 0)));
				}
				//stmts.add(new StmtAssert(this.genAssertPrime(v)));
			}
			else
				//stmts.add(new StmtAssert(
				//		new ExprBinary(new ExprVar(v + "final"), "==", new ExprVar("correctFinal_" + v), 0)));
				stmts.add(new StmtAssert(
								new ExprBinary(new ExprFunCall(fh.getName(), extra_args.get(extra_index++), fh.getName()),
										"==", new ExprVar("correctFinal_" + v), 0)));
		}

		return stmts;
	}

	private Statement genSemDisPrime(String v, String funcName){
		StringBuilder tmp = new StringBuilder();
		for (int i : Global.primes) {
			if (i == Global.primes[0]) {
				tmp.append("((" + funcName + v + Integer.toString(i) + "Array[i]) != (");
				tmp.append("oringianl" + v + "Array[i] % " + Integer.toString(i) + "))");
			} else {
				tmp.append("||\n((" + funcName + v + Integer.toString(i) + "Array[i]) != (");
				tmp.append("oringianl" + v + "Array[i] % " + Integer.toString(i) + "))");
			}
		}
		Statement res = new StmtAssign(new ExprVar("SemanticDistance"),
				new ExprString(tmp.toString()), 1, 1);
		return res;
	}

	private Expression genAssertPrime(String v){
		StringBuilder tmp = new StringBuilder();
		for (int i : Global.primes) {
			String num = Integer.toString(i);
			if (i == Global.primes[0])
				tmp.append("(" + v + num + " == " + "correctFinal_" + v + " % " + num + ")");
			else
				tmp.append("&& \n " + "(" +  v + num + " == " + "correctFinal_" + v + " % " + num + ")");
		}
		return new ExprString(tmp.toString());
	}

	static public Statement constChangeDecl(int number) {
		return constChangeDecls(number, new TypePrimitive(4));
	}

	static public Statement varArrayDecl(String name, int length, Type type) {
		Type t = new TypeArray(type, new ExprConstInt(length));
		return new StmtVarDecl(t, name + "Array", null, 0);
	}

	// added function name
	static public StmtBlock varArrayDecls(List<String> names, List<Type> types, String funcName) {
		List<Statement> stmts = new ArrayList<Statement>();
		for (int i = 0; i < names.size(); i++) {
			if (types.get(i) instanceof TypeArray)
				continue;
			Type tarray = new TypeArray(types.get(i), new ExprConstInt(length));

			List<Expression> arrayinit = new ArrayList<>();
			for (int j = 0; j < length; j++) {
				arrayinit.add(new ExprConstInt(0));
			}

			stmts.add(new StmtVarDecl(tarray, funcName + names.get(i) + "Array", new ExprArrayInit(arrayinit), 0));
		}
		return new StmtBlock(stmts);
	}

	static public Function addConstFun(int index, int ori, Type t) {
		Expression condition = new ExprBinary(new ExprVar("const" + index + "change"), "==", new ExprConstInt(1), 0);
		StmtReturn return_1 = new StmtReturn(new ExprStar(), 0);
		StmtReturn return_2 = new StmtReturn(new ExprConstInt(ori), 0);
		Statement ifst = new StmtIfThen(condition, return_1, return_2, 0);

		return new Function("Const" + index, 
							t, 
							new ArrayList<Parameter>(), 
							ifst, 
							FcnType.Static);
	}

	/**
	 * Creates a statement node for the AST that records the programs state 
	 * at a given line number and given the variables that are in use at this point
	 * @param lineNumber - the line number to record the state at
	 * @param allVars - the variables in scope at this point
	 * @return - The statement node for an AST to input into sketch
	 */
	static public Statement recordState(int lineNumber, Map<String, Type> allVars) {
		List<String> varsInScope = new ArrayList<String>(allVars.keySet());
		StmtBlock result = new StmtBlock();
		//Of the form: (count)++;
		result.addStmt(new StmtExpr(new ExprUnary(5, new ExprVar("count"), 0), 0));
		// varToUpdateArray[count] = varToUpdate;
		/*if(global.Global.rec_mod)
			result.addStmt(
				new StmtAssign(
						new ExprArrayRange(new ExprVar("stackArray"),
								new ExprArrayRange.RangeLen(new ExprVar("count"), null), 0),
						new ExprVar("funcCount"), 0));*/

		//Of the form: lineArray[count] = 3
		result.addStmt(
			new StmtAssign(
				new ExprArrayRange(new ExprVar("lineArray"),
				new ExprArrayRange.RangeLen(new ExprVar("count"), null), 0),
				new ExprConstInt(lineNumber), 0));

		//for every variable in scope of this statement
		for (String varInScope : varsInScope) {
			if (allVars.get(varInScope) instanceof TypeArray)
				continue;
			
			if (Global.prime_mod) {
				if (Global.allvars.containsKey(varInScope)) {
					if (!Global.facts.get(lineNumber).contains(varInScope)) {
						for (int p : Global.primes)
							result.addStmt(new StmtAssign(new ExprArrayRange(
									new ExprVar(Global.curFunc + varInScope + Integer.toString(p) + "Array"),
									new ExprArrayRange.RangeLen(new ExprVar("count"), null), 0),
									new ExprVar(varInScope + Integer.toString(p)), 0));
					} else {
						result.addStmt(new StmtAssign(new ExprArrayRange(new ExprVar(Global.curFunc + varInScope + "Array"),
								new ExprArrayRange.RangeLen(new ExprVar("count"), null), 0), new ExprVar(varInScope), 0));
					}
				} else {
				}
				continue;
			}
			//Of the form: funcNameVarNameArray[count] = varName
			result.addStmt(new StmtAssign(
							new ExprArrayRange(
									new ExprVar(Global.curFunc + varInScope + "Array"),
									new ExprArrayRange.RangeLen(new ExprVar("count"), null), 0), 
							new ExprVar(varInScope), 0));
		}
		
		if (Global.prime_mod) {
			for (String al : Global.alwaysVars) {
				if (!varsInScope.contains(al) && varsInScope.contains(al + Integer.toString(Global.primes[1]))) {
					for (int p : Global.primes)
						result.addStmt(new StmtAssign(new ExprArrayRange(
								new ExprVar(Global.curFunc + al + Integer.toString(p) + "Array"),
								new ExprArrayRange.RangeLen(new ExprVar("count"), null), 0),
								new ExprVar(al + Integer.toString(p)), 0));
				}
			}
		}
		//If this statement is on the line that the manipulation occurs on
		if (lineNumber == hitline) {
			//Of the form: (linehit)++;
			result.addStmt(new StmtExpr(new ExprUnary(5, new ExprVar("linehit"), 0), 0));
			
			List<Statement> consStmts = new ArrayList<>();
			for (String localVarName : finalState.getOrdered_locals()) {
				if (allVars.get(localVarName) instanceof TypeArray)
					continue;
				//Of the form finalVarName = varName
				consStmts.add(new StmtAssign(
								new ExprVar(localVarName + "final"), 
								new ExprVar(localVarName), 
								0));
			}
			//Of the form finalcount = count;
			consStmts.add(new StmtAssign(
							new ExprVar("finalcount"), 
							new ExprVar("count"), 
							0));
			//of the form: return;
			if (ConstraintFactory.fh.getReturnType() instanceof TypeVoid) {
				consStmts.add(new StmtReturn(0));
			}
			//Of the form: return 0;
			consStmts.add(new StmtReturn(new ExprConstInt(0), 0));
			Statement cons = new StmtBlock(consStmts);
			//Of the form: if (linehit == (??)) { <consStmts> }
			Statement iflinehit = new StmtIfThen(
					new ExprBinary(new ExprVar("linehit"), "==", new ExprString("??"), 0),
					cons, null, 0);
			result.addStmt(iflinehit);
		}
		return result;
	}

	static public Statement replaceConst(Statement source) {
		List<Statement> list = new ArrayList<Statement>();
		Stack<SketchObject> stmtStack = new Stack<SketchObject>();
		int index = 0;
		stmtStack.push(source);
		while (!stmtStack.empty()) {
			SketchObject target = stmtStack.pop();
			ConstData data = null;
			if (ConstraintFactory.sign_limited_range) {
				data = target.replaceConst(index, ConstraintFactory.repair_range);
			} else {
				data = target.replaceConst(index);
			}
			if (data.getType() != null) {
				addToConstMap(data);
				addToConstMapLine(data);
				list.add(constChangeDecl(index, new TypePrimitive(1)));
				list.add(new StmtFunDecl(addConstFun(index, data.getValue(), data.getType())));
			}
			index = data.getIndex();
			pushAll(stmtStack, data.getChildren());
		}
		return new StmtBlock(list);
	}

	private static void addToConstMapLine(ConstData data) {
		constMapLine.put(data.getIndex(), data.getOriline());
	}

	private static void addToConstMap(ConstData data) {
		if (constMap.containsKey(data.getName())) {
			Set<Integer> s = constMap.get(data.getName());
			s.add(data.getIndex());
		} else {
			Set<Integer> s = new HashSet<Integer>();
			s.add(data.getIndex());
			constMap.put(data.getName(), s);
		}
	}

	static public void buildContext(StmtBlock sb) {
		Context prectx = new Context();
		prectx.setLinenumber(sb.getLineNumber());
		List<Parameter> params = fh.getPara();
    	for (Parameter param : params) {
    		prectx.addVar(param.getName(), param.getType());
    	}

		sb.buildContext(prectx, 0);
	}

	/**
	 * Adds statements to each step of the sketch input script 
	 * to record the program state
	 * @param source - the root statement block of the source code
	 * @return
	 */
	static public Map<String, Type> addRecordStmt(StmtBlock source) {
		return source.addRecordStmt(null, 0, new HashMap<String, Type>());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	static public void pushAll(Stack s, List l) {
		for (int i = l.size() - 1; i >= 0; i--) {
			s.push(l.get(i));
		}
	}

	public static Map<Integer, Integer> getconstMapLine() {
		return constMapLine;
	}

}
