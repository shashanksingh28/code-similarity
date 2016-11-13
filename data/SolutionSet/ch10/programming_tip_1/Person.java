public class Person implements Comparable
{
   private String first;
   private String last;
   private int id;

   public Person(String first, String last, int id)
   {
      this.first = first;
      this.last = last;
      this.id = id;
   }

   public String toString()
   {
      return "Person[first=" + first + ",last=" + last + ",id=" + id + "]";
   }

   public int compareTo(Object otherObject)
   {
      Person other = (Person) otherObject;
      return id - other.id;
   }  
}
