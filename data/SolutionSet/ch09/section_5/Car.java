public class Car extends Vehicle
{
   // This instance variable is added to the subclass
   private String licensePlateNumber;
   
   public Car()
   {
      setNumberOfTires(4); 
      licensePlateNumber = "??????";
   }

   public void setLicensePlateNumber(String newValue)
   {
      licensePlateNumber = newValue;
   }

   public String toString() 
   {
      return super.toString() + "[licensePlateNumber=" + licensePlateNumber + "]";
      // This is a good way of implementing toString in a subclass--see Special Topic 9.6
   }
}
