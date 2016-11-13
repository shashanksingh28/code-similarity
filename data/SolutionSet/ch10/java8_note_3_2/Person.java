/**
   This interface provides a default method name, just like Named.
*/
public interface Person
{
   default String name() { return firstName() + " " + lastName(); }
   
   String firstName();
   String lastName();
}
