/**
   This interface provides a default method name, just like Person.
*/
public interface Named
{
   default String name() { return "(NONE)"; }
}
