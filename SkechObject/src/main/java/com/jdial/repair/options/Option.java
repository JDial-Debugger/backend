package options;

class Option {
	
	public Object value;
	public boolean isRequired;
	
	public Option(Object value, boolean isRequired) {
		this.value = value;
		this.isRequired = isRequired;
	}
}
