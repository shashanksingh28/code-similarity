import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class SetTester {

	public static void main(String[] args) {
	
		Set names = new HashSet();
	
		names.add("Sharon");
		names.add("Amy");
		names.add("George");
		names.add("Mary");
		
		Iterator iter = names.iterator();
		while (iter.hasNext()){
		          System.out.println(iter.next());
		 }
		
		/*//TreeSet 
		System.out.println();
		Set names2 = new TreeSet();
		names2.add("Sharon");
		names2.add("Amy");
		names2.add("George");
		names2.add("Mary");
		Iterator iter2 = names2.iterator();
		while (iter2.hasNext()){
		          System.out.println(iter2.next());
		 }

		*/
	}

}
