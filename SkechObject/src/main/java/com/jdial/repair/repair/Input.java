package repair;

public class Input {
	
	public RepairType repairType;
	public String json;
	
	public Input(RepairType repairType, String json) {
		this.repairType = repairType;
		this.json = json;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.json == null) ? 0 : this.json.hashCode());
		result = prime * result + ((this.repairType == null) ? 0 : this.repairType.hashCode());
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
		Input other = (Input) obj;
		if (this.json == null) {
			if (other.json != null)
				return false;
		} else if (!this.json.equals(other.json))
			return false;
		if (this.repairType != other.repairType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Input [repairType=" + this.repairType + ", json=" + this.json + "]";
	}
	
	
}
