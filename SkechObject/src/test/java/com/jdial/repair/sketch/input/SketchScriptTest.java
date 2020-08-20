package sketch.input;

import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import repair.CorrectionExample;
import sketch.input.SketchScript;
import sketchobj.core.Function;
import sketchobj.stmts.Statement;

@Generated(value = "org.junit-tools-1.1.0")
public class SketchScriptTest {

	private SketchScript createTestSubject() {
		return new SketchScript("", null, null);
	}

	@Test
	public void testSketchScript() throws Exception {
		SketchScript testSubject;
		String code = "";
		List<CorrectionExample> examples = null;
		Map<String, Function> relevantFuncs = null;

	}

	@Test
	public void testInsertCoeffs() throws Exception {
		SketchScript testSubject;
		Statement result;

		// default test
		testSubject = createTestSubject();
	}

	@Test
	public void testAddContext() throws Exception {
		SketchScript testSubject;
		Function func = null;

		// default test
		testSubject = createTestSubject();
		Whitebox.invokeMethod(testSubject, "addContext", new Object[] { Function.class });
	}
}