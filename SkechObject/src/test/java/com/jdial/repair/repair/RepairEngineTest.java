package repair;

import static org.junit.Assert.*;

import java.nio.charset.Charset;
import java.util.*;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import json_input.Correction;

@Generated(value = "org.junit-tools-1.1.0")
public class RepairEngineTest {

	@Test
	public void testMain() throws Exception {
		String[] args = new String[] { "" };

		// default test
		//RepairEngine.main(args);
	}
	
	@Test
	public void testRepairFuncCorrection() throws Exception {
		Gson gson = new Gson();
		String repairType = "tracePointCorrection";
		String json = Resources.toString(Resources.getResource("basicTracePointCorrection1.json"), Charset.defaultCharset());
		RepairEngine.repair(repairType, json, gson);
	}

	@Test
	public void testAddExampleByTracePointRepair() throws Exception {
	}

	@Test
	public void testAddExamplesByFuncRepair() throws Exception {
	}

	@Test
	public void testGetExample() throws Exception {
	}
}