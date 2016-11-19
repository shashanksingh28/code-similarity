package linear.search;

public class LinearSearcher {
	
	public int linearSearch(int[] anArray, int x) {
	
		for (int i=0; i<anArray.length; i++) {
			if (anArray[i]==x) {
				return i; // return the found index in anArray
			}
		}
		return -1; // didn't find the number x
	}

}
