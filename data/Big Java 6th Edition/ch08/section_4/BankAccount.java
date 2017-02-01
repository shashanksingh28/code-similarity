/**
   A bank account has a balance that can be changed by 
   deposits and withdrawals.
*/
public class BankAccount
{  
   private double balance;
   private int accountNumber;
   private static int lastAssignedNumber = 1000;

   public static final double OVERDRAFT_FEE = 29.95;

   /**
      Constructs a bank account with a zero balance.
   */
   public BankAccount()
   {   
      lastAssignedNumber++;
      accountNumber = lastAssignedNumber;
      balance = 0;
   }

   /**
      Constructs a bank account with a given balance.
      @param initialBalance the initial balance
   */
   public BankAccount(double initialBalance)
   {   
      balance = initialBalance;
   }

   /**
      Deposits money into this account.
      @param amount the amount to deposit
   */
   public void deposit(double amount)
   {  
      balance = balance + amount;
   }

   /**
      Makes a withdrawal from this account, or charges a penalty if
      sufficient funds are not available.
      @param amount the amount of the withdrawal
   */
   public void withdraw(double amount)
   {   
      if (amount > balance)
      {
         balance = balance - OVERDRAFT_FEE;
      }
      else
      {
         balance = balance - amount;
      }
   }

   /**
      Adds interest to this account.
      @param rate the interest rate in percent
   */
   public void addInterest(double rate)
   {   
      balance = balance + Financial.percentOf(rate, balance);
   }

   /**
      Gets the current balance of this account.
      @return the current balance
   */
   public double getBalance()
   {   
      return balance;
   }
}
