package factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.google.common.collect.ImmutableMap;

import json_input.RenderStack;
import json_input.Frame;

public enum RenderStackTestFactory {
	SIMPLE1 (
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
		)
	),
	FRAMES2_RETURN (
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
		)
	);
	
	private final RenderStack stack;
	
	RenderStackTestFactory(RenderStack stack) {
		this.stack = stack;
	}
	
	public RenderStack getRenderStack() { return this.stack; }

}
