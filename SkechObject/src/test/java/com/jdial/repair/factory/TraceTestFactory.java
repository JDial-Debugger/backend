package factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
						"SimpleJava:5",
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
						"SimpleJava:6",
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
						"SimpleJava:6",
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
							"__return__", 0
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
		))), Arrays.asList( //expectedTrimmed
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
							"SimpleJava:5",
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
							"SimpleJava:6",
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
							"SimpleJava:6",
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
			)
		), Arrays.asList( //ExpectedTrimmed bound 5
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
							"SimpleJava:5",
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
			)
		)
	);
	
	private final Trace trace;
	private final List<TracePoint> expectedTrimmed;
	private final List<TracePoint> expectedTrimmedBound5;
	
	TraceTestFactory(Trace trace, 
			List<TracePoint> expectedTrimmed, 
			List<TracePoint> expectedTrimmedBound5) {
		this.trace = trace;
		this.expectedTrimmed = expectedTrimmed;
		this.expectedTrimmedBound5 = expectedTrimmedBound5;
	}
	
	
	public Trace getTrace() { 
		return new Trace(this.trace); 
	}
	
	public List<TracePoint> getExpectedTrimmed() {
		List<TracePoint> copy = new ArrayList<TracePoint>();
		for (TracePoint tracePoint : this.expectedTrimmed) {
			copy.add(new TracePoint(tracePoint));
		}
		return copy;
	}
	
	/**
	 * Returns what tracePoints should be after trimTracePoints() is called
	 * @param bound - if trimTracePoints is called with a bound, the value of 
	 * that bound
	 * @return - The tracePoints that should match the result of trimTracePoints()
	 */
	public List<TracePoint> getExpectedTrimmed(Integer bound) { 
		
		if (bound == null) {
			return getExpectedTrimmed();
		}
		switch (bound) {
			case 5:
				return expectedTrimmedBound5;
			default:
				return null;
		}
	}
	
}
