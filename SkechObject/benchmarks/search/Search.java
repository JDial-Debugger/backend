public class Search {
	
	public static int search(int[] arrayToSearch, int value) {
		
		for (int i = 0; i < arrayToSearch.length; ++i) {
			if (arrayToSearch[i] == value) {
				return i;
			}
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		int[] arrayToSearch = { 1, 5, 3, 2, 6, 3, 8 };
		int targetValue = 8;
		
		int matchingIdx = search(arrayToSearch, targetValue);
	}
}
