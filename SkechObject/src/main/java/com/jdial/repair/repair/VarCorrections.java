package repair;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import sketchobj.expr.ExprConstInt;
import sketchobj.expr.ExprConstant;

/**
 * This class represents part of the repair input specifying what variable values should be
 * corrected to.
 */
public class VarCorrections implements Iterable<VarCorrections.VarCorrection>{
	
	private Set<VarCorrection> corrections;

	public VarCorrections(Map<String, Integer> varNameToValue) {
		this.corrections = new HashSet<VarCorrection>();
		for (Map.Entry<String, Integer> entry : varNameToValue.entrySet()) {
			this.addCorrection(entry.getKey(), entry.getValue());
		}
	}
	
	private void addCorrection(String varName, int correctVal) {
		ExprConstant valueExpr = new ExprConstInt(correctVal);
		VarCorrection correction = new VarCorrection(varName, valueExpr);
		this.corrections.add(correction);
	}
	
	//TODO remove this if no need for it
	@Override
	public Iterator<VarCorrection> iterator() {
		return this.corrections.iterator();
	}

	public class VarCorrection {
		public String varName;
		public ExprConstant valueExpr;
		
		public VarCorrection(String varName, ExprConstant valueExpr) {
			this.varName = varName;
			this.valueExpr = valueExpr;
		}
	}

}
