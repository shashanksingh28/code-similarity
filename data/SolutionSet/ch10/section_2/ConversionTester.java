/**
   This program demonstrates conversions between class and 
   interface types.
*/
public class ConversionTester
{
   public static Measurable larger(Measurable obj1, Measurable obj2)
   {
      if (obj1.getMeasure() > obj2.getMeasure()) 
      { 
         return obj1; 
      }
      else 
      { 
         return obj2; 
      }
   }

   public static void main(String[] args)
   {
      Country uruguay = new Country("Uruguay", 176220);
      Country thailand = new Country("Thailand", 513120);
      Measurable max = larger(uruguay, thailand);
      Country maxCountry = (Country) max;
      String name = maxCountry.getName();
      System.out.println("Country with larger area: " + name);
      System.out.println("Expected: Thailand");
   }
}
