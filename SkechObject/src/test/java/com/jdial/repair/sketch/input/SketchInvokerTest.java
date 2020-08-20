package sketch.input;

import org.junit.Test;

import java.lang.reflect.*;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class SketchInvokerTest {

	@Test
	public void testGetSketchProc_GoldenFlow() throws IOException {

		String sampleFilePath = "/tmp/jdial-fer8ds8dsdf8r3j8";
		FileWriter mockSketchInputWriter = mock(FileWriter.class);
		Runtime mockRuntime = mock(Runtime.class);
		Process mockProcess = mock(Process.class);
		InputStream mockStdOut = mock(InputStream.class);

		when(mockProcess.getInputStream()).thenReturn(mockStdOut);

		when(mockRuntime.exec(anyString(), isNull())).thenReturn(mockProcess);
		String sampleScript = "int a = 5; a = 3 + ??; assert (a == 10);";

		ErrorScanner mockErrScnr = mock(ErrorScanner.class);
		when(mockErrScnr.hasNext()).thenReturn(false);

		ErrorScannerFactory mockScnrFactory = mock(ErrorScannerFactory.class);
		when(mockScnrFactory.getErrorScanner(mockProcess)).thenReturn(mockErrScnr);

		SketchInvoker sketchInvokerToTest
			= new SketchInvoker(
				sampleFilePath,
				mockSketchInputWriter,
				mockRuntime,
				mockScnrFactory
			);

		InputStream result = sketchInvokerToTest.invokeSketch(sampleScript);
		assertEquals(mockStdOut, result);
		verify(mockSketchInputWriter).write(eq(sampleScript));
		verify(mockSketchInputWriter).close();

		verify(mockErrScnr).close();
	}

	@Test
	public void testGetSketchProc_ErrorWriting() throws IOException {

		String sampleFilePath = "/tmp/jdial-fer8ds8dsdf8r3j8";
		FileWriter mockSketchInputWriter = mock(FileWriter.class);

		doThrow(new IOException("File not found")).when(mockSketchInputWriter).write(anyString());

		String sampleScript = "int a = 5; a = 3 + ??; assert (a == 10);";

		SketchInvoker sketchInvokerToTest
			= new SketchInvoker(sampleFilePath, mockSketchInputWriter, null, null);

		try {
			sketchInvokerToTest.invokeSketch(sampleScript);
		} catch (TempFileIOException expectedException) {
			// should throw exception
			return;
		} catch (Exception ex) {
			assertTrue("Threw the incorrect exception: " + ex.toString(), false);
		}
		assertTrue("Did not throw correct exception", false);
	}

	@Test
	public void testGetSketchProc_ErrorExecuting() throws IOException {

		String sampleFilePath = "/tmp/jdial-fer8ds8dsdf8r3j8";
		FileWriter mockSketchInputWriter = mock(FileWriter.class);
		Runtime mockRuntime = mock(Runtime.class);
		Process mockProcess = mock(Process.class);
		InputStream mockStdOut = mock(InputStream.class);

		when(mockProcess.getInputStream()).thenReturn(mockStdOut);

		when(mockRuntime.exec(anyString(), isNull())).thenThrow(
			new IOException("Could not find executable")
		);
		String sampleScript = "int a = 5; a = 3 + ??; assert (a == 10);";

		ErrorScanner mockErrScnr = mock(ErrorScanner.class);
		when(mockErrScnr.hasNext()).thenReturn(false);

		ErrorScannerFactory mockScnrFactory = mock(ErrorScannerFactory.class);
		when(mockScnrFactory.getErrorScanner(mockProcess)).thenReturn(mockErrScnr);

		SketchInvoker sketchInvokerToTest
			= new SketchInvoker(
				sampleFilePath,
				mockSketchInputWriter,
				mockRuntime,
				mockScnrFactory
			);

		try {
			sketchInvokerToTest.invokeSketch(sampleScript);
			// should throw exception
		} catch (SketchExecException expectedException) {
			verify(mockSketchInputWriter).write(eq(sampleScript));
			verify(mockSketchInputWriter).close();
			return;
		} catch (Exception ex) {
			assertTrue("Threw the incorrect exception: " + ex.toString(), false);
		}

		assertTrue("Did not throw any exception", false);
	}

}
