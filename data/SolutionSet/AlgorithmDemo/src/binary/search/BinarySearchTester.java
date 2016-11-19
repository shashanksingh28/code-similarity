package binary.search;

public class BinarySearchTester {

	public static void main(String[] args) {
		
		int[] mySortedArray = { 1,5,8,9,12,17,20,32};
		
		BinarySearcher searcher = new BinarySearcher(mySortedArray);
				
		int foundIndex = searcher.search(15);
		
		if (foundIndex == -1){
			System.out.println("15 is not found!");
		}else{
			System.out.println("mySortedArray["+ foundIndex +"] = 15");	
		}
		
		

		
	}

}
