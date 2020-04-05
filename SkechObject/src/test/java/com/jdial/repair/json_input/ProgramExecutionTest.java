package json_input;

import java.lang.reflect.Field;
import java.util.Set;

import javax.annotation.Generated;

import org.junit.Before;
import org.junit.Test;

@Generated(value = "org.junit-tools-1.1.0")
public class ProgramExecutionTest {

	@Before
	public void setUp() throws Exception {

	}

	private ProgramExecution createTestSubject() {
		ProgramExecution subject = new ProgramExecution();
		try {
			Field traceField = ProgramExecution.class.getDeclaredField("trace");
			traceField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ProgramExecution();
	}

	@MethodRef(name = "getTrace", signature = "()QTrace;")
	@Test
	public void testGetTrace() throws Exception {
		ProgramExecution testSubject;
		Trace result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.getTrace();
	}

	@MethodRef(name = "getCode", signature = "()QString;")
	@Test
	public void testGetCode() throws Exception {
		ProgramExecution testSubject;
		String result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.getCode();
	}

	@MethodRef(name = "getCalledFuncs", signature = "(QString;)QSet<QString;>;")
	@Test
	public void testGetCalledFuncs() throws Exception {
		ProgramExecution testSubject;
		String callerFunction = "";
		Set<String> result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.getCalledFuncs(callerFunction);
	}
}