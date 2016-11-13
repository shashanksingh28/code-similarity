public class Vehicle
{
   private int numberOfTires;

   public int getNumberOfTires() 
   {
      return numberOfTires; 
   }

   public void setNumberOfTires(int newValue) 
   { 
      numberOfTires = newValue;
   }

   public String toString()
   {
      return getClass().getName() + "[numberOfTires=" + numberOfTires + "]";
      // This is a good way of implementing toString in a superclass--see Special Topic 9.6
   }
}
