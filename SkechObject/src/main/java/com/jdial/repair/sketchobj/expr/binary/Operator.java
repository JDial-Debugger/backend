package sketchobj.expr.binary;

public enum Operator {

	ADD("+"),
	AND("&&"),
	BITWISE_AND("&"),
	BITWISE_OR("|"),
	DIVIDE("/"),
	EQUALS("=="),
	GREATER(">"),
	GREATER_EQUALS(">="),
	LESS("<"),
	LESS_EQUALS("<="),
	LEFT_SHIFT("<<"),
	MULTIPLY("*"),
	MODULO("%"),
	NOT_EQUALS("!="),
	OR("||"),
	SIGNED_RIGHT_SHIFT(">>"),
	SUBTRACT("-"),
	UNSIGNED_RIGHT_SHIFT(">>>"),
	XOR("^");

	private final String codeRepresentation;

	Operator(String codeRepresentation) {
		this.codeRepresentation = codeRepresentation;
	}

	public String toString() {
		return codeRepresentation;
	}
}
