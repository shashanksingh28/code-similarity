// Assignment #: Arizona State University CSE205 #6
//         Name: your name
//    StudentID: your id
//      Lecture: your lecture time (e.g., MWF 10:30am)
//  Description: The Car class represent a car with information
//  such as its manufacturer, brand, year and price. 
package fall2015;
import java.text.DecimalFormat;

public class Car
{
   //The instance variables of the Car
   private String manufacturer;
   private String brandName;
   private int year;
   private double price;

   //Constructor method:
   //It initializes all instance variables to their default values.
   
   public Car()
   {
      manufacturer = new String("?");
      brandName = new String("?");
      year = 0;
      price = 0.0;
   }
    
   //Overloaded constructor, used to initialize all instance varibles
   public Car(String nManufacturer, String nBrandName, int nYear, double nPrice)
   {
      manufacturer = nManufacturer;
      brandName =nBrandName ;
      year = nYear;
      price = nPrice;
   }
    
   //Accessor method of the instance variable manufacturer
   public String getManufacturer()
   {
      return manufacturer;
   }
   
   //Accessor method of the instance variable brandName
   public String getBrandName()
   {
      return brandName;
   }

   //Accessor method of the instance variable year
   public int getYear()
   {
      return year;
   }

  
   //Accessor method of the instance variable price
   public double getPrice()
   {
      return price;
   }

   //toString method creates a string containing values of
   //instance variables using the specified format
   public String toString()
   {
      DecimalFormat fmt1 = new DecimalFormat("$0.00");
      String result = "\nManufacturer:\t" + manufacturer + "\n" +
            "Brand Name:\t" + brandName + "\n" +
            "Year:\t" + year + "\n" +
            "Price:\t"+ fmt1.format(price)+ "\n\n";
   
      return result;
   
   }

} //end of the Car class