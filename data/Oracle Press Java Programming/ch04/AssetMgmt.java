
class Asset {

    private int id;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void printDescription() {
        System.out.println("Asset ID: " + id);
        System.out.println("Asset type: " + type);
    }
}

class BankAccount extends Asset {

    private String bankName;
    private int accountNumber;
    private float balance;

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void printDescription() {
        super.printDescription();
        System.out.println("Name: " + bankName);
        System.out.printf("Account #: %d%n", accountNumber);
        System.out.printf("Current balance: $%.02f%n", balance);
    }
}

class SavingsAccount extends BankAccount {

    private float interestRate;

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public void printDescription() {
        System.out.println("A savings account");
        super.printDescription();
        System.out.printf("Interest rate (%%): %.02f%n", interestRate);
    }
}

class CheckingAccount extends BankAccount {

    private float overdraftLimit;

    public void setOverdraftLimit(float overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    public void printDescription() {
        System.out.println("A checking account");
        super.printDescription();
        System.out.printf("Overdraft limit: $%.02f%n", overdraftLimit);
    }
}

public class AssetMgmt {

    private SavingsAccount tomSavingsAccount;
    private CheckingAccount iVisionBusinessAccount;

    public static void main(String[] args) {
        AssetMgmt manager = new AssetMgmt();
        manager.createAssets();
        manager.printAllAssets();
    }

    private void createAssets() {
        tomSavingsAccount = new SavingsAccount();
        tomSavingsAccount.setId(1001);
        tomSavingsAccount.setType("Bank Account");
        tomSavingsAccount.setBankName("Citi bank");
        tomSavingsAccount.setAccountNumber(526702);
        tomSavingsAccount.setBalance(15450.00f);
        tomSavingsAccount.setInterestRate(3.0f);
        iVisionBusinessAccount = new CheckingAccount();
        iVisionBusinessAccount.setId(1002);
        iVisionBusinessAccount.setType("Bank Account");
        iVisionBusinessAccount.setBankName("Bank of America");
        iVisionBusinessAccount.setAccountNumber(24689);
        iVisionBusinessAccount.setBalance(678256.00f);
        iVisionBusinessAccount.setOverdraftLimit(50000.00f);
    }

    private void printAllAssets() {
        String lineSeparator = "-------------------";
        System.out.println(lineSeparator);
        tomSavingsAccount.printDescription();
        System.out.println(lineSeparator);
        iVisionBusinessAccount.printDescription();
        System.out.println(lineSeparator);
    }
}