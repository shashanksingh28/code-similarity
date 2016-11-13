class ThrowsExample1
{
   static void throwMethod() throws NullPointerException
   {
       System.out.println ("Inside throwMethod");
       throw new NullPointerException ("Demo"); 
   }
   
   public static void main(String args[])
   {
       try
       {
          throwMethod();
       }
       catch (NullPointerException exp)
       {
          System.out.println ("The exception get caught" +exp);
       }
    }
}
