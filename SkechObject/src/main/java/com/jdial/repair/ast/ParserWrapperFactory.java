package ast;

import visitor.JavaVisitor;

public class ParserWrapperFactory {
	
	public ParserWrapperFactory() {
		
	}
	
	public ParserWrapper getParserWrapper() {
		return new ParserWrapper(new FunctionsMap(), new JavaVisitor());
	}

}
