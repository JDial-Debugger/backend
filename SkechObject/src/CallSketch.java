import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import constraintfactory.ConstraintFactory;

public class CallSketch {
	

	public CallSketch() {

	}
	/**
	 * Calls Sketch executable on given input s and parses the output from sketch
	 * Create hashmap of coefIdx -> boolean
	 * @param s the text input into sketch
	 * @return Maps from a coefficient number to a value, should only contain non-zero coefficients
	 * @throws InterruptedException
	 */
	static public Map<Integer, Integer> CallByString(String s) throws InterruptedException {

		File dir = new File("/vagrant/backend/suggest/tmp");
		dir.mkdirs();
		File tmp = new File(dir, "tmp.txt");
		Runtime rt = Runtime.getRuntime();
		Map<Integer, Integer> coefToVal = new HashMap<Integer, Integer>();
		Map<Integer, Integer> oriValue = new HashMap<Integer, Integer>();
		List<Integer> unchangedIndex = new ArrayList<Integer>();
		try {
			tmp.createNewFile();
			WriteStringToFile(tmp, s);
			String[] envp = new String[] {"PATH=$PATH:/lib"};
			String backendDIR = "/vagrant/backend/suggest/JDial-debugger/SkechObject/";
			String suggestDIR = "/vagrant/backend/suggest/";
			File libdir = new File(backendDIR + "lib/sketch-1.6.7/sketch-frontend"); 
		//	System.out.println("TMP: " + backendDIR + "tmp/tmp.txt");
			String bitString = "--bnd-mbits 7 --bnd-cbits 5";
			//TODO change tmpt1.txt to actual tmp
			Process proc = rt.exec("./sketch " + " ../../../../../tmp/tmp.txt",null, libdir);
			PrintWriter out = new PrintWriter(suggestDIR + "/tmp/tmpOutput.txt");
			// InputStream stderr = proc.getErrorStream();
			// InputStreamReader isr = new InputStreamReader(stderr);
			// BufferedReader br = new BufferedReader(isr);
			InputStreamReader ir = new InputStreamReader(proc.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line = null;
			//write all of sketch output to a file cuz we need to do 2 passes through it
			while ((line = input.readLine()) != null) {
		         out.println(line);
		    }
			out.close();
			Scanner scnr = new Scanner(new File(suggestDIR + "/tmp/tmpOutput.txt"));
			//first pass we just search for all globalInit vals that are non zero
			Map<Integer, Integer> coefToInitVal = new HashMap<Integer, Integer>();
			int checkIndex = -1;
			boolean checking = false;
			while(scnr.hasNextLine()) {
				line = scnr.nextLine();
				if (line.length() > 25 && line.substring(5, 19).equals("glblInit_coeff")) {
					checkIndex = extractInt(line).get(0); // get X
					checking = true;
					continue;
				}
				//if we are looking for the coefficient value
				if (checking) {
					if (extractInt(line).size() > 0) {
						if (line.substring(2,7).equals("coeff")) {
							int initVal = extractInt(line).get(extractInt(line).size() - 1);
							checking = false;
							//if its 0, don't bother putting in entry, if we check a coef later
							//and its not in the hashmap, then we know its 0
							if(initVal == 0) {
								//System.out.println("Coeff " + checkIndex + " is unchanged");
								continue;
							} else {
								coefToInitVal.put(checkIndex, initVal);
							}
						} 
					}
				}
			}
			scnr.close();
			scnr = new Scanner(new File(suggestDIR + "/tmp/tmpOutput.txt"));
			//keep track of all line nums that have repairs cuz we only care about the coef's original values
			//on these lines
			HashSet<Integer> changedLineNums = new HashSet<Integer>();
			for(Integer coefIdx : coefToInitVal.keySet()) {
				changedLineNums.add(ConstraintFactory.coeffIndex_to_Line.get(coefIdx));
			}
			//second pass, we check for what the coef function returns depending on if init was 0 or not
			//and if that coef is on a repaired line
			while(scnr.hasNextLine()) {
				line = scnr.nextLine();
				if (line.length() > 12) {
					if (line.substring(0, 10).equals("void Coeff")) { 
						int curCoeff= extractInt(line).get(0);
						//	next line will always be _out = 0; which we don't care about
						scnr.nextLine();
						scnr.nextLine();
						line = scnr.nextLine();
						boolean isOnRepairLine = changedLineNums.contains(ConstraintFactory.coeffIndex_to_Line.get(curCoeff));
						//	now on the next line there is 2 possible options:
						//1)	if(coeffXchange_yadayada == 0
						//2)	_out = coeffXchange_yadayada
						//check for first case
						if(line.substring(2,10).equals("if(coeff") && isOnRepairLine) {
							//we want return value in the if statement
							if(coefToInitVal.get(curCoeff) == null) {
								//throw away line with the curly brace
								scnr.nextLine();
								line = scnr.nextLine();
								int retVal = extractInt(line).get(0);
								coefToVal.put(curCoeff, retVal);
								//next few lines we dont care about
								scnr.nextLine();
								scnr.nextLine();
								scnr.nextLine();
								scnr.nextLine();
								scnr.nextLine();
								continue;
							} else if (coefToInitVal.get(curCoeff) != null ) {
								scnr.nextLine();
								scnr.nextLine();
								scnr.nextLine();
								scnr.nextLine();
								line = scnr.nextLine();
								int retVal = extractInt(line).get(0);
								coefToVal.put(curCoeff, retVal);
								
							}
							
						}else if(line.substring(2,9).equals("_out = ") && isOnRepairLine) {
							if(coefToInitVal.get(curCoeff) != null) {
								coefToVal.put(curCoeff, coefToInitVal.get(curCoeff));
							
							}
						}
						
					}
				}
			}
			scnr.close();
			return coefToVal;

		} catch (IOException e) {
			System.out.println("ERROR: " + e);
			e.printStackTrace();
		}
		//System.out.println("result: " + result);
		//System.out.println("valid List: " + validList);
		return coefToVal;
	}

	static void WriteStringToFile(File f, String s) throws IOException {
		FileWriter fileWriter = new FileWriter(f);
		fileWriter.write(s);
		fileWriter.close();
	}

	static List<Integer> extractInt(String str) {
		if (str.length() < 3)
			return new ArrayList<>();
		str = str.replaceAll("[^-?0-9]+", " ");
		List<String> lstr = Arrays.asList(str.trim().split(" "));
		List<Integer> lint = new ArrayList<Integer>();
		if (lstr.size() == 0)
			return lint;
		for (String s : lstr) {
			if (s.length() == 0 || s.length() > 5 || s.equals("-"))
				continue;
			lint.add(Integer.parseInt(s));
		}
		return (lint);
	}

}
