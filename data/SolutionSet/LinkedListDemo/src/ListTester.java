import java.util.LinkedList;
import java.util.ListIterator;

/**
   A program that demonstrates the LinkedList class
*/
public class ListTester
{  
   public static void main(String[] args)
   {  
      LinkedList<String> staff = new LinkedList<String>();
      staff.addLast("Dick");
      staff.addLast("Harry");
      staff.addLast("Romeo");
      staff.addLast("Tom");
      
      // | in the comments indicates the iterator position

      ListIterator<String> iterator = staff.listIterator(); // |DHRT
      iterator.next(); // D|HRT
      iterator.next(); // DH|RT

      
      // Add more elements after second element
      
      iterator.add("Juliet"); // DHJ|RT
      iterator.add("Nina"); // DHJN|RT

      iterator.next(); // DHJNR|T

      // Remove last traversed element, removed Romeo

      iterator.remove(); // DHJN|T
     
      // Print all elements

      for (String name : staff)
         System.out.println(name);
   }
}
