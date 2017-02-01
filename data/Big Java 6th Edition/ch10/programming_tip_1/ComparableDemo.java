import java.util.Arrays;

/**
   This program demonstrates compareTo methods that compare
   integers and floating-point values.
*/
public class ComparableDemo
{
   public static void main(String[] args)
   {
      Comparable[] accounts = new Comparable[3];
      accounts[0] = new BankAccount(0);
      accounts[1] = new BankAccount(10000);
      accounts[2] = new BankAccount(2000);

      Arrays.sort(accounts);
      System.out.println(Arrays.toString(accounts));

      Comparable[] people = new Comparable[3];
      people[0] = new Person("James", "Gosling", 42);
      people[2] = new Person("Alonzo", "Church", 30510);
      people[1] = new Person("John", "Mauchly", 1729);

      Arrays.sort(people);
      System.out.println(Arrays.toString(people));
   }
}
