package com.horstmann.bigjava;

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

   /**
      Adds a percentage to an amount. 
      @param percentage the percentage to apply
      @param amount the amount to which the percentage is applied
      @return the sum of the amount and the requested percentage
   */
   public static double addPercentTo(double percentage, double amount)
   { 
      return (1 + percentage / 100) * amount;
   }

}
