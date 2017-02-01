public class Financial
{
   /**
      Computes a percentage of an amount. 
      @param percentage the percentage to apply
      @param amount the amount to which the percentage is applied
      @return the requested percentage of the amount 
   */
   public static double percentOf(double percentage, double amount)
   { 
      return (percentage / 100) * amount;
   }
}
