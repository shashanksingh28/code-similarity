/**
   A bank consisting of multiple bank accounts.
*/
public class Bank
{
   private BankAccount[] accounts;

   /**
      Constructs a bank account with a given number of accounts.
      @param size the number of accounts
   */
   public Bank(int size)
   {
      accounts = new BankAccount[size];
      for (int i = 0; i < accounts.length; i++)
      {
         accounts[i] = new BankAccount();
      }
   }

   /**
      Deposits money into a bank account.
      @param accountNumber the account number
      @param amount the amount to deposit
   */
   public void deposit(int accountNumber, double amount)
   {
      BankAccount account = accounts[accountNumber];
      account.deposit(amount);
   }

   /**
      Withdraws money from a bank account.
      @param accountNumber the account number
      @param amount the amount to withdraw
   */
   public void withdraw(int accountNumber, double amount)
   {
      BankAccount account = accounts[accountNumber];
      account.withdraw(amount);
   }

   /**
      Gets the balance of a bank account.
      @param accountNumber the account number
      @return the account balance
   */
   public double getBalance(int accountNumber)
   {
      BankAccount account = accounts[accountNumber];
      return account.getBalance();
   }
}


