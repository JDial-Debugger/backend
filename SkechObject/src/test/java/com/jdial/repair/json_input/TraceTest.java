package json_input;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Generated;

import org.junit.Test;

import factory.TraceTestFactory;

@Generated(value = "org.junit-tools-1.1.0")
public class TraceTest {

	@Test
	public void testGetTracePoints() throws Exception {

		Trace testSubject = TraceTestFactory.SIMPLE1.getTrace();
		
		final Field field = testSubject.getClass().getDeclaredField("tracePoints");
        field.setAccessible(true);
        
		List<TracePoint> result = testSubject.getTracePoints();
		
        assertEquals("Fields don't match", field.get(testSubject), result);
	}

	@Test
	public void testGetCalledFuncs() throws Exception {
		
		
		Trace testSubject = TraceTestFactory.SIMPLE1.getTrace();
		String callerFunc = "main";
		int callerFuncLine = 10;
		
		Set<String> expected = new HashSet<String>(Arrays.asList("SimpleJava"));

		// default test
		Set<String> result = testSubject.getCalledFuncs(callerFunc, callerFuncLine);
		
        assertEquals("Function names don't match", expected, result);
	}

	@Test
	public void testTrimTracePoints() throws Exception {

		Trace testSubject = TraceTestFactory.SIMPLE1.getTrace();
		String targetFunc = "SimpleJava";
		int bound = 5;
		
		testSubject.trimTracePoints(targetFunc, bound, null);
		
		final Field field = testSubject.getClass().getDeclaredField("tracePoints");
        field.setAccessible(true);
        
        List<TracePoint> expected = TraceTestFactory.SIMPLE1.getExpectedTrimmed(5);
        
        assertEquals("Trace points don't match", 
        		expected, 
        		field.get(testSubject));
	}

	@Test
	public void testTrimTracePointsNoBound() throws Exception {
		
		Trace testSubject = TraceTestFactory.SIMPLE1.getTrace();
		String targetFunc = "SimpleJava";
		int callLine = 10;
		
		testSubject.trimTracePoints(targetFunc, null, callLine);
		
		final Field field = testSubject.getClass().getDeclaredField("tracePoints");
        field.setAccessible(true);
        
        List<TracePoint> expected = TraceTestFactory.SIMPLE1.getExpectedTrimmed();
        
        assertEquals("Trace points don't match", 
        		expected, 
        		field.get(testSubject));
	}
}