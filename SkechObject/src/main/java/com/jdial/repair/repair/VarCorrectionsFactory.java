package repair;

import java.util.Map;

public class VarCorrectionsFactory {

	public VarCorrectionsFactory() {
	}

	public VarCorrections getVarCorrections(Map<String, Integer> varNameToValue) {
		return new VarCorrections(varNameToValue);
	}

}
