
class Asset {

    private int id;
    private String type;

    public Asset() {
        System.out.println("Creating Asset ...");
    }
}

class BankAccount extends Asset {

    private String bankName;
    private int accountNumber;
    private float balance;

    public BankAccount() {
        System.out.println("Creating BankAccount ...");
    }
}

class SavingsAccount extends BankAccount {

    private float interestRate;

    public SavingsAccount() {
        System.out.println("Creating SavingsAccount ...");
    }
}

public class ObjectCreationProcess {

    public static void main(String[] args) {
        SavingsAccount tomSavingsAccount = new SavingsAccount();
    }
}