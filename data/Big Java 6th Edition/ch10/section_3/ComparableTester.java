import java.util.Arrays;

public class ComparableTester
{
   public static void main(String[] args)
   {
      BankAccount[] accounts = new BankAccount[3];
      accounts[0] = new BankAccount(10000);
      accounts[1] = new BankAccount(0);
      accounts[2] = new BankAccount(2000);      
      Arrays.sort(accounts);
      for (int i = 0; i < accounts.length; i++)
      {
	 System.out.print(accounts[i].getBalance() + " ");
      }
      System.out.println();
      System.out.println("Expected: 0.0 2000.0 10000.0");
   }
}
