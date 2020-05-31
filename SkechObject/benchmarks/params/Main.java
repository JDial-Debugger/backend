
public class Main {
	
	public static int foo(int a, int b) {
		int x = a + 3;
		int y = b;
		return x + y;
	}
	
	public static void main(String[] args) {
		foo(2, 5);//14
		foo(0, 3);//10
	}
}
