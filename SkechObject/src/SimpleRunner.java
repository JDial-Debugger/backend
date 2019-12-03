import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SimpleRunner {
	public static void main(String[] args) throws FileNotFoundException, InterruptedException{
		String oriTraces = new Scanner(new File("benchmarks/traceBasic.json")).useDelimiter("\\Z").next();
		String correctTrace = new Scanner(new File("benchmarks/pointBasic.json")).useDelimiter("\\Z").next();
		MainEntrance me = new MainEntrance(oriTraces,correctTrace,8);
		try {
			String res = me.Synthesize(true).toString();
			System.out.println(res);
			
		} catch (SketchExecException ex) {
			System.out.println(ex);
		}
	}
}
