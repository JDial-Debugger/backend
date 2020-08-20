package repair;


import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.io.Resources;

@Generated(value = "org.junit-tools-1.1.0")
public class RepairEngineTest {

	private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private PrintStream originalOut = System.out;
	private PrintStream originalErr = System.err;
	
	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@Test
	public void testMainTracePointBasic() throws Exception {
		String repairType = "tracePointCorrection";
		String inputResourceName = "basicTracePointCorrection1.json";
		String filePath = Resources.getResource(inputResourceName).getPath();
		String[] args = new String[] { repairType, filePath };
		// default test
		RepairEngine.main(args);
		
		String expectedRepairOutput = "{\"4\":\"int b = ((a) + 1) + 3;\"}";
		assertEquals(expectedRepairOutput, outContent.toString().trim());
	}
	
	@Test
	public void testMainFuncCorrectionBasic() throws Exception {
		String repairType = "funcCorrection";
		String inputResourceName = "basicFuncCorrection1.json";
		String filePath = Resources.getResource(inputResourceName).getPath();
		String[] args = new String[] { repairType, filePath };
		// default test
		RepairEngine.main(args);
		
		String expectedRepairOutput = "{\"5\":\"int y = b + 4;\"}";
		assertEquals(expectedRepairOutput, outContent.toString().trim());
	}
	
	@Test
	public void testBasicConditionalTracePoint() throws Exception {
		String repairType = "tracePointCorrection";
		String inputResourceName = "conditional/basic/tracePointCorrection1.json";
		String filePath = Resources.getResource(inputResourceName).getPath();
		String[] args = new String[] { repairType, filePath };
		// default test
		RepairEngine.main(args);
		
		String expectedRepairOutput = "{\"4\":\"int b = a + 2;\"}";
		assertEquals(expectedRepairOutput, outContent.toString().trim());
		System.setOut(originalOut);
		System.out.println(outContent.toString());
		System.out.println("fdsfdsfds");
	}
	
	@After
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setOut(originalErr);
	}
	
}