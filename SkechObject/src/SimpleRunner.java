import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SimpleRunner {
	public static void main(String[] args) throws FileNotFoundException, InterruptedException{
		String oriTraces = new Scanner(new File("trace063290802")).useDelimiter("\\Z").next();
		String correctTrace = new Scanner(new File("point552300905")).useDelimiter("\\Z").next();
		MainEntrance me = new MainEntrance(oriTraces,correctTrace,8);
		String res = me.Synthesize(true).toString();
		System.out.println(res);
	}
}
