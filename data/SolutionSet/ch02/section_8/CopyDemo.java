import java.awt.Rectangle;

/**
   This example demonstrates the difference between copying
   numbers and object references.
*/
public class CopyDemo
{
   public static void main(String[] args)
   {
      // Declare two object variables and copy the first into the second

      Rectangle box = new Rectangle(5, 10, 20, 30);
      Rectangle box2 = box;
      
      // Both variables refer to the same object

      System.out.print("box: ");
      System.out.println(box);
      System.out.print("box2: ");
      System.out.println(box2);

      System.out.println("Mutating box2");
      box2.translate(15, 25);

      // Both variables refer to the mutated object

      System.out.print("box: ");
      System.out.println(box);
      System.out.print("box2: ");
      System.out.println(box2);

      // Declare two number variables and copy the first into the second

      int luckyNumber = 13; 
      int luckyNumber2 = luckyNumber; 

      System.out.print("luckyNumber: ");
      System.out.println(luckyNumber);
      System.out.print("luckyNumber2: ");
      System.out.println(luckyNumber2);
      
      System.out.println("Changing luckyNumber2");
      luckyNumber2 = 12;

      // Only the second number changes.

      System.out.print("luckyNumber: ");
      System.out.println(luckyNumber);
      System.out.print("luckyNumber2: ");
      System.out.println(luckyNumber2);
   }
}



