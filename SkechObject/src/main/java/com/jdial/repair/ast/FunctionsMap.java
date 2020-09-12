package ast;

import java.util.HashMap;
import java.util.Map;

import collections.LimitedMap;
import sketchobj.core.Function;

/**
 * Maps a function name to a function. Meant to limit the Map API
 * 
 */
public class FunctionsMap implements LimitedMap<String, Function>{

	private Map<String, Function> funcs;

	public FunctionsMap() {
		this.funcs = new HashMap<String, Function>();
	}

	public void put(String funcName, Function func) {
		this.funcs.put(funcName, func);
	}

}
