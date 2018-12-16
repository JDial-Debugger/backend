public class Main
{
    public static int sumPow(int x){
		int sum = 1;
		for(int i = 1; i < x; i++) {
			sum += Math.pow(2,i);
		}
		return sum;
    
	}

	public static void main(String[] args)
	{
	    int x = triple(9); 
	    System.out.println(x);
	}		
}