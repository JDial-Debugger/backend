package repair;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.io.Resources;

@Generated(value = "org.junit-tools-1.1.0")
public class RepairEngineTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	
	@Before
	public void setUpStreams() {
		//System.setOut(new PrintStream(outContent));
		//System.setErr(new PrintStream(errContent));
	}

	@Test
	public void testMainTracePointBasic() throws Exception {
		String repairType = "tracePointCorrection";
		String json = Resources.toString(Resources.getResource("basicTracePointCorrection1.json"), Charset.defaultCharset());
		String[] args = new String[] { repairType, json };
		// default test
		RepairEngine.main(args);
		System.out.println("out content");
		System.out.println(outContent.toString());
	}
	
	@Test
	public void testMainFuncCorrectionBasic() throws Exception {
		String repairType = "funcCorrection";
		String filePath= Resources.getResource("basicFuncCorrection1.json").getPath();
		String[] args = new String[] { repairType, filePath };
		// default test
		RepairEngine.main(args);
		System.out.println("out content");
		System.out.println(outContent.toString());
	}
	
	@After
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setOut(originalErr);
	}
	
}