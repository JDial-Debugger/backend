package visitor;

import java.util.*;

import javaparser.*;
import org.antlr.v4.runtime.tree.ParseTree;
import sketchobj.core.*;
import sketchobj.expr.*;
import sketchobj.expr.ExprArrayRange.RangeLen;
import sketchobj.expr.binary.And;
import sketchobj.expr.binary.BitwiseAnd;
import sketchobj.expr.binary.BitwiseOr;
import sketchobj.expr.binary.ExprBinary;
import sketchobj.expr.binary.ExprBinaryFactory;
import sketchobj.expr.binary.ExprBinaryOptions;
import sketchobj.expr.binary.GetExprBinaryOptions;
import sketchobj.expr.binary.LeftShift;
import sketchobj.expr.binary.Or;
import sketchobj.expr.binary.RightShift;
import sketchobj.expr.binary.Xor;
import sketchobj.stmts.*;

public class JavaVisitor extends simpleJavaBaseVisitor<SketchObject> {

	private ExprBinaryFactory binaryExprFactory;
	private String functionToParse;

	// added 11/18
	public static Queue<String> methodNames = new LinkedList<>();
	// added 11/18

	public JavaVisitor(ExprBinaryFactory binaryExprFactory) {
		this.binaryExprFactory = binaryExprFactory;
	}

	public JavaVisitor(String functionToParse, ExprBinaryFactory binaryExprFactory) {
		this(binaryExprFactory);
		this.functionToParse = functionToParse;
	}

	public void setFunctionToParse(String functionToParse) {
		this.functionToParse = functionToParse;
	}

	/**
	 * compilationUnit : packageDeclaration? importDeclaration* typeDeclaration* EOF
	 **/
	@Override
	public SketchObject visitCompilationUnit(simpleJavaParser.CompilationUnitContext ctx) {
		return visit(
			ctx.typeDeclaration(0).classDeclaration().normalClassDeclaration().classBody()
		);
	}

	/**
	 * classBody : '{' classBodyDeclaration* '}' ;
	 */
	@Override
	public SketchObject visitClassBody(simpleJavaParser.ClassBodyContext ctx) {
		for (int i = 0; i < ctx.classBodyDeclaration().size(); i++) {
			if (
				functionToParse == null
					|| functionToParse.equals(
						ctx.classBodyDeclaration()
							.get(i)
							.classMemberDeclaration()
							.methodDeclaration()
							.methodHeader()
							.getChild(1)
							.getChild(0)
							.getText()
					)
			) {
				return visit(
					ctx.classBodyDeclaration().get(i).classMemberDeclaration().methodDeclaration()
				);
			}
		}
		return null;
	}

	/** methodModifier* methodHeader methodBody **/
	@Override
	public SketchObject visitMethodDeclaration(simpleJavaParser.MethodDeclarationContext ctx) {

		FcnHeader head = (FcnHeader) visit(ctx.methodHeader());
		Statement body = (Statement) visit(ctx.methodBody());
		return new Function(head, body);
	}

	/** result methodDeclarator throws_? **/
	@Override
	public SketchObject visitExpandHeader(simpleJavaParser.ExpandHeaderContext ctx) {
		FcnHeader head = (FcnHeader) visit(ctx.methodDeclarator());
		head.setReturnType((Type) visit(ctx.result()));

		return head;
	}

	/** Identifier '(' formalParameterList? ')' dims? **/
	@Override
	public SketchObject visitMethodDeclarator(simpleJavaParser.MethodDeclaratorContext ctx) {
		if (ctx.formalParameterList() != null)
			return new FcnHeader(
				ctx.Identifier().getText(),
				null,
				(ParametersList) visit(ctx.formalParameterList())
			);
		else
			return new FcnHeader(ctx.Identifier().getText(), null, new ArrayList<Parameter>());
	}

	/** : unannType | 'void' **/
	@Override
	public SketchObject visitResult(simpleJavaParser.ResultContext ctx) {
		if (ctx.start.getText().equals("void")) {
			return new TypeVoid();
		}
		return visit(ctx.unannType());
	}

	/** unannPrimitiveType **/
	@Override
	public SketchObject visitUnannTypePri(simpleJavaParser.UnannTypePriContext ctx) {
		return visit(ctx.unannPrimitiveType());
	}

	@Override
	public SketchObject visitBoolType(simpleJavaParser.BoolTypeContext ctx) {
		return new TypePrimitive(TypePrimitive.TYPE_BIT);
	}

	@Override
	public SketchObject visitNumType(simpleJavaParser.NumTypeContext ctx) {
		return visit(ctx.numericType());
	}

	@Override
	public SketchObject visitNumericIntegeralType(
		simpleJavaParser.NumericIntegeralTypeContext ctx
	) {
		return visit(ctx.integralType());
	}

	@Override
	public SketchObject visitIntegralType(simpleJavaParser.IntegralTypeContext ctx) {
		String t = ctx.getStart().getText();
		switch (t) {
		case "byte":
			return new TypePrimitive(TypePrimitive.TYPE_BIT);
		case "short":
			return new TypePrimitive(TypePrimitive.TYPE_INT16);
		case "int":
			return new TypePrimitive(TypePrimitive.TYPE_INT);
		case "long":
			return new TypePrimitive(TypePrimitive.TYPE_INT64);
		case "char":
			return new TypePrimitive(TypePrimitive.TYPE_CHAR);
		default:
			throw new IllegalArgumentException("Unknown type " + t);
		}
	}

	@Override
	public SketchObject visitNumericFloatingPointType(
		simpleJavaParser.NumericFloatingPointTypeContext ctx
	) {
		return visit(ctx.floatingPointType());
	}

	@Override
	public SketchObject visitFloatingPointType(simpleJavaParser.FloatingPointTypeContext ctx) {
		String t = ctx.getStart().getText();
		switch (t) {
		case "float":
			return new TypePrimitive(TypePrimitive.TYPE_FLOAT);
		case "double":
			return new TypePrimitive(TypePrimitive.TYPE_DOUBLE);
		default:
			throw new IllegalArgumentException("Unknown type " + t);
		}
	}

	/** unannReferenceType **/
	@Override
	public SketchObject visitUnannTypeRef(simpleJavaParser.UnannTypeRefContext ctx) {
		return visit(ctx.unannReferenceType());
	}

	@Override
	public SketchObject visitUarrayType(simpleJavaParser.UarrayTypeContext ctx) {
		return visit(ctx.unannArrayType());
	}

	/** unannPrimitiveType dims **/
	@Override
	public SketchObject visitUnannArrayTypePri(simpleJavaParser.UnannArrayTypePriContext ctx) {
		return new TypeArray((Type) visit(ctx.unannPrimitiveType()), null);
	}

	/** annotation* '[' ']' (annotation* '[' ']')* **/
	@Override
	public SketchObject visitDims(simpleJavaParser.DimsContext ctx) {
		// TODO 2dimArray
		return null;
	}

	/** formalParameters ',' lastFormalParameter **/
	@Override
	public SketchObject visitExpandParaList(simpleJavaParser.ExpandParaListContext ctx) {
		ParametersList list = (ParametersList) visit(ctx.formalParameters());
		list.add((Parameter) visit(ctx.lastFormalParameter()));
		return list;
	}

	/** lastFormalParameter **/
	@Override
	public SketchObject visitOnlyOnePara(simpleJavaParser.OnlyOneParaContext ctx) {
		ParametersList list = new ParametersList();
		list.add((Parameter) visit(ctx.lastFormalParameter()));
		return list;
	}

	@Override
	public SketchObject visitExpandLastPara(simpleJavaParser.ExpandLastParaContext ctx) {
		return visit(ctx.formalParameter());
	}

	/** formalParameter (',' formalParameter)* **/
	@Override
	public SketchObject visitFormalPara(simpleJavaParser.FormalParaContext ctx) {
		ParametersList list = new ParametersList();
		for (int i = 0; i < ctx.formalParameter().size(); i++) {
			list.add((Parameter) visit(ctx.formalParameter(i)));
		}
		return list;
	}

	/** variableModifier* unannType variableDeclaratorId **/
	@Override
	public SketchObject visitFormalParameter(simpleJavaParser.FormalParameterContext ctx) {
		// TODO dims?
		return new Parameter(
			(Type) visit(ctx.unannType()),
			ctx.variableDeclaratorId().Identifier().getText(),
			0,
			false
		);
	}

	// ----------body----------

	/** block | ';' **/
	@Override
	public SketchObject visitMethodBody(simpleJavaParser.MethodBodyContext ctx) {
		return visit(ctx.block());
	}

	/** '{' blockStatements? '}' **/
	@Override
	public SketchObject visitBlock(simpleJavaParser.BlockContext ctx) {
		List<Statement> list = new ArrayList<Statement>();
		for (int i = 0; i < ctx.blockStatements().blockStatement().size(); i++) {
			list.add((Statement) visit(ctx.blockStatements().blockStatement(i)));
		}
		return new StmtBlock(list);
	}

	/**
	 * Returns a special node for asserting function returns that should not be included with source
	 * code. Requires that exactly one side of the conditional in the assert statement is a function
	 * call
	 */
	@Override
	public StmtFuncAssert visitAssertStatement(simpleJavaParser.AssertStatementContext ctx) {
		ExprBinary assertExpr = (ExprBinary) visit(ctx.expression(0));
		Expression lhs = assertExpr.getLeft();
		Expression rhs = assertExpr.getRight();
		// check if function call in exactly one of lhs or rhs
		if (lhs instanceof ExprFuncCall && !(rhs instanceof ExprFuncCall)) {
			return new StmtFuncAssert((ExprFuncCall) lhs, rhs);
		} else if (rhs instanceof ExprFuncCall && !(lhs instanceof ExprFuncCall)) {
			return new StmtFuncAssert((ExprFuncCall) rhs, lhs);
		} else {
			return null;
		}
	}

	/** localVariableDeclarationStatement **/
	@Override
	public Statement visitLocalVarDecl(simpleJavaParser.LocalVarDeclContext ctx) {
		Type t
			= (Type) visit(
				ctx.localVariableDeclarationStatement().localVariableDeclaration().unannType()
			);
		ArrayList<Type> types = new java.util.ArrayList<Type>();
		ArrayList<String> names = new java.util.ArrayList<String>();
		ArrayList<Expression> inits = new java.util.ArrayList<Expression>();
		if (!(t instanceof TypeArray)) {
			for (
				int i = 0;
				i
					< ctx.localVariableDeclarationStatement()
						.localVariableDeclaration()
						.variableDeclaratorList()
						.variableDeclarator()
						.size();
				i++
			) {
				types.add(t);
				// TODO dims
				names.add(
					ctx.localVariableDeclarationStatement()
						.localVariableDeclaration()
						.variableDeclaratorList()
						.variableDeclarator()
						.get(i)
						.variableDeclaratorId()
						.Identifier()
						.getText()
				);
				// TODO check what if variableInitializer dosen't exist
				inits.add(
					(Expression) visit(
						ctx.localVariableDeclarationStatement()
							.localVariableDeclaration()
							.variableDeclaratorList()
							.variableDeclarator()
							.get(i)
							.variableInitializer()
					)
				);
			}
		} else {
			names.add(
				ctx.localVariableDeclarationStatement()
					.localVariableDeclaration()
					.variableDeclaratorList()
					.variableDeclarator()
					.get(0)
					.variableDeclaratorId()
					.Identifier()
					.getText()
			);
			// in Java you can declare int[] arr;
			// but in sketch, it must be int[3] arr; where the constant > 0
			// for now, just initialize it to size 1 if no length given
			ExprConstInt arrSizeExpr = ExprConstInt.createConstant(1);
			((TypeArray) t).setLenghth(arrSizeExpr);
			if (
				ctx.localVariableDeclarationStatement()
					.localVariableDeclaration()
					.variableDeclaratorList()
					.variableDeclarator()
					.get(0).children.size()
					== 1
			) {
				types.add(t);
				inits.add(null);
				StmtVarDecl ret = new StmtVarDecl(types, names, inits, ctx.start.getLine());
				return ret;
			}
			ExprArrayInit ei
				= (ExprArrayInit) visit(
					ctx.localVariableDeclarationStatement()
						.localVariableDeclaration()
						.variableDeclaratorList()
						.variableDeclarator()
						.get(0)
						.variableInitializer()
				);
			// In java array inits are: int[] arr = {4, 32, 1};
			// while in sketch they are: int[3] arr = {4, 32, 1};
			// following lines change that TODO: make this work for multi dimensional arrays, or
			// just fix the root of the problem when a type object gets created
			int arrSize = ((ExprArrayInit) ei).getElements().size();
			arrSizeExpr = ExprConstInt.createConstant(arrSize);
			((TypeArray) t).setLenghth(arrSizeExpr);
			if (ei.length == null) {
				types.add(t);
				inits.add(ei);
				StmtVarDecl ret = new StmtVarDecl(types, names, inits, ctx.start.getLine());
				return ret;
			} else {
				((TypeArray) t).setLenghth(ei.length);
				inits.add(null);
				types.add(t);
				StmtVarDecl ret = new StmtVarDecl(types, names, inits, ctx.start.getLine());
				return ret;
			}
		}

		StmtVarDecl ret = new StmtVarDecl(types, names, inits, ctx.start.getLine());
		return ret;

	}

	@Override
	public Statement visitLocalVariableDeclarationStatement(
		simpleJavaParser.LocalVariableDeclarationStatementContext ctx
	) {
		return (Statement) visit(ctx.localVariableDeclaration());
	}

	@Override
	public Statement visitLocalVariableDeclaration(
		simpleJavaParser.LocalVariableDeclarationContext ctx
	) {
		Type t = (Type) visit(ctx.unannType());
		ArrayList<Type> types = new java.util.ArrayList<Type>();
		ArrayList<String> names = new java.util.ArrayList<String>();
		ArrayList<Expression> inits = new java.util.ArrayList<Expression>();
		for (int i = 0; i < ctx.variableDeclaratorList().variableDeclarator().size(); i++) {
			types.add(t);
			// TODO dims
			names.add(
				ctx.variableDeclaratorList()
					.variableDeclarator()
					.get(i)
					.variableDeclaratorId()
					.Identifier()
					.getText()
			);
			// TODO check what if variableInitializer dosen't exist
			inits.add(
				(Expression) visit(
					ctx.variableDeclaratorList().variableDeclarator().get(i).variableInitializer()
				)
			);
		}

		return new StmtVarDecl(types, names, inits, ctx.start.getLine());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Expression visitVarInitArray(simpleJavaParser.VarInitArrayContext ctx) {
		@SuppressWarnings("rawtypes")
		List list = new ArrayList<Expression>();
		for (
			int i = 0;
			i < ctx.arrayInitializer().variableInitializerList().variableInitializer().size();
			i++
		) {
			list.add(
				visit(ctx.arrayInitializer().variableInitializerList().variableInitializer(i))
			);
		}
		return new ExprArrayInit(list);
	}

	@Override
	public SketchObject visitSingleStatement(simpleJavaParser.SingleStatementContext ctx) {
		return visit(ctx.statement());
	}

	@Override
	public SketchObject visitStatement(simpleJavaParser.StatementContext ctx) {
		return visit(ctx.getChild(0));
	}

	@Override
	public SketchObject visitStatementWithoutTrailingSubstatement(
		simpleJavaParser.StatementWithoutTrailingSubstatementContext ctx
	) {
		return visit(ctx.getChild(0));
	}

	/** 'do' statement 'while' '(' expression ')' ';' **/
	@Override
	public SketchObject visitDoStatement(simpleJavaParser.DoStatementContext ctx) {
		return new StmtDoWhile(
			(Statement) visit(ctx.statement()),
			(Expression) visit(ctx.expression()),
			ctx.start.getLine()
		);
	}

	/** 'return' expression? ';' **/
	@Override
	public SketchObject visitReturnStatement(simpleJavaParser.ReturnStatementContext ctx) {
		return new StmtReturn((Expression) visit(ctx.expression()), ctx.start.getLine());
	}

	/** statementExpression (',' statementExpression)* **/
	@Override
	public Statement visitStatementExpressionList(
		simpleJavaParser.StatementExpressionListContext ctx
	) {
		List<Statement> list = new ArrayList<Statement>();
		for (int i = 0; i < ctx.statementExpression().size(); i++) {
			list.add(
				new StmtExpr((Expression) visit(ctx.statementExpression(i)), ctx.start.getLine())
			);
		}
		return new StmtBlock(list);
	}

	@Override
	public Statement visitExpressionStatement(simpleJavaParser.ExpressionStatementContext ctx) {
		// TODO convert expression and statement
		ParseTree tree = ctx.statementExpression();
		SketchObject sk = visit(tree);

		if (sk == null)
			return new StmtExpr((Expression) visit(ctx.statementExpression()), ctx.start.getLine());

		Class<? extends SketchObject> sk1 = sk.getClass();
		Class<? extends SketchObject> sk2 = StmtAssign.class;
		if (sk1.equals(sk2))
			return (Statement) visit(ctx.statementExpression());
		return new StmtExpr((Expression) visit(ctx.statementExpression()), ctx.start.getLine());
//
//		if (visit(tree).getClass().equals(StmtAssign.class))
//			return (Statement) visit(ctx.statementExpression());
//		return new StmtExpr((Expression) visit(ctx.statementExpression()), ctx.start.getLine());
	}

	@Override
	public SketchObject visitStatementExpression(simpleJavaParser.StatementExpressionContext ctx) {
		return visit(ctx.getChild(0));
	}

	/** '++' unaryExpression **/
	@Override
	public Expression visitPreIncrementExpression(
		simpleJavaParser.PreIncrementExpressionContext ctx
	) {
		return new ExprUnary(
			4,
			(Expression) visit(ctx.unaryExpression()),
			ctx.getStart().getLine()
		);
	}

	/** '--' unaryExpression **/
	@Override
	public Expression visitPreDecrementExpression(
		simpleJavaParser.PreDecrementExpressionContext ctx
	) {
		return new ExprUnary(
			6,
			(Expression) visit(ctx.unaryExpression()),
			ctx.getStart().getLine()
		);
	}

	/** unaryExpression '++' **/
	@Override
	public Expression visitPostIncrementExpression(
		simpleJavaParser.PostIncrementExpressionContext ctx
	) {
		return new ExprUnary(
			5,
			(Expression) visit(ctx.postfixExpression()),
			ctx.getStart().getLine()
		);
	}

	/** unaryExpression '--' **/
	@Override
	public Expression visitPostDecrementExpression(
		simpleJavaParser.PostDecrementExpressionContext ctx
	) {
		return new ExprUnary(
			7,
			(Expression) visit(ctx.postfixExpression()),
			ctx.getStart().getLine()
		);
	}

	@Override
	public SketchObject visitPreOp(simpleJavaParser.PreOpContext ctx) {
		return visit(ctx.getChild(0));
	}

	@Override
	public SketchObject visitNotPlusMinus(simpleJavaParser.NotPlusMinusContext ctx) {
		return visit(ctx.unaryExpressionNotPlusMinus());
	}

	@Override
	public SketchObject visitExpandNotPlusMinus(simpleJavaParser.ExpandNotPlusMinusContext ctx) {
		return visit(ctx.getChild(0));
	}

	@Override
	public SketchObject visitExpandNotPlusMinusBNot(
		simpleJavaParser.ExpandNotPlusMinusBNotContext ctx
	) {
		return new ExprUnary(
			2,
			(Expression) visit(ctx.unaryExpression()),
			ctx.getStart().getLine()
		);
	}

	@Override
	public SketchObject visitExpandNotPlusMinusNot(
		simpleJavaParser.ExpandNotPlusMinusNotContext ctx
	) {
		return new ExprUnary(
			1,
			(Expression) visit(ctx.unaryExpression()),
			ctx.getStart().getLine()
		);
	}

	/**
	 * ( primary | expressionName ) ( postIncrementExpression_lf_postfixExpression |
	 * postDecrementExpression_lf_postfixExpression )*
	 **/
	@Override
	public SketchObject visitPostfixExpression(simpleJavaParser.PostfixExpressionContext ctx) {
		Expression name = null;
		if (ctx.getChild(0).getClass().equals(simpleJavaParser.PrimaryContext.class))
			name = (Expression) visit(ctx.primary());
		if (ctx.getChild(0).getClass().equals(simpleJavaParser.ExpressionNameContext.class))
			name = (Expression) visit(ctx.expressionName());
		// TODO what if double ++
		if (ctx.postIncrementExpression_lf_postfixExpression().size() != 0)
			return new ExprUnary(5, name, ctx.getStart().getLine());
		if (ctx.postDecrementExpression_lf_postfixExpression().size() != 0)
			return new ExprUnary(7, name, ctx.getStart().getLine());
		return name;
	}

	@Override
	public SketchObject visitExpressionName(simpleJavaParser.ExpressionNameContext ctx) {
		if (ctx.getChild(0).getClass().equals(simpleJavaParser.AmbiguousNameContext.class)) {
			return new ExprField(
				(Expression) visit(ctx.ambiguousName()),
				ctx.Identifier().getText()
			);
		}
		return new ExprVar(ctx.Identifier().getText());
	}

	@Override
	public SketchObject visitAmbiguousName(simpleJavaParser.AmbiguousNameContext ctx) {
		if (ctx.getChild(0).getClass().equals(simpleJavaParser.AmbiguousNameContext.class)) {
			return new ExprField(
				(Expression) visit(ctx.ambiguousName()),
				ctx.Identifier().getText()
			);
		}
		return new ExprVar(ctx.Identifier().getText());
	}

	/** leftHandSide assignmentOperator expression **/
	@Override
	public SketchObject visitAssignment(simpleJavaParser.AssignmentContext ctx) {
		int op = 0;
		String aop = ctx.assignmentOperator().getText();
		if (aop.equals("*="))
			op = ExprBinary2.BINOP_MUL;
		if (aop.equals("/="))
			op = ExprBinary2.BINOP_DIV;
		if (aop.equals("+="))
			op = ExprBinary2.BINOP_ADD;
		if (aop.equals("-="))
			op = ExprBinary2.BINOP_SUB;
		return new StmtAssign(
			(Expression) visit(ctx.leftHandSide()),
			(Expression) visit(ctx.expression()),
			op,
			ctx.start.getLine()
		);
	}

	@Override
	public SketchObject visitLeftHandSide(simpleJavaParser.LeftHandSideContext ctx) {
		return visit(ctx.getChild(0));
	}

	/**
	 * ( expressionName '[' expression ']' | primaryNoNewArray_lfno_arrayAccess '[' expression ']' )
	 * ( primaryNoNewArray_lf_arrayAccess '[' expression ']' )*
	 **/
	@Override
	public SketchObject visitArrayAccess(simpleJavaParser.ArrayAccessContext ctx) {
		// TODO bad style NOW
		RangeLen r = new RangeLen((Expression) visit(ctx.expression(0)));
		return new ExprArrayRange(
			(Expression) visit(ctx.expressionName()),
			r,
			ctx.getStart().getLine()
		);
	}

	@Override
	public SketchObject visitPrimary(simpleJavaParser.PrimaryContext ctx) {
		// TODO bad style
		return visit(ctx.getChild(0));
	}

	@Override
	public SketchObject visitPrimaryLiteral(simpleJavaParser.PrimaryLiteralContext ctx) {
		return visit(ctx.literal());
	}

	@Override
	public SketchObject visitListeral_unused(simpleJavaParser.Listeral_unusedContext ctx) {

		if (ctx.getText().equals("true"))
			return new ExprConstInt(1);
		if (ctx.getText().equals("false"))
			return new ExprConstInt(0);
		return new ExprConstChar(ctx.getText());
	}

	@Override
	public SketchObject visitPrimaryNoNewArray_lfno_primary(
		simpleJavaParser.PrimaryNoNewArray_lfno_primaryContext ctx
	) {
		if (ctx.getChild(0).getText().equals("("))
			return visit(ctx.getChild(1));
		return visit(ctx.getChild(0));
	}

	@Override
	public SketchObject visitArrayAccess_lfno_primary(
		simpleJavaParser.ArrayAccess_lfno_primaryContext ctx
	) {
		RangeLen r = new RangeLen((Expression) visit(ctx.expression(0)));
		return new ExprArrayRange(
			(Expression) visit(ctx.expressionName()),
			r,
			ctx.getStart().getLine()
		);
	}

	@Override
	public SketchObject visitLiteralInt(simpleJavaParser.LiteralIntContext ctx) {
		return new ExprConstInt(ctx.IntegerLiteral().getText());
	}

	// ---------- control flow ----------

	/** 'for' '(' forInit? ';' expression? ';' forUpdate? ')' statement **/
	@Override
	public Statement visitForStatement(simpleJavaParser.ForStatementContext ctx) {
		simpleJavaParser.BasicForStatementContext c = ctx.basicForStatement();
		return new StmtFor(
			(Statement) visit(c.forInit()),
			(Expression) visit(c.expression()),
			(Statement) visit(c.forUpdate()),
			(Statement) visit(c.statement()),
			false,
			ctx.start.getLine()
		);
	}

	@Override
	public Statement visitForInit(simpleJavaParser.ForInitContext ctx) {
		return (Statement) visit(ctx.getChild(0));
	}

	/** 'while' '(' expression ')' statement **/
	@Override
	public Statement visitWhileStatement(simpleJavaParser.WhileStatementContext ctx) {
		return new StmtWhile(
			(Expression) visit(ctx.expression()),
			(Statement) visit(ctx.statement()),
			ctx.start.getLine()
		);
	}

	/** 'if' '(' expression ')' statementNoShortIf 'else' statement **/
	@Override
	public Statement visitIfThenElseStatement(simpleJavaParser.IfThenElseStatementContext ctx) {
		return new StmtIfThen(
			(Expression) visit(ctx.expression()),
			(Statement) visit(ctx.statementNoShortIf()),
			(Statement) visit(ctx.statement()),
			ctx.start.getLine()
		);
	}

	/** 'if' '(' expression ')' statement **/
	@Override
	public Statement visitIfThenStatement(simpleJavaParser.IfThenStatementContext ctx) {
		return new StmtIfThen(
			(Expression) visit(ctx.expression()),
			(Statement) visit(ctx.statement()),
			null,
			ctx.start.getLine()
		);
	}

	@Override
	public Statement visitStatementNoShortIf(simpleJavaParser.StatementNoShortIfContext ctx) {
		return (Statement) visit(ctx.getChild(0));
	}

	// ---------- expression ----------
	@Override
	public Expression visitExpression(simpleJavaParser.ExpressionContext ctx) {
		return (Expression) visit(ctx.assignmentExpression());
	}

	/** conditionalOrExpression '||' conditionalAndExpression **/
	@Override
	public Expression visitExpandConditionalOrExpr(
		simpleJavaParser.ExpandConditionalOrExprContext ctx
	) {
		Expression left = (Expression) this.visit(ctx.conditionalOrExpression());
		Expression right = (Expression) this.visit(ctx.conditionalAndExpression());
		ExprBinaryOptions options
			= new ExprBinaryOptions().setLeft(left)
				.setRight(right)
				.setLineNumber(ctx.start.getLine());
		return this.binaryExprFactory.getExprBinary(Or.class, options);
	}

	/** conditionalAndExpression '&&' inclusiveOrExpression **/
	@Override
	public Expression visitExpandConditionalAndExpr(
		simpleJavaParser.ExpandConditionalAndExprContext ctx
	) {
		Expression left = (Expression) visit(ctx.conditionalAndExpression());
		Expression right = (Expression) visit(ctx.inclusiveOrExpression());
		ExprBinaryOptions options
			= new ExprBinaryOptions().setLeft(left)
				.setRight(right)
				.setLineNumber(ctx.start.getLine());
		return this.binaryExprFactory.getExprBinary(And.class, options);
	}

	/** inclusiveOrExpression '|' exclusiveOrExpression **/
	@Override
	public Expression visitExpandInclusiveOrExpr(
		simpleJavaParser.ExpandInclusiveOrExprContext ctx
	) {
		Expression left = (Expression) visit(ctx.inclusiveOrExpression());
		Expression right = (Expression) visit(ctx.exclusiveOrExpression());
		ExprBinaryOptions options
			= new ExprBinaryOptions().setLeft(left)
				.setRight(right)
				.setLineNumber(ctx.start.getLine());
		return this.binaryExprFactory.getExprBinary(BitwiseOr.class, options);
	}

	/** exclusiveOrExpression '^' andExpression **/
	@Override
	public Expression visitExpandExclusiveOrExpr(
		simpleJavaParser.ExpandExclusiveOrExprContext ctx
	) {
		Expression left = (Expression) visit(ctx.exclusiveOrExpression());
		Expression right = (Expression) visit(ctx.andExpression());
		ExprBinaryOptions options
			= new ExprBinaryOptions().setLeft(left)
				.setRight(right)
				.setLineNumber(ctx.start.getLine());
		return this.binaryExprFactory.getExprBinary(Xor.class, options);
	}

	/** andExpression '&' equalityExpression **/
	@Override
	public Expression visitExpandAndExpr(simpleJavaParser.ExpandAndExprContext ctx) {

		Expression left = (Expression) visit(ctx.andExpression());
		Expression right = (Expression) visit(ctx.equalityExpression());
		ExprBinaryOptions options
			= new ExprBinaryOptions().setLeft(left)
				.setRight(right)
				.setLineNumber(ctx.start.getLine());
		return this.binaryExprFactory.getExprBinary(BitwiseAnd.class, options);
	}

	/** relationalExpression '<' shiftExpression **/
	@Override
	public Expression visitExpandRelationalExpr(simpleJavaParser.ExpandRelationalExprContext ctx) {
		int childLeftIdx = 0;
		int childOpIdx = 1;
		int childRightIdx = 2;
		Expression left = (Expression) visit(ctx.getChild(childLeftIdx));
		Expression right = (Expression) visit(ctx.getChild(childRightIdx));
		GetExprBinaryOptions options
			= new GetExprBinaryOptions().setLeft(left)
				.setOperator(ctx.getChild(childOpIdx).getText())
				.setRight(right)
				.setLineNumber(ctx.start.getLine());
		return this.binaryExprFactory.getExprBinary(options);
	}

	/**
	 * equalityExpression '==' relationalExpression # expandEqExpr | equalityExpression '!='
	 * relationalExpression # expandEqExpr
	 **/
	@Override
	public Expression visitExpandEqExpr(simpleJavaParser.ExpandEqExprContext ctx) {

		Expression left = (Expression) visit(ctx.getChild(0));
		Expression right = (Expression) visit(ctx.getChild(2));
		GetExprBinaryOptions options
			= new GetExprBinaryOptions().setLeft(left)
				.setOperator(ctx.getChild(1).getText())
				.setRight(right)
				.setLineNumber(ctx.getStart().getLine());
		return this.binaryExprFactory.getExprBinary(options);
	}

	@Override
	public Expression visitExpandShiftLeft(simpleJavaParser.ExpandShiftLeftContext ctx) {

		Expression left = (Expression) visit(ctx.shiftExpression());
		Expression right = (Expression) visit(ctx.additiveExpression());

		ExprBinaryOptions options
			= new ExprBinaryOptions().setLeft(left)
				.setRight(right)
				.setLineNumber(ctx.start.getLine());
		return this.binaryExprFactory.getExprBinary(LeftShift.class, options);
	}

	@Override
	public Expression visitExpandShiftRight(simpleJavaParser.ExpandShiftRightContext ctx) {
		Expression left = (Expression) visit(ctx.shiftExpression());
		Expression right = (Expression) visit(ctx.additiveExpression());

		ExprBinaryOptions options
			= new ExprBinaryOptions().setLeft(left)
				.setRight(right)
				.setLineNumber(ctx.start.getLine());
		return this.binaryExprFactory.getExprBinary(RightShift.class, options);
	}

	@Override
	public Expression visitExpandAdditiveExpr(simpleJavaParser.ExpandAdditiveExprContext ctx) {
		Expression left = (Expression) visit(ctx.getChild(0));
		Expression right = (Expression) visit(ctx.getChild(2));
		GetExprBinaryOptions options
			= new GetExprBinaryOptions().setLeft(left)
				.setOperator(ctx.getChild(1).getText())
				.setRight(right)
				.setLineNumber(ctx.getStart().getLine());
		return this.binaryExprFactory.getExprBinary(options);
	}

	@Override
	public Expression visitExpandMulExpr(simpleJavaParser.ExpandMulExprContext ctx) {
		Expression left = (Expression) visit(ctx.getChild(0));
		Expression right = (Expression) visit(ctx.getChild(2));
		GetExprBinaryOptions options
			= new GetExprBinaryOptions().setLeft(left)
				.setOperator(ctx.getChild(1).getText())
				.setRight(right)
				.setLineNumber(ctx.getStart().getLine());
		return this.binaryExprFactory.getExprBinary(options);
	}

	@Override
	public Expression visitMethodInvocation_lfno_primary(
		simpleJavaParser.MethodInvocation_lfno_primaryContext ctx
	) {
		String methodName = "";
		String methodNameJ = "";
		for (int i = 0; i < ctx.getChildCount(); i++) {
			String tmp = ctx.getChild(i).getText();
			if (tmp.equals("("))
				break;
			methodNameJ += tmp;
			if (tmp.equals("."))
				continue;
			methodName += tmp;
		}
		ParseTree tree = ctx.argumentList();
		ExpressionList temp;

		// added 11/18
		methodNames.add(methodName);
		// added 11/18

		if (tree != null) {
			temp = (ExpressionList) visit(tree);
		} else {
			// temp = new ExpressionList(new ArrayList<>());
			return new ExprFuncCall("External_" + methodName);
		}

		return new ExprFuncCall("External_" + methodName, temp, methodNameJ);
	}

	@Override
	public ExpressionList visitArgumentList(simpleJavaParser.ArgumentListContext ctx) {
		List<Expression> l = new ArrayList<Expression>();
		for (int i = 0; i < ctx.expression().size(); i++) {
			l.add((Expression) visit(ctx.expression(i)));
		}
		return new ExpressionList(l);
	}

	/*
	 * arrayCreationExpression : 'new' primitiveType dimExprs dims? | 'new' classOrInterfaceType
	 * dimExprs dims? | 'new' primitiveType dims arrayInitializer | 'new' classOrInterfaceType dims
	 * arrayInitializer ;
	 */
	@Override
	public SketchObject visitArrayCreationExpression(
		simpleJavaParser.ArrayCreationExpressionContext ctx
	) {
		if (ctx.getText().substring(ctx.getText().length() - 1).equals("}")) {
			List<Expression> el = new ArrayList<Expression>();
			for (
				simpleJavaParser.VariableInitializerContext cc : ctx.arrayInitializer()
					.variableInitializerList()
					.variableInitializer()
			) {
				el.add((Expression) visit(cc));
			}
			return new ExprArrayInit(el);
		}
		if (ctx.getText().substring(ctx.getText().length() - 1).equals("]")) {
			return new ExprArrayInit((Expression) visit(ctx.dimExprs()), 0);
		}
		return null;
	}

	@Override
	public Expression visitDimExpr(simpleJavaParser.DimExprContext ctx) {
		return (Expression) visit(ctx.expression());
	}

	// //TODO :
	//
	// TODO expression
	// | switchStatement

	// NOT SUPPORT YET
	// lambdaExpression
	// | labeledStatementNoShortIf
	// | ifThenElseStatementNoShortIf
	// | whileStatementNoShortIf
	// | forStatementNoShortIf
	// | conditionalExpression

	// forUpdate no need to write?
	// | breakStatement not support
	// | continueStatement not support
	// | methodInvocation unused
	// | classInstanceCreationExpression unused
}
