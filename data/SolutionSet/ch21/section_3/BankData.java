import java.io.IOException;
import java.io.RandomAccessFile;

/**
   This class is a conduit to a random access file
   containing bank account records.
*/
public class BankData implements AutoCloseable
{
   private RandomAccessFile file;

   public static final int INT_SIZE = 4;  
   public static final int DOUBLE_SIZE = 8;  
   public static final int RECORD_SIZE = INT_SIZE + DOUBLE_SIZE;

   /**
      Constructs a BankData object that is not associated with a file.
   */
   public BankData()
   {
      file = null;
   }

   /**
      Opens the data file.
      @param filename the name of the file containing bank
      account information
   */
   public void open(String filename)
         throws IOException
   {
      if (file != null) { file.close(); }
      file = new RandomAccessFile(filename, "rw");
   }

   /**
      Gets the number of accounts in the file.
      @return the number of accounts
   */
   public int size()
         throws IOException
   {
      return (int) (file.length() / RECORD_SIZE);
   }

   /**
      Closes the data file.
   */
   public void close()
         throws IOException
   {
      if (file != null) { file.close(); }
      file = null;
   }

   /**
      Reads a bank account record.
      @param n the index of the account in the data file
      @return a bank account object initialized with the file data
   */
   public BankAccount read(int n)
         throws IOException
   {  
      file.seek(n * RECORD_SIZE);      
      int accountNumber = file.readInt();
      double balance = file.readDouble();
      return new BankAccount(accountNumber, balance);
   }

   /**
      Finds the position of a bank account with a given number.
      @param accountNumber the number to find
      @return the position of the account with the given number, 
      or -1 if there is no such account
   */
   public int find(int accountNumber)
         throws IOException
   {
      for (int i = 0; i < size(); i++)
      {
         file.seek(i * RECORD_SIZE);
         int a = file.readInt();         
         if (a == accountNumber) { return i; }
            // Found a match            
      } 
      return -1; // No match in the entire file
   }

   /**
      Writes a bank account record to the data file.
      @param n the index of the account in the data file
      @param account the account to write
   */
   public void write(int n, BankAccount account)
         throws IOException
   {  
      file.seek(n * RECORD_SIZE);
      file.writeInt(account.getAccountNumber());
      file.writeDouble(account.getBalance());
   }
}
