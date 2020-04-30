package sketchobj.core;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import sketch_input.Coefficient;

public class TypeStructRef extends Type{
	
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isUnboxed ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		TypeStructRef other = (TypeStructRef) obj;
		if (isUnboxed != other.isUnboxed)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	private String name;
    private final boolean isUnboxed;
    
    public TypeStructRef(String name, boolean isUnboxed) {
        this.name = name;
        this.isUnboxed = isUnboxed;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isStruct () { return true; }
    
    public Collection<Type> getBaseTypes() {
        return Collections.singletonList((Type) this);
    }
    
	public boolean isUnboxed() {
		return isUnboxed;
	}
	
	@Override
	public void insertCoeffs(List<Coefficient> coeffs) {}
}
