import java.awt.Rectangle;

/**
   This program demonstrates the use of a Measurer.
*/
public class MeasurerTester
{
   public static void main(String[] args)
   {
      class AreaMeasurer implements Measurer
      {
         public double measure(Object anObject)
         {
            Rectangle aRectangle = (Rectangle) anObject;
            double area = aRectangle.getWidth() * aRectangle.getHeight();
            return area;
         }
      }

      Measurer areaMeas = new AreaMeasurer();

      Rectangle[] rects = new Rectangle[] 
         {
            new Rectangle(5, 10, 20, 30),
            new Rectangle(10, 20, 30, 40),
            new Rectangle(20, 30, 5, 15)
         };

      double averageArea = Data.average(rects, areaMeas);
      System.out.println("Average area: " + averageArea);
      System.out.println("Expected: 625");
   }
}
