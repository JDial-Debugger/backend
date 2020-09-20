package sketchobj.expr.binary;

class NumericVals<N extends Number> {

	public N lhsVal;
	public N rhsVal;
	public boolean hasVals;
	
	public NumericVals(N lhsVal, N rhsVal) {
		this.lhsVal = lhsVal;
		this.rhsVal = rhsVal;
		this.hasVals = true;
	}
	
	public NumericVals() {
		this.hasVals = false;
	}

}
