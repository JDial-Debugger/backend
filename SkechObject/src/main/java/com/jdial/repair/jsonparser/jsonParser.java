// Generated from JDial-debugger/SkechObject/src/jsonparser/json.g4 by ANTLR 4.5.1
package jsonparser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class jsonParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, COLON=28, COMMA=29, BOOLEAN=30, STRING=31, 
		ESC=32, NUMBER=33, WS=34;
	public static final int
		RULE_json = 0, RULE_userlog = 1, RULE_code = 2, RULE_stdin = 3, RULE_traces = 4, 
		RULE_trace = 5, RULE_stdout = 6, RULE_event = 7, RULE_line = 8, RULE_assertions = 9, 
		RULE_assertionList = 10, RULE_globals = 11, RULE_varnames = 12, RULE_ordered_globals = 13, 
		RULE_func_name = 14, RULE_heap = 15, RULE_varlist = 16, RULE_var = 17, 
		RULE_value = 18, RULE_heap_content = 19, RULE_heap_object = 20, RULE_list = 21, 
		RULE_stack_to_render = 22, RULE_stack = 23, RULE_frame = 24, RULE_encoded_locals = 25, 
		RULE_ordered_varnames = 26, RULE_parent_frame_id_list = 27, RULE_is_highlighted = 28, 
		RULE_is_zombie = 29, RULE_is_parent = 30, RULE_unique_hash = 31, RULE_frame_id = 32;
	public static final String[] ruleNames = {
		"json", "userlog", "code", "stdin", "traces", "trace", "stdout", "event", 
		"line", "assertions", "assertionList", "globals", "varnames", "ordered_globals", 
		"func_name", "heap", "varlist", "var", "value", "heap_content", "heap_object", 
		"list", "stack_to_render", "stack", "frame", "encoded_locals", "ordered_varnames", 
		"parent_frame_id_list", "is_highlighted", "is_zombie", "is_parent", "unique_hash", 
		"frame_id"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'{'", "'\"code\"'", "'\"stdin\"'", "'\"trace\"'", "'}'", "'['", 
		"']'", "'\"stdout\"'", "'\"event\"'", "'\"line\"'", "'\"assertions\"'", 
		"'\"globals\"'", "'\"ordered_globals\"'", "'\"func_name\"'", "'\"heap\"'", 
		"'\"REF\"'", "'\"VOID\"'", "'\"LIST\"'", "'\"stack_to_render\"'", "'\"encoded_locals\"'", 
		"'\"ordered_varnames\"'", "'\"parent_frame_id_list\"'", "'\"is_highlighted\"'", 
		"'\"is_zombie\"'", "'\"is_parent\"'", "'\"unique_hash\"'", "'\"frame_id\"'", 
		"':'", "','"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, "COLON", "COMMA", "BOOLEAN", "STRING", "ESC", 
		"NUMBER", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "json.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public jsonParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class JsonContext extends ParserRuleContext {
		public List<TerminalNode> COLON() { return getTokens(jsonParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(jsonParser.COLON, i);
		}
		public CodeContext code() {
			return getRuleContext(CodeContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(jsonParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(jsonParser.COMMA, i);
		}
		public StdinContext stdin() {
			return getRuleContext(StdinContext.class,0);
		}
		public TracesContext traces() {
			return getRuleContext(TracesContext.class,0);
		}
		public JsonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_json; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitJson(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonContext json() throws RecognitionException {
		JsonContext _localctx = new JsonContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_json);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(T__0);
			setState(67);
			match(T__1);
			setState(68);
			match(COLON);
			setState(69);
			code();
			setState(70);
			match(COMMA);
			setState(71);
			match(T__2);
			setState(72);
			match(COLON);
			setState(73);
			stdin();
			setState(74);
			match(COMMA);
			setState(75);
			match(T__3);
			setState(76);
			match(COLON);
			setState(77);
			traces();
			setState(78);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UserlogContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(jsonParser.STRING, 0); }
		public UserlogContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_userlog; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitUserlog(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UserlogContext userlog() throws RecognitionException {
		UserlogContext _localctx = new UserlogContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_userlog);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CodeContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(jsonParser.STRING, 0); }
		public CodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_code; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitCode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CodeContext code() throws RecognitionException {
		CodeContext _localctx = new CodeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_code);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StdinContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(jsonParser.STRING, 0); }
		public StdinContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stdin; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitStdin(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StdinContext stdin() throws RecognitionException {
		StdinContext _localctx = new StdinContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_stdin);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TracesContext extends ParserRuleContext {
		public List<TraceContext> trace() {
			return getRuleContexts(TraceContext.class);
		}
		public TraceContext trace(int i) {
			return getRuleContext(TraceContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(jsonParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(jsonParser.COMMA, i);
		}
		public TracesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_traces; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitTraces(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TracesContext traces() throws RecognitionException {
		TracesContext _localctx = new TracesContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_traces);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			match(T__5);
			setState(92);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(87);
					trace();
					setState(88);
					match(COMMA);
					}
					} 
				}
				setState(94);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			setState(95);
			trace();
			setState(96);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TraceContext extends ParserRuleContext {
		public StdoutContext stdout() {
			return getRuleContext(StdoutContext.class,0);
		}
		public EventContext event() {
			return getRuleContext(EventContext.class,0);
		}
		public LineContext line() {
			return getRuleContext(LineContext.class,0);
		}
		public Stack_to_renderContext stack_to_render() {
			return getRuleContext(Stack_to_renderContext.class,0);
		}
		public GlobalsContext globals() {
			return getRuleContext(GlobalsContext.class,0);
		}
		public Ordered_globalsContext ordered_globals() {
			return getRuleContext(Ordered_globalsContext.class,0);
		}
		public Func_nameContext func_name() {
			return getRuleContext(Func_nameContext.class,0);
		}
		public HeapContext heap() {
			return getRuleContext(HeapContext.class,0);
		}
		public AssertionsContext assertions() {
			return getRuleContext(AssertionsContext.class,0);
		}
		public TraceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trace; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitTrace(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TraceContext trace() throws RecognitionException {
		TraceContext _localctx = new TraceContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_trace);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(T__0);
			setState(99);
			stdout();
			setState(100);
			event();
			setState(101);
			line();
			setState(102);
			stack_to_render();
			setState(103);
			globals();
			setState(104);
			ordered_globals();
			setState(105);
			func_name();
			setState(106);
			heap();
			setState(108);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(107);
				assertions();
				}
			}

			setState(110);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StdoutContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public TerminalNode STRING() { return getToken(jsonParser.STRING, 0); }
		public TerminalNode COMMA() { return getToken(jsonParser.COMMA, 0); }
		public StdoutContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stdout; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitStdout(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StdoutContext stdout() throws RecognitionException {
		StdoutContext _localctx = new StdoutContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_stdout);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			match(T__7);
			setState(113);
			match(COLON);
			setState(114);
			match(STRING);
			setState(115);
			match(COMMA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EventContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public TerminalNode STRING() { return getToken(jsonParser.STRING, 0); }
		public TerminalNode COMMA() { return getToken(jsonParser.COMMA, 0); }
		public EventContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_event; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitEvent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EventContext event() throws RecognitionException {
		EventContext _localctx = new EventContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_event);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			match(T__8);
			setState(118);
			match(COLON);
			setState(119);
			match(STRING);
			setState(120);
			match(COMMA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LineContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public TerminalNode NUMBER() { return getToken(jsonParser.NUMBER, 0); }
		public TerminalNode COMMA() { return getToken(jsonParser.COMMA, 0); }
		public LineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_line; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitLine(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LineContext line() throws RecognitionException {
		LineContext _localctx = new LineContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_line);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			match(T__9);
			setState(123);
			match(COLON);
			setState(124);
			match(NUMBER);
			setState(125);
			match(COMMA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssertionsContext extends ParserRuleContext {
		public TerminalNode COMMA() { return getToken(jsonParser.COMMA, 0); }
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public AssertionListContext assertionList() {
			return getRuleContext(AssertionListContext.class,0);
		}
		public AssertionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assertions; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitAssertions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssertionsContext assertions() throws RecognitionException {
		AssertionsContext _localctx = new AssertionsContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_assertions);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			match(COMMA);
			setState(128);
			match(T__10);
			setState(129);
			match(COLON);
			setState(130);
			match(T__5);
			setState(131);
			assertionList();
			setState(132);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssertionListContext extends ParserRuleContext {
		public List<TerminalNode> STRING() { return getTokens(jsonParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(jsonParser.STRING, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(jsonParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(jsonParser.COMMA, i);
		}
		public AssertionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assertionList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitAssertionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssertionListContext assertionList() throws RecognitionException {
		AssertionListContext _localctx = new AssertionListContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_assertionList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(134);
					match(STRING);
					setState(135);
					match(COMMA);
					}
					} 
				}
				setState(140);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			setState(142);
			_la = _input.LA(1);
			if (_la==STRING) {
				{
				setState(141);
				match(STRING);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GlobalsContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public VarlistContext varlist() {
			return getRuleContext(VarlistContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(jsonParser.COMMA, 0); }
		public GlobalsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globals; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitGlobals(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalsContext globals() throws RecognitionException {
		GlobalsContext _localctx = new GlobalsContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_globals);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			match(T__11);
			setState(145);
			match(COLON);
			setState(146);
			match(T__0);
			setState(147);
			varlist();
			setState(148);
			match(T__4);
			setState(149);
			match(COMMA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarnamesContext extends ParserRuleContext {
		public List<TerminalNode> STRING() { return getTokens(jsonParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(jsonParser.STRING, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(jsonParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(jsonParser.COMMA, i);
		}
		public VarnamesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varnames; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitVarnames(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarnamesContext varnames() throws RecognitionException {
		VarnamesContext _localctx = new VarnamesContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_varnames);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(151);
					match(STRING);
					setState(152);
					match(COMMA);
					}
					} 
				}
				setState(157);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			setState(159);
			_la = _input.LA(1);
			if (_la==STRING) {
				{
				setState(158);
				match(STRING);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ordered_globalsContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public VarnamesContext varnames() {
			return getRuleContext(VarnamesContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(jsonParser.COMMA, 0); }
		public Ordered_globalsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ordered_globals; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitOrdered_globals(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Ordered_globalsContext ordered_globals() throws RecognitionException {
		Ordered_globalsContext _localctx = new Ordered_globalsContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_ordered_globals);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			match(T__12);
			setState(162);
			match(COLON);
			setState(163);
			match(T__5);
			setState(164);
			varnames();
			setState(165);
			match(T__6);
			setState(166);
			match(COMMA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Func_nameContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public TerminalNode STRING() { return getToken(jsonParser.STRING, 0); }
		public TerminalNode COMMA() { return getToken(jsonParser.COMMA, 0); }
		public Func_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_name; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitFunc_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Func_nameContext func_name() throws RecognitionException {
		Func_nameContext _localctx = new Func_nameContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_func_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			match(T__13);
			setState(169);
			match(COLON);
			setState(170);
			match(STRING);
			setState(171);
			match(COMMA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HeapContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public Heap_contentContext heap_content() {
			return getRuleContext(Heap_contentContext.class,0);
		}
		public HeapContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_heap; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitHeap(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HeapContext heap() throws RecognitionException {
		HeapContext _localctx = new HeapContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_heap);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			match(T__14);
			setState(174);
			match(COLON);
			setState(175);
			match(T__0);
			setState(176);
			heap_content();
			setState(177);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarlistContext extends ParserRuleContext {
		public List<VarContext> var() {
			return getRuleContexts(VarContext.class);
		}
		public VarContext var(int i) {
			return getRuleContext(VarContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(jsonParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(jsonParser.COMMA, i);
		}
		public VarlistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varlist; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitVarlist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarlistContext varlist() throws RecognitionException {
		VarlistContext _localctx = new VarlistContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_varlist);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(184);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(179);
					var();
					setState(180);
					match(COMMA);
					}
					} 
				}
				setState(186);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			setState(188);
			_la = _input.LA(1);
			if (_la==STRING) {
				{
				setState(187);
				var();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(jsonParser.STRING, 0); }
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public VarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_var);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			match(STRING);
			setState(191);
			match(COLON);
			setState(192);
			value();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(jsonParser.NUMBER, 0); }
		public TerminalNode COMMA() { return getToken(jsonParser.COMMA, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_value);
		try {
			setState(203);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(194);
				match(NUMBER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(195);
				match(T__5);
				setState(196);
				match(T__15);
				setState(197);
				match(COMMA);
				setState(198);
				match(NUMBER);
				setState(199);
				match(T__6);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(200);
				match(T__5);
				setState(201);
				match(T__16);
				setState(202);
				match(T__6);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Heap_contentContext extends ParserRuleContext {
		public List<Heap_objectContext> heap_object() {
			return getRuleContexts(Heap_objectContext.class);
		}
		public Heap_objectContext heap_object(int i) {
			return getRuleContext(Heap_objectContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(jsonParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(jsonParser.COMMA, i);
		}
		public Heap_contentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_heap_content; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitHeap_content(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Heap_contentContext heap_content() throws RecognitionException {
		Heap_contentContext _localctx = new Heap_contentContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_heap_content);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(205);
					heap_object();
					setState(206);
					match(COMMA);
					}
					} 
				}
				setState(212);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			setState(214);
			_la = _input.LA(1);
			if (_la==STRING) {
				{
				setState(213);
				heap_object();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Heap_objectContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(jsonParser.STRING, 0); }
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public TerminalNode COMMA() { return getToken(jsonParser.COMMA, 0); }
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public Heap_objectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_heap_object; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitHeap_object(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Heap_objectContext heap_object() throws RecognitionException {
		Heap_objectContext _localctx = new Heap_objectContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_heap_object);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			match(STRING);
			setState(217);
			match(COLON);
			setState(218);
			match(T__5);
			setState(219);
			match(T__17);
			setState(220);
			match(COMMA);
			setState(221);
			list();
			setState(222);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListContext extends ParserRuleContext {
		public List<TerminalNode> NUMBER() { return getTokens(jsonParser.NUMBER); }
		public TerminalNode NUMBER(int i) {
			return getToken(jsonParser.NUMBER, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(jsonParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(jsonParser.COMMA, i);
		}
		public ListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListContext list() throws RecognitionException {
		ListContext _localctx = new ListContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_list);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(228);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(224);
					match(NUMBER);
					setState(225);
					match(COMMA);
					}
					} 
				}
				setState(230);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			setState(231);
			match(NUMBER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Stack_to_renderContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public StackContext stack() {
			return getRuleContext(StackContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(jsonParser.COMMA, 0); }
		public Stack_to_renderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stack_to_render; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitStack_to_render(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Stack_to_renderContext stack_to_render() throws RecognitionException {
		Stack_to_renderContext _localctx = new Stack_to_renderContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_stack_to_render);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			match(T__18);
			setState(234);
			match(COLON);
			setState(235);
			match(T__5);
			setState(236);
			stack();
			setState(237);
			match(T__6);
			setState(238);
			match(COMMA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StackContext extends ParserRuleContext {
		public List<FrameContext> frame() {
			return getRuleContexts(FrameContext.class);
		}
		public FrameContext frame(int i) {
			return getRuleContext(FrameContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(jsonParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(jsonParser.COMMA, i);
		}
		public StackContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stack; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitStack(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StackContext stack() throws RecognitionException {
		StackContext _localctx = new StackContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_stack);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(240);
					match(T__0);
					setState(241);
					frame();
					setState(242);
					match(T__4);
					setState(243);
					match(COMMA);
					}
					} 
				}
				setState(249);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			setState(250);
			match(T__0);
			setState(251);
			frame();
			setState(252);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FrameContext extends ParserRuleContext {
		public Func_nameContext func_name() {
			return getRuleContext(Func_nameContext.class,0);
		}
		public Encoded_localsContext encoded_locals() {
			return getRuleContext(Encoded_localsContext.class,0);
		}
		public Ordered_varnamesContext ordered_varnames() {
			return getRuleContext(Ordered_varnamesContext.class,0);
		}
		public Parent_frame_id_listContext parent_frame_id_list() {
			return getRuleContext(Parent_frame_id_listContext.class,0);
		}
		public Is_highlightedContext is_highlighted() {
			return getRuleContext(Is_highlightedContext.class,0);
		}
		public Is_zombieContext is_zombie() {
			return getRuleContext(Is_zombieContext.class,0);
		}
		public Is_parentContext is_parent() {
			return getRuleContext(Is_parentContext.class,0);
		}
		public Unique_hashContext unique_hash() {
			return getRuleContext(Unique_hashContext.class,0);
		}
		public Frame_idContext frame_id() {
			return getRuleContext(Frame_idContext.class,0);
		}
		public FrameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_frame; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitFrame(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FrameContext frame() throws RecognitionException {
		FrameContext _localctx = new FrameContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_frame);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			func_name();
			setState(255);
			encoded_locals();
			setState(256);
			ordered_varnames();
			setState(257);
			parent_frame_id_list();
			setState(258);
			is_highlighted();
			setState(259);
			is_zombie();
			setState(260);
			is_parent();
			setState(261);
			unique_hash();
			setState(262);
			frame_id();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Encoded_localsContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public VarlistContext varlist() {
			return getRuleContext(VarlistContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(jsonParser.COMMA, 0); }
		public Encoded_localsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_encoded_locals; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitEncoded_locals(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Encoded_localsContext encoded_locals() throws RecognitionException {
		Encoded_localsContext _localctx = new Encoded_localsContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_encoded_locals);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(264);
			match(T__19);
			setState(265);
			match(COLON);
			setState(266);
			match(T__0);
			setState(267);
			varlist();
			setState(268);
			match(T__4);
			setState(269);
			match(COMMA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ordered_varnamesContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public VarnamesContext varnames() {
			return getRuleContext(VarnamesContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(jsonParser.COMMA, 0); }
		public Ordered_varnamesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ordered_varnames; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitOrdered_varnames(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Ordered_varnamesContext ordered_varnames() throws RecognitionException {
		Ordered_varnamesContext _localctx = new Ordered_varnamesContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_ordered_varnames);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(271);
			match(T__20);
			setState(272);
			match(COLON);
			setState(273);
			match(T__5);
			setState(274);
			varnames();
			setState(275);
			match(T__6);
			setState(276);
			match(COMMA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Parent_frame_id_listContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public TerminalNode COMMA() { return getToken(jsonParser.COMMA, 0); }
		public Parent_frame_id_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parent_frame_id_list; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitParent_frame_id_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Parent_frame_id_listContext parent_frame_id_list() throws RecognitionException {
		Parent_frame_id_listContext _localctx = new Parent_frame_id_listContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_parent_frame_id_list);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(278);
			match(T__21);
			setState(279);
			match(COLON);
			setState(280);
			match(T__5);
			setState(281);
			match(T__6);
			setState(282);
			match(COMMA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Is_highlightedContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public TerminalNode BOOLEAN() { return getToken(jsonParser.BOOLEAN, 0); }
		public TerminalNode COMMA() { return getToken(jsonParser.COMMA, 0); }
		public Is_highlightedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_is_highlighted; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitIs_highlighted(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Is_highlightedContext is_highlighted() throws RecognitionException {
		Is_highlightedContext _localctx = new Is_highlightedContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_is_highlighted);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			match(T__22);
			setState(285);
			match(COLON);
			setState(286);
			match(BOOLEAN);
			setState(287);
			match(COMMA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Is_zombieContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public TerminalNode BOOLEAN() { return getToken(jsonParser.BOOLEAN, 0); }
		public TerminalNode COMMA() { return getToken(jsonParser.COMMA, 0); }
		public Is_zombieContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_is_zombie; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitIs_zombie(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Is_zombieContext is_zombie() throws RecognitionException {
		Is_zombieContext _localctx = new Is_zombieContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_is_zombie);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(289);
			match(T__23);
			setState(290);
			match(COLON);
			setState(291);
			match(BOOLEAN);
			setState(292);
			match(COMMA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Is_parentContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public TerminalNode BOOLEAN() { return getToken(jsonParser.BOOLEAN, 0); }
		public TerminalNode COMMA() { return getToken(jsonParser.COMMA, 0); }
		public Is_parentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_is_parent; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitIs_parent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Is_parentContext is_parent() throws RecognitionException {
		Is_parentContext _localctx = new Is_parentContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_is_parent);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(294);
			match(T__24);
			setState(295);
			match(COLON);
			setState(296);
			match(BOOLEAN);
			setState(297);
			match(COMMA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Unique_hashContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public TerminalNode STRING() { return getToken(jsonParser.STRING, 0); }
		public TerminalNode COMMA() { return getToken(jsonParser.COMMA, 0); }
		public Unique_hashContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unique_hash; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitUnique_hash(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Unique_hashContext unique_hash() throws RecognitionException {
		Unique_hashContext _localctx = new Unique_hashContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_unique_hash);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(299);
			match(T__25);
			setState(300);
			match(COLON);
			setState(301);
			match(STRING);
			setState(302);
			match(COMMA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Frame_idContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(jsonParser.COLON, 0); }
		public TerminalNode NUMBER() { return getToken(jsonParser.NUMBER, 0); }
		public Frame_idContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_frame_id; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof jsonVisitor ) return ((jsonVisitor<? extends T>)visitor).visitFrame_id(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Frame_idContext frame_id() throws RecognitionException {
		Frame_idContext _localctx = new Frame_idContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_frame_id);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(304);
			match(T__26);
			setState(305);
			match(COLON);
			setState(306);
			match(NUMBER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3$\u0137\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\7\6]\n\6\f\6\16\6`\13\6\3\6\3\6"+
		"\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7o\n\7\3\7\3\7\3\b\3\b"+
		"\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\f\3\f\7\f\u008b\n\f\f\f\16\f\u008e\13\f\3\f\5\f\u0091"+
		"\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\7\16\u009c\n\16\f\16\16\16"+
		"\u009f\13\16\3\16\5\16\u00a2\n\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\7"+
		"\22\u00b9\n\22\f\22\16\22\u00bc\13\22\3\22\5\22\u00bf\n\22\3\23\3\23\3"+
		"\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u00ce\n\24"+
		"\3\25\3\25\3\25\7\25\u00d3\n\25\f\25\16\25\u00d6\13\25\3\25\5\25\u00d9"+
		"\n\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\7\27\u00e5\n\27"+
		"\f\27\16\27\u00e8\13\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3"+
		"\31\3\31\3\31\3\31\3\31\7\31\u00f8\n\31\f\31\16\31\u00fb\13\31\3\31\3"+
		"\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\33\3"+
		"\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\35\3"+
		"\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3"+
		"\37\3 \3 \3 \3 \3 \3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\2\2#\2\4\6\b\n\f"+
		"\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@B\2\2\u0123\2D\3"+
		"\2\2\2\4R\3\2\2\2\6T\3\2\2\2\bV\3\2\2\2\nX\3\2\2\2\fd\3\2\2\2\16r\3\2"+
		"\2\2\20w\3\2\2\2\22|\3\2\2\2\24\u0081\3\2\2\2\26\u008c\3\2\2\2\30\u0092"+
		"\3\2\2\2\32\u009d\3\2\2\2\34\u00a3\3\2\2\2\36\u00aa\3\2\2\2 \u00af\3\2"+
		"\2\2\"\u00ba\3\2\2\2$\u00c0\3\2\2\2&\u00cd\3\2\2\2(\u00d4\3\2\2\2*\u00da"+
		"\3\2\2\2,\u00e6\3\2\2\2.\u00eb\3\2\2\2\60\u00f9\3\2\2\2\62\u0100\3\2\2"+
		"\2\64\u010a\3\2\2\2\66\u0111\3\2\2\28\u0118\3\2\2\2:\u011e\3\2\2\2<\u0123"+
		"\3\2\2\2>\u0128\3\2\2\2@\u012d\3\2\2\2B\u0132\3\2\2\2DE\7\3\2\2EF\7\4"+
		"\2\2FG\7\36\2\2GH\5\6\4\2HI\7\37\2\2IJ\7\5\2\2JK\7\36\2\2KL\5\b\5\2LM"+
		"\7\37\2\2MN\7\6\2\2NO\7\36\2\2OP\5\n\6\2PQ\7\7\2\2Q\3\3\2\2\2RS\7!\2\2"+
		"S\5\3\2\2\2TU\7!\2\2U\7\3\2\2\2VW\7!\2\2W\t\3\2\2\2X^\7\b\2\2YZ\5\f\7"+
		"\2Z[\7\37\2\2[]\3\2\2\2\\Y\3\2\2\2]`\3\2\2\2^\\\3\2\2\2^_\3\2\2\2_a\3"+
		"\2\2\2`^\3\2\2\2ab\5\f\7\2bc\7\t\2\2c\13\3\2\2\2de\7\3\2\2ef\5\16\b\2"+
		"fg\5\20\t\2gh\5\22\n\2hi\5.\30\2ij\5\30\r\2jk\5\34\17\2kl\5\36\20\2ln"+
		"\5 \21\2mo\5\24\13\2nm\3\2\2\2no\3\2\2\2op\3\2\2\2pq\7\7\2\2q\r\3\2\2"+
		"\2rs\7\n\2\2st\7\36\2\2tu\7!\2\2uv\7\37\2\2v\17\3\2\2\2wx\7\13\2\2xy\7"+
		"\36\2\2yz\7!\2\2z{\7\37\2\2{\21\3\2\2\2|}\7\f\2\2}~\7\36\2\2~\177\7#\2"+
		"\2\177\u0080\7\37\2\2\u0080\23\3\2\2\2\u0081\u0082\7\37\2\2\u0082\u0083"+
		"\7\r\2\2\u0083\u0084\7\36\2\2\u0084\u0085\7\b\2\2\u0085\u0086\5\26\f\2"+
		"\u0086\u0087\7\t\2\2\u0087\25\3\2\2\2\u0088\u0089\7!\2\2\u0089\u008b\7"+
		"\37\2\2\u008a\u0088\3\2\2\2\u008b\u008e\3\2\2\2\u008c\u008a\3\2\2\2\u008c"+
		"\u008d\3\2\2\2\u008d\u0090\3\2\2\2\u008e\u008c\3\2\2\2\u008f\u0091\7!"+
		"\2\2\u0090\u008f\3\2\2\2\u0090\u0091\3\2\2\2\u0091\27\3\2\2\2\u0092\u0093"+
		"\7\16\2\2\u0093\u0094\7\36\2\2\u0094\u0095\7\3\2\2\u0095\u0096\5\"\22"+
		"\2\u0096\u0097\7\7\2\2\u0097\u0098\7\37\2\2\u0098\31\3\2\2\2\u0099\u009a"+
		"\7!\2\2\u009a\u009c\7\37\2\2\u009b\u0099\3\2\2\2\u009c\u009f\3\2\2\2\u009d"+
		"\u009b\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u00a1\3\2\2\2\u009f\u009d\3\2"+
		"\2\2\u00a0\u00a2\7!\2\2\u00a1\u00a0\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2"+
		"\33\3\2\2\2\u00a3\u00a4\7\17\2\2\u00a4\u00a5\7\36\2\2\u00a5\u00a6\7\b"+
		"\2\2\u00a6\u00a7\5\32\16\2\u00a7\u00a8\7\t\2\2\u00a8\u00a9\7\37\2\2\u00a9"+
		"\35\3\2\2\2\u00aa\u00ab\7\20\2\2\u00ab\u00ac\7\36\2\2\u00ac\u00ad\7!\2"+
		"\2\u00ad\u00ae\7\37\2\2\u00ae\37\3\2\2\2\u00af\u00b0\7\21\2\2\u00b0\u00b1"+
		"\7\36\2\2\u00b1\u00b2\7\3\2\2\u00b2\u00b3\5(\25\2\u00b3\u00b4\7\7\2\2"+
		"\u00b4!\3\2\2\2\u00b5\u00b6\5$\23\2\u00b6\u00b7\7\37\2\2\u00b7\u00b9\3"+
		"\2\2\2\u00b8\u00b5\3\2\2\2\u00b9\u00bc\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba"+
		"\u00bb\3\2\2\2\u00bb\u00be\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bd\u00bf\5$"+
		"\23\2\u00be\u00bd\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf#\3\2\2\2\u00c0\u00c1"+
		"\7!\2\2\u00c1\u00c2\7\36\2\2\u00c2\u00c3\5&\24\2\u00c3%\3\2\2\2\u00c4"+
		"\u00ce\7#\2\2\u00c5\u00c6\7\b\2\2\u00c6\u00c7\7\22\2\2\u00c7\u00c8\7\37"+
		"\2\2\u00c8\u00c9\7#\2\2\u00c9\u00ce\7\t\2\2\u00ca\u00cb\7\b\2\2\u00cb"+
		"\u00cc\7\23\2\2\u00cc\u00ce\7\t\2\2\u00cd\u00c4\3\2\2\2\u00cd\u00c5\3"+
		"\2\2\2\u00cd\u00ca\3\2\2\2\u00ce\'\3\2\2\2\u00cf\u00d0\5*\26\2\u00d0\u00d1"+
		"\7\37\2\2\u00d1\u00d3\3\2\2\2\u00d2\u00cf\3\2\2\2\u00d3\u00d6\3\2\2\2"+
		"\u00d4\u00d2\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5\u00d8\3\2\2\2\u00d6\u00d4"+
		"\3\2\2\2\u00d7\u00d9\5*\26\2\u00d8\u00d7\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9"+
		")\3\2\2\2\u00da\u00db\7!\2\2\u00db\u00dc\7\36\2\2\u00dc\u00dd\7\b\2\2"+
		"\u00dd\u00de\7\24\2\2\u00de\u00df\7\37\2\2\u00df\u00e0\5,\27\2\u00e0\u00e1"+
		"\7\t\2\2\u00e1+\3\2\2\2\u00e2\u00e3\7#\2\2\u00e3\u00e5\7\37\2\2\u00e4"+
		"\u00e2\3\2\2\2\u00e5\u00e8\3\2\2\2\u00e6\u00e4\3\2\2\2\u00e6\u00e7\3\2"+
		"\2\2\u00e7\u00e9\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e9\u00ea\7#\2\2\u00ea"+
		"-\3\2\2\2\u00eb\u00ec\7\25\2\2\u00ec\u00ed\7\36\2\2\u00ed\u00ee\7\b\2"+
		"\2\u00ee\u00ef\5\60\31\2\u00ef\u00f0\7\t\2\2\u00f0\u00f1\7\37\2\2\u00f1"+
		"/\3\2\2\2\u00f2\u00f3\7\3\2\2\u00f3\u00f4\5\62\32\2\u00f4\u00f5\7\7\2"+
		"\2\u00f5\u00f6\7\37\2\2\u00f6\u00f8\3\2\2\2\u00f7\u00f2\3\2\2\2\u00f8"+
		"\u00fb\3\2\2\2\u00f9\u00f7\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\u00fc\3\2"+
		"\2\2\u00fb\u00f9\3\2\2\2\u00fc\u00fd\7\3\2\2\u00fd\u00fe\5\62\32\2\u00fe"+
		"\u00ff\7\7\2\2\u00ff\61\3\2\2\2\u0100\u0101\5\36\20\2\u0101\u0102\5\64"+
		"\33\2\u0102\u0103\5\66\34\2\u0103\u0104\58\35\2\u0104\u0105\5:\36\2\u0105"+
		"\u0106\5<\37\2\u0106\u0107\5> \2\u0107\u0108\5@!\2\u0108\u0109\5B\"\2"+
		"\u0109\63\3\2\2\2\u010a\u010b\7\26\2\2\u010b\u010c\7\36\2\2\u010c\u010d"+
		"\7\3\2\2\u010d\u010e\5\"\22\2\u010e\u010f\7\7\2\2\u010f\u0110\7\37\2\2"+
		"\u0110\65\3\2\2\2\u0111\u0112\7\27\2\2\u0112\u0113\7\36\2\2\u0113\u0114"+
		"\7\b\2\2\u0114\u0115\5\32\16\2\u0115\u0116\7\t\2\2\u0116\u0117\7\37\2"+
		"\2\u0117\67\3\2\2\2\u0118\u0119\7\30\2\2\u0119\u011a\7\36\2\2\u011a\u011b"+
		"\7\b\2\2\u011b\u011c\7\t\2\2\u011c\u011d\7\37\2\2\u011d9\3\2\2\2\u011e"+
		"\u011f\7\31\2\2\u011f\u0120\7\36\2\2\u0120\u0121\7 \2\2\u0121\u0122\7"+
		"\37\2\2\u0122;\3\2\2\2\u0123\u0124\7\32\2\2\u0124\u0125\7\36\2\2\u0125"+
		"\u0126\7 \2\2\u0126\u0127\7\37\2\2\u0127=\3\2\2\2\u0128\u0129\7\33\2\2"+
		"\u0129\u012a\7\36\2\2\u012a\u012b\7 \2\2\u012b\u012c\7\37\2\2\u012c?\3"+
		"\2\2\2\u012d\u012e\7\34\2\2\u012e\u012f\7\36\2\2\u012f\u0130\7!\2\2\u0130"+
		"\u0131\7\37\2\2\u0131A\3\2\2\2\u0132\u0133\7\35\2\2\u0133\u0134\7\36\2"+
		"\2\u0134\u0135\7#\2\2\u0135C\3\2\2\2\17^n\u008c\u0090\u009d\u00a1\u00ba"+
		"\u00be\u00cd\u00d4\u00d8\u00e6\u00f9";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}