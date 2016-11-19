package selection.sort;

public class SelectionSortTester {

	public static void main(String[] args) {
		
		int[] a = {11, 9, 17, 5, 12};
		
		System.out.println("Un-sorted array: "+ getAllArrayElementString(a));
		
		SelectionSorter sorter = new SelectionSorter(a); //11, 9, 17, 5, 12
		
		sorter.sort();
		
		System.out.println("Sorted array: "+ getAllArrayElementString(a));

	}

	public static String getAllArrayElementString(int[] a){
		
		String arrayString = "";
		for(int i: a){
			arrayString += i +", ";
		}
		
		return arrayString;
	}
}
