package factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.ImmutableMap;

import json_input.Event;
import json_input.Frame;
import json_input.RenderStack;
import json_input.Trace;
import json_input.TracePoint;

/**
 * Generates a Trace to be used in testing
 */
public enum TraceTestFactory {
	
	//SimpleJava program with no issues
	SIMPLE1 (new Trace(Arrays.asList(
		new TracePoint(
			"",
			Event.CALL,
			10,
			new RenderStack(
				Arrays.asList(
					new Frame(
						"main:10",
						new HashMap<String, Integer>(),
						new ArrayList<String>(),
						true,
						false,
						1
					)
				)
			),
			"main",
			new HashMap<String, List<Object>>()
		),
		new TracePoint(
			"",
			Event.STEP_LINE,
			10,
			new RenderStack(
				Arrays.asList(
					new Frame(
						"main:10",
						new HashMap<String, Integer>(),
						new ArrayList<String>(),
						true,
						false,
						2
					)
				)
			),
			"main",
			new HashMap<String, List<Object>>()
		),
		new TracePoint(
			"",
			Event.CALL,
			3,
			new RenderStack(
				Arrays.asList(
					new Frame(
						"SimpleJava:3",
						new HashMap<String, Integer>(),
						new ArrayList<String>(),
						true,
						false,
						3
					),
					new Frame(
						"main:10",
						new HashMap<String, Integer>(),
						new ArrayList<String>(),
						false,
						false,
						4
					)
				)
			),
			"SimpleJava",
			new HashMap<String, List<Object>>()
		),
		new TracePoint(
			"",
			Event.STEP_LINE,
			3,
			new RenderStack(
				Arrays.asList(
					new Frame(
						"SimpleJava:3",
						new HashMap<String, Integer>(),
						new ArrayList<String>(),
						true,
						false,
						5
					),
					new Frame(
						"main:10",
						new HashMap<String, Integer>(),
						new ArrayList<String>(),
						false,
						false,
						6
					)
				)
			),
			"SimpleJava",
			new HashMap<String, List<Object>>()
		),
		new TracePoint(
			"",
			Event.STEP_LINE,
			4,
			new RenderStack(
				Arrays.asList(
					new Frame(
						"SimpleJava:4",
						ImmutableMap.of(
							"a", 2
						),
						Arrays.asList(
							"a"
						),
						true,
						false,
						11
					),
					new Frame(
						"main:10",
						new HashMap<String, Integer>(),
						new ArrayList<String>(),
						false,
						false,
						12
					)
				)
			),
			"SimpleJava",
			new HashMap<String, List<Object>>()
		),
		new TracePoint(
			"",
			Event.STEP_LINE,
			5,
			new RenderStack(
				Arrays.asList(
					new Frame(
						"SimpleJava:4",
						ImmutableMap.of(
							"a", 2,
							"b", 3
						),
						Arrays.asList(
							"a",
							"b"
						),
						true,
						false,
						21
					),
					new Frame(
						"main:10",
						new HashMap<String, Integer>(),
						new ArrayList<String>(),
						false,
						false,
						22
					)
				)
			),
			"SimpleJava",
			new HashMap<String, List<Object>>()
		),
		new TracePoint(
			"",
			Event.STEP_LINE,
			6,
			new RenderStack(
				Arrays.asList(
					new Frame(
						"SimpleJava:4",
						ImmutableMap.of(
							"a", 2,
							"b", 3,
							"c", 5
						),
						Arrays.asList(
							"a",
							"b"
						),
						true,
						false,
						31
					),
					new Frame(
						"main:10",
						new HashMap<String, Integer>(),
						new ArrayList<String>(),
						false,
						false,
						32
					)
				)
			),
			"SimpleJava",
			new HashMap<String, List<Object>>()
		),
		new TracePoint(
			"",
			Event.RETURN,
			6,
			new RenderStack(
				Arrays.asList(
					new Frame(
						"SimpleJava:4",
						ImmutableMap.of(
							"a", 2,
							"b", 3,
							"c", 5,
							"__return__", 5
						),
						Arrays.asList(
							"a",
							"b"
						),
						true,
						false,
						37
					),
					new Frame(
						"main:10",
						new HashMap<String, Integer>(),
						new ArrayList<String>(),
						false,
						false,
						38
					)
				)
			),
			"SimpleJava",
			new HashMap<String, List<Object>>()
		),
		new TracePoint(
			"",
			Event.STEP_LINE,
			10,
			new RenderStack(
				Arrays.asList(
					new Frame(
						"main:10",
						new HashMap<String, Integer>(),
						new ArrayList<String>(),
						true,
						false,
						39
					)
				)
			),
			"main",
			new HashMap<String, List<Object>>()
		),
		new TracePoint(
			"",
			Event.STEP_LINE,
			11,
			new RenderStack(
				Arrays.asList(
					new Frame(
						"main:10",
						ImmutableMap.of(
							"x", 5
						),
						Arrays.asList(
							"x"
						),
						true,
						false,
						40
					)
				)
			),
			"main",
			new HashMap<String, List<Object>>()
		),
		new TracePoint(
			"5\n",
			Event.STEP_LINE,
			12,
			new RenderStack(
				Arrays.asList(
					new Frame(
						"main:12",
						ImmutableMap.of(
							"x", 5
						),
						Arrays.asList(
							"x"
						),
						true,
						false,
						44
					)
				)
			),
			"main",
			new HashMap<String, List<Object>>()
		),
		new TracePoint(
			"5\n",
			Event.RETURN,
			12,
			new RenderStack(
				Arrays.asList(
					new Frame(
						"main:12",
						ImmutableMap.of(
							"x", 5,
							"__return__", null
						),
						Arrays.asList(
							"x",
							"__return__"
						),
						true,
						false,
						44
					)
				)
			),
			"main",
			new HashMap<String, List<Object>>()
		)
	)));
	
	TraceTestFactory(Trace trace) {
		this.trace = trace;
	}
	
	private final Trace trace;
	
	Trace getTrace() { return this.trace; }
	
}
