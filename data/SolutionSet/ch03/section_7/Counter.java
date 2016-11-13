/**
   This example demonstrates the this reference.
*/
public class Counter
{
   private int value; 

   /**
      Constructs a counter with a given value
   */
   public Counter(int value) 
   {
      this.value = value;
      // this. resolves the conflict between an instance variable
      // and a local variable with the same name
   }

   public Counter()
   {
      this(0);
      // Invokes the Counter(int value) constructor with value = 0
   }

   /**
      Advances the value of this counter by 1.
   */
   public void click() 
   {
      this.value = this.value + 1;
      // Using this makes it clear that the instance variable is updated
   }

   /**
      Gets the previous value of this counter.
      @return the previous value
   */
   public int getPrevious() 
   {
      return getValue() - 1;
      // When you call a method without an object, it is invoked
      // on the this reference, i.e this.getValue().
   }

   /**
      Gets the current value of this counter.
      @return the current value
   */
   public int getValue()
   {
      return value;
      // Or, if you prefer, return this.value;
   }
}
