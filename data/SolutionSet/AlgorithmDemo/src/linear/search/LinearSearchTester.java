package linear.search;

public class LinearSearchTester {

	public static void main(String[] args) {
		
		int[] aRandomArray = {12, 2, 4, 5, 6, 7, 1, 19, 10};
		
		//I want to find 7
		LinearSearcher searcher = new LinearSearcher();
		
		int foundIndex = searcher.linearSearch(aRandomArray, 7);
		
		System.out.println("aRandomArray["+ foundIndex +"] = 7");

	}

}
