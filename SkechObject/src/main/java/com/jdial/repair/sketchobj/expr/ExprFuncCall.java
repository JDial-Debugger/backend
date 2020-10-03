package sketchobj.expr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import coefficient.Coefficient;
import constraintfactory.ConstData;
import constraintfactory.ExternalFunction;
import sketchobj.core.ExpressionList;

public class ExprFuncCall extends Expression
{
    private final String name;
    private String name_Java;
    private static int NEXT_UID=0;
	private int line;
    private int clusterId; // Used to identify the cluster to combine funCalls.
    private int callId;
    private final List<Expression> params;

    public void resetCallid(){
    	this.callId = NEXT_UID++;
    }

    public int getCallId(){
    	return callId;
    }
    
    /** Creates a new function call with the specified name and
     * parameter list. */
    public ExprFuncCall(String name, List<Expression> params, String nameJ) {
        this.name = name;
        this.params = Collections.unmodifiableList(params);
        this.name_Java = nameJ;
    }
    
    public ExprFuncCall(String name, List<Expression> params) {
        this.name = name;
        this.params = Collections.unmodifiableList(params);
        this.name_Java = name;
    }
    
    public ExprFuncCall(String name, ExpressionList l, String nameJ) {
    	this.name = name;
    	this.params = l.getList();
    	this.name_Java = nameJ;
    }

    /** Creates a new function call with the specified name and
     * specified single parameter. */
    public ExprFuncCall(String name, Expression param, String nameJ)
    {
    	this ( name, Collections.singletonList (param), nameJ);
    }

    public ExprFuncCall(String name) {
        this(name, new ArrayList<Expression>());
    }
    
    public ExprFuncCall( String name, String nameJ, Expression... params) {
        this(name, Arrays.asList(params), nameJ);
    }
    
    @Override
	public ExprFuncCall clone() {
		return new ExprFuncCall(this.name, this.params, this.name_Java);
	}

    /** Returns the name of the function being called. */
    public String getName()
    {
        return name;
    }

    /** Returns the parameters of the function call, as an unmodifiable
     * list. */
    public List<Expression> getParams()
    {
        return params;
    }

    public String printParams(){
        String s = "";
        boolean notf = false;
        for(Expression p : params){
            if(notf){ s += ", "; }
            s += p.toString();
            notf = true;
        }
        return s;
    }

    public void setClusterId(int i) {
        this.clusterId = i;
    }

    public int getClusterId() {
        return this.clusterId;
    }
    
    public String toString()
    {
        return name + "(" + printParams() + ")";
    }

	@Override
	public ConstData replaceConst(int index) {
		// TODO Auto-generated method stub
		return new ConstData(index,this.line);
	}

	@Override
	public ConstData replaceConst(int index, String string) {
		// TODO Auto-generated method stub
		return new ConstData(index,string,this.line);
	}

	@Override
	public List<ExternalFunction> extractExternalFuncs(List<ExternalFunction> externalFuncNames) {
		for(ExternalFunction ef: externalFuncNames){
			if(ef.getName().equals(this.name))
				return externalFuncNames;
		}
		Integer ary = this.params.size();
		// TODO: Number of External Call
		externalFuncNames.add(new ExternalFunction(this.name,this.name_Java, ary,10));
		return externalFuncNames;
	}

	@Override
	public void checkAtom() {
		this.setAtom(true);
		
	}

	//TODO unimplemented
	@Override
	public Set<String> getVarNames() {
		return new HashSet<String>();
	}
	
	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {}
}