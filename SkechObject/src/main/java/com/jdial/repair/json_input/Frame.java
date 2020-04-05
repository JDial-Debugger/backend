package json_input;

import java.util.List;
import java.util.Map;

public class Frame {

	private String name;
	private Map<String, Integer> encodedLocals;
	private List<String> orderedVarnames;
	private boolean highlighted;
	private boolean zombie;
	private Integer id;

	public Frame() {}
	public Frame(String name,
					Map<String, Integer> encodedLocals,
					List<String> orderedVarnames,
					boolean highlighted,
					boolean zombie,
					Integer id) {
		this.name = name;
		this.encodedLocals = encodedLocals;
		this.orderedVarnames = orderedVarnames;
		this.highlighted = highlighted;
		this.zombie = zombie;
		this.id = id;
	}
	
	public String toString(){
		return name + "\n" + id+"\n"+encodedLocals.toString();
	}
	
	public String getName() { return this.name; }
	public Map<String, Integer> getEncodedLocals() { return this.encodedLocals; }
	public List<String> getOrderedVarnames() { return this.orderedVarnames; }
	public boolean isHighlighted() { return this.highlighted; }
	public boolean isZombie() { return this.zombie; }
	public Integer getId() { return this.id; }
	public List<String> getOrderedLocals() { return this.orderedVarnames.toStringList(); }

}
