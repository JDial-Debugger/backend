package sketch_input;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import static org.mockito.Mockito.*;

public class SketchOutputParserTest {

	private LineNumberReaderFactory getMockLineReaderFactory(LineNumberReader mockReader) {
		LineNumberReaderFactory factoryMock = mock(LineNumberReaderFactory.class);
		when(factoryMock.getLineNumberReader(any())).thenReturn(mockReader);
		return factoryMock;
	}

	@Test
	public void testParseChangedCoeffs_GoldenFlow() throws IOException {

		List<Coefficient> mockInputCoeffs = new ArrayList<Coefficient>();
		Coefficient mockCoeff2 = mock(Coefficient.class);
		when(mockCoeff2.getName()).thenReturn("__jdial_coeff_2");
		mockInputCoeffs.add(mockCoeff2);
		Coefficient mockCoeff3 = mock(Coefficient.class);
		when(mockCoeff3.getName()).thenReturn("__jdial_coeff_3");
		mockInputCoeffs.add(mockCoeff3);
		Coefficient mockCoeff4 = mock(Coefficient.class);
		when(mockCoeff4.getName()).thenReturn("__jdial_coeff_4");
		mockInputCoeffs.add(mockCoeff4);
		Coefficient mockCoeff5 = mock(Coefficient.class);
		when(mockCoeff5.getName()).thenReturn("__jdial_coeff_5");
		mockInputCoeffs.add(mockCoeff5);

		LineNumberReader mockSketchOutputReader = mock(LineNumberReader.class);
		when(mockSketchOutputReader.readLine()).thenReturn(
			"void glblInit___jdial_coeff_3_change_s67 (ref bit __jdial_coeff_3_change_s66)/*tmp.txt:31*/"
		)
			.thenReturn("{")
			.thenReturn("  __jdial_coeff_3_change_s66 = 0;")
			.thenReturn("}")
			.thenReturn(
				"void glblInit___jdial_coeff_4_change_s69 (ref int __jdial_coeff_4_change_s68)/*tmp.txt:42*/"
			)
			.thenReturn("{")
			.thenReturn("  __jdial_coeff_4_change_s68 = 4;")
			.thenReturn("}")
			.thenReturn(
				"void glblInit___jdial_coeff_2_change_s75 (ref bit __jdial_coeff_2_change_s74)/*tmp.txt:20*/"
			)
			.thenReturn("{")
			.thenReturn("  __jdial_coeff_2_change_s74 = 0;")
			.thenReturn("}")
			.thenReturn("/*tmp.txt:50*/")
			.thenReturn("")
			.thenReturn(
				"void glblInit___jdial_coeff_5_change_s59 (ref bit __jdial_coeff_5_change_s58)/*tmp.txt:50*/"
			)
			.thenReturn("{")
			.thenReturn("  __jdial_coeff_5_change_s58 = 1;")
			.thenReturn("}")
			.thenReturn(null);

		LineNumberReaderFactory mockFactory = getMockLineReaderFactory(mockSketchOutputReader);

		SketchOutputParser sketchOutputParserToTest
			= new SketchOutputParser(mockInputCoeffs, mockFactory);

		Set<Coefficient> resultCoeffs = sketchOutputParserToTest.parseChangedCoeffs(null);
		Set<Coefficient> expectedCoeffs
			= new HashSet<Coefficient>(Arrays.asList(mockCoeff4, mockCoeff5));

		assertEquals(expectedCoeffs, resultCoeffs);
		verify(mockCoeff2).removeFromSource();
		verify(mockCoeff3).removeFromSource();
	}

	@Test
	public void testParseChangedCoeffs_EmptyStr() throws IOException {
		LineNumberReader mockSketchOutputReader = mock(LineNumberReader.class);
		when(mockSketchOutputReader.readLine()).thenReturn("").thenReturn(null);

		LineNumberReaderFactory mockFactory = getMockLineReaderFactory(mockSketchOutputReader);
		SketchOutputParser sketchOutputParserToTest
			= new SketchOutputParser(new ArrayList<Coefficient>(), mockFactory);

		Set<Coefficient> resultCoeffs = sketchOutputParserToTest.parseChangedCoeffs(null);

		assertTrue("Empty sketch output did not result in empty array", resultCoeffs.isEmpty());
	}

	@Test
	public void testParseChangedCoeffs_UnknownCoeff() throws IOException {

		List<Coefficient> mockInputCoeffs = new ArrayList<Coefficient>();
		Coefficient mockCoeff2 = mock(Coefficient.class);
		when(mockCoeff2.getName()).thenReturn("__jdial_coeff_2");
		mockInputCoeffs.add(mockCoeff2);
		Coefficient mockCoeff3 = mock(Coefficient.class);
		when(mockCoeff3.getName()).thenReturn("__jdial_coeff_3");
		mockInputCoeffs.add(mockCoeff3);

		LineNumberReader mockSketchOutputReader = mock(LineNumberReader.class);
		when(mockSketchOutputReader.readLine()).thenReturn(
			"void glblInit___jdial_coeff_3_change_s67 (ref bit __jdial_coeff_3_change_s66)/*tmp.txt:31*/"
		)
			.thenReturn("{")
			.thenReturn("  __jdial_coeff_34_change_s66 = 5;")
			.thenReturn("}")
			.thenReturn(null);

		LineNumberReaderFactory mockFactory = getMockLineReaderFactory(mockSketchOutputReader);
		SketchOutputParser sketchOutputParserToTest
			= new SketchOutputParser(mockInputCoeffs, mockFactory);

		Set<Coefficient> resultCoeffs = sketchOutputParserToTest.parseChangedCoeffs(null);

		assertTrue(
			"Unknown coefficient name did not result in empty array",
			resultCoeffs.isEmpty()
		);
	}

	@Test
	public void testParseChangedCoeffs_MissingRepairValue() throws IOException {
		
		List<Coefficient> mockInputCoeffs = new ArrayList<Coefficient>();
		Coefficient mockCoeff3 = mock(Coefficient.class);
		when(mockCoeff3.getName()).thenReturn("__jdial_coeff_3");
		mockInputCoeffs.add(mockCoeff3);

		LineNumberReader mockSketchOutputReader = mock(LineNumberReader.class);
		when(mockSketchOutputReader.readLine()).thenReturn(
			"void glblInit___jdial_coeff_3_change_s67 (ref bit __jdial_coeff_3_change_s66)/*tmp.txt:31*/"
		)
			.thenReturn("{")
			.thenReturn("  __jdial_coeff_3_change_s66 = ")
			.thenReturn("}")
			.thenReturn(null);

		LineNumberReaderFactory mockFactory = getMockLineReaderFactory(mockSketchOutputReader);
		SketchOutputParser sketchOutputParserToTest
			= new SketchOutputParser(new ArrayList<Coefficient>(), mockFactory);

		Set<Coefficient> resultCoeffs = sketchOutputParserToTest.parseChangedCoeffs(null);

		assertTrue(
			"Coefficient in output without repair value did not result in empty array",
			resultCoeffs.isEmpty()
		);
	}

	@Test
	public void testParseChangedCoeffs_NonIntRepairValue() throws IOException {
		
		List<Coefficient> mockInputCoeffs = new ArrayList<Coefficient>();
		Coefficient mockCoeff3 = mock(Coefficient.class);
		when(mockCoeff3.getName()).thenReturn("__jdial_coeff_3");
		mockInputCoeffs.add(mockCoeff3);

		LineNumberReader mockSketchOutputReader = mock(LineNumberReader.class);
		when(mockSketchOutputReader.readLine()).thenReturn(
			"void glblInit___jdial_coeff_3_change_s67 (ref bit __jdial_coeff_3_change_s66)/*tmp.txt:31*/"
		)
			.thenReturn("{")
			.thenReturn("  __jdial_coeff_3_change_s66 = abc")
			.thenReturn("}")
			.thenReturn(null);

		LineNumberReaderFactory mockFactory = getMockLineReaderFactory(mockSketchOutputReader);
		SketchOutputParser sketchOutputParserToTest
			= new SketchOutputParser(new ArrayList<Coefficient>(), mockFactory);

		Set<Coefficient> resultCoeffs = sketchOutputParserToTest.parseChangedCoeffs(null);

		assertTrue(
			"Coefficient in output without non int repair value did not result in empty array",
			resultCoeffs.isEmpty()
		);
	}

	@Test
	public void testParseChangedCoeffs_IOException() throws IOException {
		LineNumberReader mockSketchOutputReader = mock(LineNumberReader.class);
		when(mockSketchOutputReader.readLine()).thenThrow(new IOException("File not found"));

		LineNumberReaderFactory mockFactory = getMockLineReaderFactory(mockSketchOutputReader);
		SketchOutputParser sketchOutputParserToTest
			= new SketchOutputParser(new ArrayList<Coefficient>(), mockFactory);

		try {
			sketchOutputParserToTest.parseChangedCoeffs(null);
		} catch(SketchOutputIOException expectedException) {
			//	should throw exception
			return;
		}
		assertTrue("Did not throw correct exception", false);

	}
}
