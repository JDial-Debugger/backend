package jsonast;

import java.util.ArrayList;
import java.util.List;


public class Assertions extends JsonNode{

	private List<String> assertions;

	public Assertions(List<String> assertions) {
		this.assertions = assertions;
	}

	public List<String> getAssertions() {
		return assertions;
	}

	public void setAssertions(List<String> assertions) {
		this.assertions = assertions;
	}
}
