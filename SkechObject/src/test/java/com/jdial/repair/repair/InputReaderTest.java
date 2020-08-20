package repair;

import org.junit.Test;

import constants.Errors;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class InputReaderTest {

	@Test
	public void testReadInput_GoldenFlow_funcCorrection() {
		String[] args = { "funcCorrection", "/tmp/43fjds8f3"};
		String sampleFileContents = "abc";
		
		Input expectedInput = new Input(RepairType.FUNC_CORRECTION, sampleFileContents);
		
		InputFileScanner mockScnr = mock(InputFileScanner.class);
		when(mockScnr.readContents()).thenReturn(sampleFileContents);
		InputFileScannerFactory mockScnrFactory = mock(InputFileScannerFactory.class);
		when(mockScnrFactory.getInputFileScanner(args[1])).thenReturn(mockScnr);
		
		InputReader readerToTest = new InputReader(mockScnrFactory);
		Input result = readerToTest.readInput(args);
		
		assertEquals(expectedInput, result);
	}
	
	@Test
	public void testReadInput_GoldenFlow_tracePointCorrection() {
		String[] args = { "tracePointCorrection", "/tmp/43fjds8f3"};
		String sampleFileContents = "abc";
		
		Input expectedInput = new Input(RepairType.TRACE_POINT, sampleFileContents);
		
		InputFileScanner mockScnr = mock(InputFileScanner.class);
		when(mockScnr.readContents()).thenReturn(sampleFileContents);
		InputFileScannerFactory mockScnrFactory = mock(InputFileScannerFactory.class);
		when(mockScnrFactory.getInputFileScanner(args[1])).thenReturn(mockScnr);
		
		InputReader readerToTest = new InputReader(mockScnrFactory);
		Input result = readerToTest.readInput(args);
		
		assertEquals(expectedInput, result);
	}
	
	@Test
	public void testReadInput_BadRepairType() {
		String[] args = { "badRepairType", "/tmp/43fjds8f3"};
		
		InputReader readerToTest = new InputReader(null);
		
		try {
			readerToTest.readInput(args);
		} catch (InvalidInputException ex) {
			return;
		} catch (Exception ex) {
			assertTrue(Errors.wrongException(ex), false);
		}
		assertTrue(Errors.NO_EXCEPTION, false);
	}
	
	@Test
	public void testReadInput_BadInputLength() {
		String[] args = { "badRepairType" };
		
		InputReader readerToTest = new InputReader(null);
		
		try {
			readerToTest.readInput(args);
		} catch (InvalidInputException ex) {
			return;
		} catch (Exception ex) {
			assertTrue(Errors.wrongException(ex), false);
		}
		assertTrue(Errors.NO_EXCEPTION, false);
	}
}
