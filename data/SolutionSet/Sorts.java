import java.util.*;

// Assignment #: 8
//         Name: Daniel D'Souza
//    StudentID: 1207035111
//  Lab Lecture: Tu/Thrs 4:30
//  Description: Sorts object using compareTo

public class Sorts {
	
	private ArrayList<Comparable> sortedList; //contains stuff to sort

	/**
	 * Constructs a sort object, and carries out operations
	 * @param unsortedList
	 */
	public Sorts (ArrayList<Comparable> unsortedList) {
		sortedList = new ArrayList<Comparable>();
		sortedList = unsortedList; //assigns unsorted values to list
		sort(); //sorts
	}
	
	/**
	 * Returns the sorted list.
	 * @return the sorted List with Comparables
	 */
	public ArrayList<Comparable> getSortedList() {
		return sortedList;
	}
	
	/**
	 * Sorts the ArrayList with a strange insertion sort. I think it's closer to bubble sort
	 */
	private void sort() {
		for (int i=0; i < sortedList.size()-1; i++) { //iterates through array
			for (int j=i+1; j < sortedList.size(); j++) { //compares numbers
				if(sortedList.get(i).compareTo(sortedList.get(j)) > 0){
					swap(i,j);
				}
			}
		}
	}
	
	/**
	 * Swaps two entries
	 * @param from, the first entry
	 * @param to, the second entry
	 */
	private void swap(int from, int to) {
		Comparable temp = sortedList.get(to);
		sortedList.set(to, sortedList.get(from));
		sortedList.set(from, temp);
	}
}
