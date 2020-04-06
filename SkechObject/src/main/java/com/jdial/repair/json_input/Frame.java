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
	
	@Override
	public String toString(){
		return name + "\n" + id+"\n"+encodedLocals.toString();
	}
	
	public String getName() { return this.name; }
	public Map<String, Integer> getEncodedLocals() { return this.encodedLocals; }
	public List<String> getOrderedVarnames() { return this.orderedVarnames; }
	public boolean isHighlighted() { return this.highlighted; }
	public boolean isZombie() { return this.zombie; }
	public Integer getId() { return this.id; }
	public List<String> getOrderedLocals() { return this.orderedVarnames; }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((encodedLocals == null) ? 0 : encodedLocals.hashCode());
		result = prime * result + (highlighted ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orderedVarnames == null) ? 0 : orderedVarnames.hashCode());
		result = prime * result + (zombie ? 1231 : 1237);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Frame other = (Frame) obj;
		if (encodedLocals == null) {
			if (other.encodedLocals != null)
				return false;
		} else if (!encodedLocals.equals(other.encodedLocals))
			return false;
		if (highlighted != other.highlighted)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orderedVarnames == null) {
			if (other.orderedVarnames != null)
				return false;
		} else if (!orderedVarnames.equals(other.orderedVarnames))
			return false;
		if (zombie != other.zombie)
			return false;
		return true;
	}

}
