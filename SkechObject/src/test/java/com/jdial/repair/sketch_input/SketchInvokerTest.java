package sketch_input;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.*;

import static org.mockito.Mockito.*;

import org.mockito.internal.util.reflection.FieldSetter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class SketchInvokerTest {

	private Constructor<?> sketchInvokerConstructor;

	@Before
	public void setConstructorAccessible()
			throws ClassNotFoundException,
			NoSuchMethodException,
			SecurityException,
			InstantiationException,
			IllegalAccessException,
			IllegalArgumentException,
			InvocationTargetException {

		Class sketchInvokerClassRef = Class.forName("SketchInvoker");
		this.sketchInvokerConstructor =
				sketchInvokerClassRef.getDeclaredConstructor();
		this.sketchInvokerConstructor.setAccessible(true);
	}

	@Test
	public void testGetSketchProc()
			throws IOException,
			NoSuchFieldException,
			SecurityException,
			InstantiationException,
			IllegalAccessException,
			IllegalArgumentException,
			InvocationTargetException {

		String sampleFilePath = "/tmp/jdial-fer8ds8dsdf8r3j8";
		FileWriter sketchInputWriterMock = mock(FileWriter.class);
		Runtime runtimeMock = mock(Runtime.class);
		Process processMock = mock(Process.class);
		
		when(runtimeMock.exec(anyString(), null)).thenReturn(processMock);
		String sampleScript = "int a = 5; a = 3 + ??; assert (a == 10);";
		
		SketchInvoker sketchInvokerToTest =
				(SketchInvoker) this.sketchInvokerConstructor
						.newInstance(
								sampleFilePath,
								sketchInputWriterMock,
								runtimeMock);
		InputStream result = sketchInvokerToTest.getSketchProc(sampleScript);
		System.out.println(result.toString());
		

	}

}
