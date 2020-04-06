package json_input;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;

import factory.RenderStackTestFactory;

@Generated(value = "org.junit-tools-1.1.0")
public class RenderStackTest {

	@Generated(value = "org.junit-tools-1.1.0")

	@Test
	public void testGetFrames_Simple1() throws Exception {
		RenderStack testSubject;
		List<Frame> result;

		testSubject = RenderStackTestFactory.SIMPLE1.getRenderStack();
		result = testSubject.getFrames();
		
		final Field field = testSubject.getClass().getDeclaredField("frames");
        field.setAccessible(true);
        
        assertEquals("Fields don't match", field.get(testSubject), result);
	}
	
	@Test
	public void testGetFrames_2FramesReturn() throws Exception {
		RenderStack testSubject;
		List<Frame> result;

		testSubject = RenderStackTestFactory.FRAMES2_RETURN.getRenderStack();
		result = testSubject.getFrames();
		
		final Field field = testSubject.getClass().getDeclaredField("frames");
        field.setAccessible(true);
        
        assertEquals("Fields don't match", field.get(testSubject), result);
	}

	@Test
	public void testGetCalledFuncs_empty() throws Exception {
		RenderStack testSubject;
		String callerFunc = "main:10";
		Set<String> result;

		testSubject = RenderStackTestFactory.SIMPLE1.getRenderStack();
		result = testSubject.getCalledFuncs(callerFunc);
		
		Set<String> expected = new HashSet<String>();
        
        assertEquals("Called functions don't match", expected, result);
	}
	
	@Test
	public void testGetCalledFuncs_single() throws Exception {
		RenderStack testSubject;
		String callerFunc = "main:10";
		Set<String> result;

		testSubject = RenderStackTestFactory.FRAMES2_RETURN.getRenderStack();
		result = testSubject.getCalledFuncs(callerFunc);
		
		Set<String> expected = new HashSet<String>(Arrays.asList("SimpleJava"));
        
        assertEquals("Called functions don't match", expected, result);
	}
}