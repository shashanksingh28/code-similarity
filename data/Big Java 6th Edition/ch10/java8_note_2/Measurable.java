public interface Measurable
{
   double getMeasure(); // An abstract method
   
   default boolean smallerThan(Measurable other)
   {
      return getMeasure() < other.getMeasure();
   }
}
