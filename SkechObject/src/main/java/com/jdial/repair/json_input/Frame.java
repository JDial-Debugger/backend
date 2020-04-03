package json_input;

import java.util.List;

public class Frame {

	private String name;
	private VarList encodedLocals;
	private VarList orderedVarnames;
	private boolean highlighted;
	private boolean zombie;
	private Integer id;

	public Frame() {}
	
	public String toString(){
		return name + "\n" + id+"\n"+encodedLocals.toString();
	}
	
	public String getName() { return this.name; }
	public VarList getEncodedLocals() { return this.encodedLocals; }
	public VarList getOrderedVarnames() { return this.orderedVarnames; }
	public boolean isHighlighted() { return this.highlighted; }
	public boolean isZombie() { return this.zombie; }
	public Integer getId() { return this.id; }
	public List<String> getOrderedLocals() { return this.orderedVarnames.toStringList(); }

}
