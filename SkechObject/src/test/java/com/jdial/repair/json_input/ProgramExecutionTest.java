package json_input;

import static org.junit.Assert.assertSame;

import javax.annotation.Generated;
import org.junit.Before;
import org.junit.Test;

@Generated(value = "org.junit-tools-1.1.0")
public class ProgramExecutionTest {

	@Before
	public void setUp() throws Exception {

	}

	private static class simple1 {
		static Trace testTrace = new Trace();
		static String testStdin = "stdin";
		static String testCode = "Code";
		static ProgramExecution getTestSubject() {
			return new ProgramExecution(testStdin, testTrace, testCode);
		}
	}

	//@MethodRef(name = "getTrace", signature = "()QTrace;")
	@Test
	public void testGetTrace() throws Exception {
		ProgramExecution testSubject;
		Trace result;

		// default test
		testSubject = simple1.getTestSubject();
		result = testSubject.getTrace();
		assertSame(result, simple1.testTrace);
		
	}

	//@MethodRef(name = "getCode", signature = "()QString;")
	@Test
	public void testGetCode() throws Exception {
		ProgramExecution testSubject;
		String result;

		// default test
		testSubject = simple1.getTestSubject();
		result = testSubject.getCode();
		assertSame(result, simple1.testCode);
	}
}