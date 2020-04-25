package cfg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import global.Global;
import sketchobj.core.Function;
import sketchobj.core.Parameter;
import sketchobj.core.Type;
import sketchobj.core.TypeArray;
import sketchobj.expr.ExprArrayRange;
import sketchobj.expr.ExprBinary;
import sketchobj.expr.ExprUnary;
import sketchobj.expr.ExprVar;
import sketchobj.expr.Expression;
import sketchobj.stmts.*;

// Assume each line has at most one Statement
/**
 * 
 * Represents the control flow graph of a program. Used to find which variables
 * can have coefficients. Consists of nodes and connections between nodes.
 *
 */
public class CFG {

    private Node enter;
    //private int counter;
    private Map<Integer, List<Integer>> edges;
    private Map<Integer, Node> nodes;
    private Map<Integer, List<String>> keepActual;
    private boolean forInit = false;
    private static final Logger logger = LoggerFactory.getLogger(CFG.class);
    private Set<String> allVarNames;
    private Set<String> allTypeArrayVarNames;

    public CFG(Function function) {
    	this.edges = new HashMap<Integer, List<Integer>>();
    	this.nodes = new HashMap<Integer, Node>();
    	this.keepActual = new HashMap<Integer, List<String>>();
    	this.allVarNames = new HashSet<String>();
    	this.allTypeArrayVarNames = new HashSet<String>();
    	
    	List<Parameter> params = function.getParams();
    	//add params of function
    	for (Parameter param : params) {
    		if (param.getType() instanceof TypeArray) {
				this.allTypeArrayVarNames.add(param.getName());
    		} else {
				this.allVarNames.add(param.getName());
    		}
    	}
    	Connection con = buildStmt(function.getBody());
    	this.enter = con.getIn();
    	for (Map.Entry<Integer, Node> entry : this.nodes.entrySet()) {
    		this.keepActual.put(entry.getKey(), new ArrayList<String>());
    	}
    }
    
    public Set<String> getAllVarNames() { return this.allVarNames; }
    public Set<String> getAllTypeArrayVarNames() { return this.allTypeArrayVarNames; }

    private void updateEdges(int key, int value) {
    	if (this.edges.containsKey(key)) {
    		this.edges.get(key).add(value);
    	} else {
    		ArrayList<Integer> list = new ArrayList<Integer>();
    		list.add(value);
    		this.edges.put(key, list);
    	}
    }


    /**
     * Creates nodes and connections recursively for the statement
     * @param stmt - The statement to construct a node for
     * @return - mapping of the node for this statement to subsequent
     * nodes in the graph
     */
    private Connection buildStmt (Statement stmt) {
    	Node in = null;
    	Node out = null;

    	if (stmt instanceof StmtAssert) {
    		in = new Node(stmt.getLineNumber(), ((StmtAssert) stmt).toString(), (StmtAssert) stmt, null);
    		this.nodes.put(stmt.getLineNumber(), in);
    		return new Connection(in, in);
    	}

    	if (stmt instanceof StmtAssign) {
    		in = new Node(stmt.getLineNumber(), ((StmtAssign) stmt).toString(), (StmtAssign) stmt, null);
    		this.nodes.put(stmt.getLineNumber(), in);
    		return new Connection(in, in);
    	}

        if (stmt instanceof StmtBlock) {
    		if (((StmtBlock) stmt).stmts.size() == 0) {
				System.err.println("empty stmtBlock body!");
				return new Connection(-1);
			}
    		ArrayList<Connection> list = new ArrayList<Connection>();
    		for (Statement s : ((StmtBlock) stmt).stmts) {
    			if (s != null) {
    				list.add(buildStmt(s));
    			}
    		}
    		if (list.size() == 0) {
				System.err.println("empty stmtBlock body!");
				return new Connection(-1);
			}
    		return combineList(list);
    	}

        if (stmt instanceof StmtDoWhile) {
        	Connection con1 = buildStmt(((StmtDoWhile) stmt).getBody());
        	if (con1.getIn() != null) {
        		in = con1.getIn();

        		out = new Node(stmt.getLineNumber(), ((StmtDoWhile) stmt).getCond().getCtx().toString(),
        				null, ((StmtDoWhile) stmt).getCond());
        		this.nodes.put(stmt.getLineNumber(), out);
        		Connection con2 = new Connection(out, out);

        		ArrayList<Connection> list = new ArrayList<Connection>();
        		list.add(con1);
        		list.add(con2);
        		combineList(list);
        		this.updateEdges(out.getId(), in.getId());
        		return new Connection(in, out);
        	} else {
        		in = new Node(stmt.getLineNumber(), ((StmtDoWhile) stmt).getCond().getCtx().toString(),
        				null, ((StmtDoWhile) stmt).getCond());
        		this.nodes.put(stmt.getLineNumber(), in);
        		this.updateEdges(in.getId(), in.getId());
        		return new Connection(in, in);
        	}
    	}

        if (stmt instanceof StmtExpr) {
    		in = new Node(stmt.getLineNumber(), ((StmtExpr) stmt).toString(), (StmtExpr) stmt, null);
    		this.nodes.put(stmt.getLineNumber(), in);
    		return new Connection(in, in);
    	}

        // assume init, cond, incr are non-empty, although body might be empty
        if (stmt instanceof StmtFor) {
    		ArrayList<Connection> list = new ArrayList<Connection>();
    		this.forInit = true;
    		Connection InitCon = buildStmt(((StmtFor) stmt).getInit());
    		this.forInit = false;
    		in = InitCon.getIn();

    		Node condNode = new Node(stmt.getLineNumber(), ((StmtFor) stmt).getCond().toString(),
    				null, ((StmtFor) stmt).getCond());
    		this.nodes.put(stmt.getLineNumber(), condNode);
    		out = condNode;
    		Connection condCon = new Connection(condNode, condNode);

    		Connection bodyCon = buildStmt(((StmtFor) stmt).getBody());
    		//Connection incrCon = buildStmt(((StmtFor) stmt).getIncr());

    		list.add(InitCon);
    		list.add(condCon);
    		list.add(bodyCon);
    		//list.add(incrCon);
    		combineList(list);

    		ArrayList<Connection> loop = new ArrayList<Connection>();
    		loop.add(bodyCon);
    		//loop.add(incrCon);
    		loop.add(condCon);
    		combineList(loop);

    		return new Connection(in, out);
    	}

        // it does not matter for intra-procedure case
        if (stmt instanceof StmtFuncDecl) {
    		return buildStmt(((StmtFuncDecl) stmt).getDecl().getBody());
    	}

        if (stmt instanceof StmtIfThen) {
        	String condStr = "" + ((StmtIfThen) stmt).getCond() + "";
        	Node condNode = new Node(stmt.getLineNumber(), condStr, null, ((StmtIfThen) stmt).getCond());
    		this.nodes.put(stmt.getLineNumber(), condNode);
    		in = condNode;
    		Connection con1 = new Connection(in, in);
    		Connection con2 = buildStmt(((StmtIfThen) stmt).getCons());
    		Connection con3 = buildStmt(((StmtIfThen) stmt).getAlt());

    		ArrayList<Connection> list1 = new ArrayList<Connection>();
    		list1.add(con1);
    		list1.add(con2);
    		List<Node> out1 = combineList(list1).getOut();

    		ArrayList<Connection> list2 = new ArrayList<Connection>();
    		list2.add(con1);
    		list2.add(con3);
    		List<Node> out2 = combineList(list2).getOut();

    		List<Node> outList = new ArrayList<>();
    		outList.addAll(out1);
    		outList.addAll(out2);
    		return new Connection(in, outList);
        }

        if (stmt instanceof StmtMinimize) {
        	in = new Node(stmt.getLineNumber(), ((StmtMinimize) stmt).toString(), (StmtMinimize) stmt, null);
    		this.nodes.put(stmt.getLineNumber(), in);
    		return new Connection(in, in);
        }

        if (stmt instanceof StmtReturn) {
        	in = new Node(stmt.getLineNumber(), ((StmtReturn) stmt).toString(), (StmtReturn) stmt, null);
    		this.nodes.put(stmt.getLineNumber(), in);
    		return new Connection(in, in);
        }

        if (stmt instanceof StmtVarDecl) {
        	List<Type> types = ((StmtVarDecl) stmt).getTypes();
        	List<String> names = ((StmtVarDecl) stmt).getNames();
        	
        	for (int i = 0; i < types.size(); i++) {
        		if (types.get(i) instanceof TypeArray) {
					this.allTypeArrayVarNames.add(names.get(i));
        		} else {
					this.allVarNames.add(names.get(i));
        		}
    		}

        	in = new Node(stmt.getLineNumber(), ((StmtVarDecl) stmt).toString(), (StmtVarDecl) stmt, null);
    		this.nodes.put(stmt.getLineNumber(), in);
    		return new Connection(in, in);
        }

        if (stmt instanceof StmtWhile) {
        	in = new Node(stmt.getLineNumber(), ((StmtWhile) stmt).toString(),
        			null, ((StmtWhile) stmt).getCond());
        	this.nodes.put(stmt.getLineNumber(), in);
    		Connection con1 = new Connection(in, in);
        	Connection con2 = buildStmt(((StmtWhile) stmt).getBody());
        	if (con2.getIn() == null) {
        		return con1;
        	} else {
        		ArrayList<Connection> list = new ArrayList<Connection>();
        		list.add(con1);
        		list.add(con2);
        		combineList(list);
        		ArrayList<Connection> loop = new ArrayList<Connection>();
        		loop.add(con2);
        		loop.add(con1);
        		combineList(loop);
        		return new Connection(in, in);
        	}

        }

    	return null;
    }

	private Connection combineList(List<Connection> list) {
		/*int removed = 0;
    	for (int i = 0; i < list.size(); i++) {
    		Connection tmp = list.get(i - removed);
    		if (tmp.getIn() == null) {
    			list.remove(tmp);
    			removed++;
    		}
    	}*/

		//filter out non-existant connections
		for (int i = list.size() - 1; i >= 0; i--) {
    		Connection tmp = list.get(i);
    		if (tmp == null) {
    			list.remove(i);
    		} else {
	    		if (tmp.getIn() == null) {
	    			list.remove(i);
	    		}
    		}
    	}

    	int actualSize = list.size();
    	if (actualSize == 0) {
			logger.warn("empty CFG list!");
			return new Connection(-1);
		}
    	if (actualSize == 1) {
    		return list.get(0);
    	}

    	Node in = list.get(0).getIn();
    	List<Node> out = list.get(actualSize - 1).getOut();

    	for (int i = 0; i < actualSize - 1; i++) {
    		int inId = list.get(i + 1).getIn().getId();
    		List<Node> tmpOut = list.get(i).getOut();
    		for (int j = 0; j < tmpOut.size(); j++) {
    			this.updateEdges(tmpOut.get(j).getId(), inId);
    		}
    	}

    	return new Connection(in, out);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CFG:\n");
		sb.append("entrance is Node " + Integer.toString(enter.getId()) + "\n");
		sb.append("size is " +  Integer.toString(this.nodes.size()) + "\n");
		sb.append("nodes: \n");
		for (Map.Entry<Integer, Node> entry : this.nodes.entrySet()) {
		    sb.append(entry.getValue().toStringNode() + "\n");
		}
		sb.append("edges: \n");
		for (Map.Entry<Integer, List<Integer>> entry : this.edges.entrySet()) {
		    sb.append(entry.getKey() + " : " + entry.getValue() + "\n");
		}
		return sb.toString();
	}

	/* backward may dataflow analysis framework
	in(exit) = ∅
	for all other statements s
		in(s) = ∅
	W = all statements
	while W not empty
		take s from W
		out(s) = ∪s′∈succ(s) in(s′)
		temp = gen(s) ∪ (out(s) - kill(s))
		if temp ≠ in(s) then
			in(s) = temp
			W := W ∪ pred(s)
		end
	end
	*/
	/**
	 * For each part in the control graph, finds active variables
	 * @return - for each part of the control graph, all available 
	 * variable names
	 */
	public Map<Integer, Set<String>> dataflow() {
		
		Set<Integer> indexes = this.nodes.keySet();
		Map<Integer, Set<String>> in = new HashMap<Integer, Set<String>>();
		Map<Integer, Set<String>> out = new HashMap<Integer, Set<String>>();
		Map<Integer, Set<String>> gen = this.getHead();
		for (int i : indexes) {
			in.put(i, new HashSet<String>());
			out.put(i, new HashSet<String>());
		}
		
		Queue<Integer> W = new LinkedList<Integer>(indexes);
		while (!W.isEmpty()) {
			
			int s = W.poll();
			List<Integer> succ = this.edges.get(s);
			Set<String> outS = new HashSet<String>();
			if (succ != null ) {
				for (int i : succ) {
					outS.addAll(in.get(i));
				}
			}
			out.put(s, outS);

			// if there is a StmtAssign, add more vars to gen
			if (nodes.get(s).isStmt()) {
				
				Statement st = nodes.get(s).getStmt();
				if (st instanceof StmtAssign) {
					
					Set<String> leftVars = st.getVarNames(-1);
					Set<String> rightVars = st.getVarNames(1);
					
					for (String str : leftVars) {
						if (outS.contains(str)) {
							if (gen.containsKey(s)) {
								gen.get(s).addAll(rightVars);
							} else {
								gen.put(s, rightVars);
							}
							break;
						}
					}
				}
				
				if (st instanceof StmtVarDecl) {
					
					StmtVarDecl curStmt = (StmtVarDecl) st;
					
					for (int i = 0; i < curStmt.getNames().size(); i++) {
						
						if (outS.contains(curStmt.getName(i))) {
							
							Expression expr = curStmt.getInit(i);
							if (expr != null) {
								if (gen.containsKey(s)) {
									gen.get(s).addAll(expr.getVarNames());
								} else {
									gen.put(s, expr.getVarNames());
								}
							}
						}
					}
				}
			}

			Set<String> temp = new HashSet<>(outS);
			
			if (gen.containsKey(s)) {
				temp.addAll(gen.get(s));
			}

			if (!temp.equals(in.get(s))) {
				
				in.put(s, temp);
				
				for (int i : this.getPred(s)) {
					W.offer(i);
				}
			}
		}
		return in;
	}

	/*
	public void dataflow(){
		Map<Integer, List<String>> head = this.getHead();
		for (Map.Entry<Integer, List<String>> entry : head.entrySet()) {
			//System.out.println(entry.getKey());
			//System.out.println(entry.getValue());
			boolean[] mark = new boolean[this.nodes.size()];
			for (int i = 0; i < mark.length; i++) {
				mark[i] = false;
			}
			Queue<Map<Integer, List<String>>> front = new LinkedList<Map<Integer, List<String>>>();
			int key = entry.getKey();
			List<String> value = entry.getValue();

			Map<Integer, List<String>> thisMap = this.initMap();
			thisMap.replace(key, value);

			// take one step backwards
			List<Integer> stepBack = this.getInNeighbor(key);
			for (Integer i : stepBack) {
				if (!mark[i]) {
					front.add(i);
					// if there is a StmtAssign, add more vars to value
					if (nodes.get(key).isStmt()) {
						Statement st = nodes.get(key).getStmt();
						if (st instanceof StmtAssign) {
							List<String> leftVars = this.extractVarFromExpr(((StmtAssign) st).getLHS(), 0);
						}
					}
					List<String> list = this.keepActual.get(i);
					for (String s : value) {
						if (!list.contains(s))
							list.add(s);
					}
					mark[i] = true;
				}
			}

			// BFS traverse CFG
			while (!front.isEmpty()) {
				List<Integer> back = this.getInNeighbor(front.poll());
				for (Integer i : back) {
					if (!mark[i]) {
						front.add(i);
						List<String> list = this.keepActual.get(i);
						for (String s : value) {
							if (!list.contains(s))
								list.add(s);
						}
						mark[i] = true;
					}
				}
			}

		}
		for (Map.Entry<Integer, List<String>> entry : this.keepActual.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
	}
	*/

	private Map<Integer, List<String>> initMap() {
		Map<Integer, List<String>> res = new HashMap<Integer, List<String>>();
		for (Map.Entry<Integer, Node> entry : this.nodes.entrySet()) {
    		res.put(entry.getKey(), new ArrayList<String>());
    	}
		return res;
	}

	/**
	 * Finds the preceeding control blocks for the given control block
	 * @param nodeNum - the control block to find preds for
	 * @return - all control blocks that transfer control to the
	 * given block
	 */
	private Set<Integer> getPred(int nodeNum) {
		
		Set<Integer> res = new HashSet<Integer>();
		
		for (Map.Entry<Integer, List<Integer>> entry : this.edges.entrySet()) {
			
			if (entry.getValue().contains(nodeNum)) {
				res.add(entry.getKey());
			}
		}
		return res;
	}

	/**
	 * 
	 * get the starting points of backwards data-flow analysis
	 * if there is a < b at line 5, add Map.Entry<5, {a,b}>
	 * @return - the control flow block to varnames used during that block
	 */
	private Map<Integer, Set<String>> getHead() {
		
		Map<Integer, Set<String>> map = new HashMap<Integer, Set<String>>();
		
		for (Map.Entry<Integer, Node> entry : this.nodes.entrySet()) {
			
			int key = entry.getKey();
			Node node = entry.getValue();
			logger.debug("Finding vars for node: " + key);
			if (node.getType() == 1) {
				
				Set<String> set = node.getStmt().getVarNames(0);
				logger.debug("Adding stmt vars to head: " + set);
				if (set.size() > 0) {
					map.put(key, set);
				}
			} else if (node.getType() == 2) {
				
				Set<String> set = node.getExpr().getVarNames();
				logger.debug("Adding expr vars to head: " + set);
				if (set.size() > 0) {
					map.put(key, set);
				}
			} else {
				logger.debug("node type not stmt or expr");
			}
		}
		return map;
	}

	public void GenfeasibleVars() {
		for (String varName : this.allVarNames) {
			boolean feasible = false;
			for (Map.Entry<Integer, Set<String>> entry1 : Global.facts.entrySet()) {
				if (!entry1.getValue().contains(varName)) {
					feasible = true;
					break;
				}
			}
			if (feasible)
				Global.feasibleVars.put(varName, false);
		}
		//System.err.println(Global.feasibleVars);
	}

	public static void GenAlwaysVars() {
		for (Map.Entry<String, Boolean> entry : Global.feasibleVars.entrySet()) {
			boolean always = true;
			String name = entry.getKey();
			for (Map.Entry<Integer, Set<String>> entry1 : Global.facts.entrySet()) {
				if (entry1.getValue().contains(name)) {
					always = false;
					break;
				}
			}
			if (always)
				Global.alwaysVars.add(name);
		}
	}

	/**
	 * 
	 */
	public void inilocs() {
		
		for (Map.Entry<String, Boolean> entry : Global.feasibleVars.entrySet()) {
			if (this.allVarNames.contains(entry.getKey()))
				Global.inilocs.put(entry.getKey(), new HashSet<>());
		}
		
		for (String var : Global.alwaysVars)
			if (this.allVarNames.contains(var))
				Global.inilocs.get(var).add(enter.getId());
		
		if (!Global.only_mod)
			for (Map.Entry<Integer, List<Integer>> entry : this.edges.entrySet()) {
				
				for (String varName : this.allTypeArrayVarNames) {
					if (Global.facts.get(entry.getKey()).contains(varName)) {
						for (int tail : entry.getValue()) {
							if (!Global.facts.get(tail).contains(varName)) {
								Global.inilocs.get(varName).add(tail);
							}
						}
					}
				}
			}
	}

	public void getAltFacts() {
		Set<String> nonvars = new HashSet<>();
		for (String varName : this.allVarNames) {
			if (!Global.alwaysVars.contains(varName))
				nonvars.add(varName);
		}
		for (Map.Entry<Integer, Node> entry : this.nodes.entrySet()) {
			Set<String> curvars = new HashSet<>();
			Node node = entry.getValue();
			if (node.getType() == 1) {
				Set<String> leftvars = node.getStmt().getVarNames(-1);
				curvars.addAll(leftvars);
				if (node.getStmt() instanceof StmtAssign || node.getStmt() instanceof StmtVarDecl) {
					for (String lv : leftvars)
						Global.inilocs.remove(lv);
				}
				curvars.addAll(node.getStmt().getVarNames(1));
			} else if (node.getType() == 2) {
				curvars.addAll(node.getExpr().getVarNames());
			}
			if (!Collections.disjoint(nonvars, curvars))
				Global.altfacts.add(entry.getKey());
		}

	}

}
