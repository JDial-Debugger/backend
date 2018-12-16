
import java.util.Scanner;

public class Main
{
    public static int triple(int x){
    	int y = 3 * x;
    	if(x == 10)
    		y = 30;
    	return y;
	}

	public static void main(String[] args)
	{
	    int x = triple(9); 
	    System.out.println(x);
	}		
}