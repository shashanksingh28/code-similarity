
class Asset {

    private int id;
    private String type;

    public Asset() {
        System.out.println("Creating Asset ...");
    }

    public Asset(int id, String type) {
        this.id = id;
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

    public BankAccount() {
        System.out.println("Creating BankAccount ...");
    }

    public BankAccount(String bankName, int accountNumber, float balance,
            int id, String type) {
        super(id, type);
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void printDescription() {
        super.printDescription();
        System.out.println("Name: " + bankName);
        System.out.println("Account #: " + accountNumber);
        System.out.println("Current balance: $" + balance);
    }
}

class SavingsAccount extends BankAccount {

    private float interestRate;

    public SavingsAccount() {
        System.out.println("Creating SavingsAccount ...");
    }

    public SavingsAccount(float interestRate, String bankName,
            int accountNumber, float balance, int id, String type) {
        super(bankName, accountNumber, balance, id, type);
        this.interestRate = interestRate;
    }

    public void printDescription() {
        System.out.println("A savings account");
        super.printDescription();
        System.out.println("Interest rate (%): " + interestRate);
    }
}

public class SuperConstructorApp {

    public static void main(String[] args) {
        String lineSeparator = "-------------------";
        SavingsAccount tomSavingsAccount = new SavingsAccount();
        SavingsAccount jimSavingsAccount = new SavingsAccount(4.0f, "AMEX",
                2015, 500.00f, 2005, "Bank Account");
        System.out.println(lineSeparator);
        System.out.println("Tom's Savings Account");
        tomSavingsAccount.printDescription();
        System.out.println(lineSeparator);
        System.out.println("Jim's Savings Account");
        jimSavingsAccount.printDescription();
        System.out.println(lineSeparator);
    }
}