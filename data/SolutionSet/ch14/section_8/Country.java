/**
   A country with a name and area.
*/
public class Country implements Comparable
{
   private String name;
   private double area;

   /**
      Constructs a country.
      @param aName the name of the country
      @param anArea the area of the country
   */
   public Country(String aName, double anArea) 
   { 
      name = aName;
      area = anArea; 
   }

   /**
      Gets the country name.
      @return the name
   */
   public String getName() 
   {
      return name;
   }

   /**
      Gets the area of the country.
      @return the area
   */
   public double getArea() 
   {
      return area;
   }
   
   public int compareTo(Object otherObject)
   {
      Country other = (Country) otherObject;
      if (area < other.area) { return -1; }
      else if (area == other.area) { return 0; }
      else { return 1; }
   }
   
   public String toString()
   {
      return getClass().getName() + "[name=" + name 
         + ",area=" + area + "]";
   }
}
