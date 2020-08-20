package conditional.basic;

public class Main {
	
	public static int conditionalFunc(int a) {
		
		int b = a + 5;
		
		int result = -100;
		
		//a = 2
		if (b == 7) {
			result  = -2;
		//a = 4
		} else if (b / 2 == 3) {
			result = -4;
		//a = 0
		} else if (b * 2 == 10) {
			result = 0;
		//a = 3
		} else if (b - 2 == 6) {
			result = -3;
		} else {
			result = 100;
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		int sampleArg = 3;
		int funcResult = conditionalFunc(sampleArg);
	}

}
