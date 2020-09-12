package ast;

import java.util.Set;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import javaparser.simpleJavaLexer;
import javaparser.simpleJavaParser;
import sketchobj.core.Function;
import sketchobj.core.SketchObject;
import visitor.JavaVisitor;

/**
 * Wraps a 3rd party parser so that we can parse java code in an easy to use
 * API. Currently wraps the ANTLR parser.
 */
public class ParserWrapper {

	private FunctionsMap funcsMap;
	private JavaVisitor javaVisitor;

	public ParserWrapper(FunctionsMap funcsMap, JavaVisitor javaVisitor) {
		this.funcsMap = funcsMap;
		this.javaVisitor = javaVisitor;
	}

	public FunctionsMap parseFunctionsFromCode(Set<String> funcNamesToParse, String code) {

		simpleJavaParser parser = this.initAntlrParser(code);

		for (String funcName : funcNamesToParse) {
			this.javaVisitor.setFunctionToParse(funcName);
			SketchObject funcAst = this.javaVisitor.visit(parser.compilationUnit());
			this.funcsMap.put(funcName, (Function) funcAst);

		}
		return this.funcsMap;
	}

	private simpleJavaParser initAntlrParser(String code) {

		ANTLRInputStream input = new ANTLRInputStream(code);
		simpleJavaLexer lexer = new simpleJavaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		return new simpleJavaParser(tokens);
	}

}
