
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

    public float getNetWorth() {
        return 0;
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

    public float getNetWorth() {
        return balance;
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

class Security extends Asset {

    private String tradeExchangeName;

    public String getTradeExchangeName() {
        return tradeExchangeName;
    }

    public void setTradeExchangeName(String tradeExchangeName) {
        this.tradeExchangeName = tradeExchangeName;
    }

    public void printDescription() {
        super.printDescription();
        System.out.println("Trade Exchange: " + tradeExchangeName);
    }
}

class Stock extends Security {

    private String symbol;
    private float marketPrice;
    private int quantityAtHand;

    public void setMarketPrice(float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public void setQuantityAtHand(int quantityAtHand) {
        this.quantityAtHand = quantityAtHand;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void printDescription() {
        System.out.println("Investment in securities");
        super.printDescription();
        System.out.println("Stock: " + symbol);
        System.out.printf("Today's market price: $%.02f%n", marketPrice);
        System.out.printf("Quantity at Hand: %d%n", quantityAtHand);
        System.out.printf("Net worth: $%.02f%n", marketPrice * quantityAtHand);
    }

    public float getNetWorth() {
        return marketPrice * quantityAtHand;
    }
}

class Bond extends Security {

    private String name, maturityDate;
    private float investedAmount;

    public void setName(String name) {
        this.name = name;
    }

    public void setInvestedAmount(float investedAmount) {
        this.investedAmount = investedAmount;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public void printDescription() {
        System.out.println("Investments in Bonds");
        super.printDescription();
        System.out.println("Bond name: " + name);
        System.out.printf("Invested Amount: $%.02f%n", investedAmount);
        System.out.println("Maturity Date: " + maturityDate);
    }

    public float getNetWorth() {
        return investedAmount;
    }
}

class RealEstate extends Asset {

    private String name;
    private float builtUpArea;
    private float currentMarketRate;

    public void setName(String name) {
        this.name = name;
    }

    public void setBuiltUpArea(float builtUpArea) {
        this.builtUpArea = builtUpArea;
    }

    public void setCurrentMarketRate(float currentMarketRate) {
        this.currentMarketRate = currentMarketRate;
    }

    public void printDescription() {
        System.out.println("Real Estate");
        super.printDescription();
        System.out.println("Name: " + name);
        System.out.printf("Built-up Area: sq.ft. %.02f%n", builtUpArea);
        System.out.printf("Current Market Rate(per sq.ft.): $%.02f%n",
                currentMarketRate);
        System.out.printf("Net worth: $%.02f%n",
                +builtUpArea * currentMarketRate);
    }

    public float getNetWorth() {
        return builtUpArea * currentMarketRate;
    }
}

public class PortfolioManager {

    Asset[] tomAssets = new Asset[5];

    public static void main(String[] args) {
        PortfolioManager manager = new PortfolioManager();
        manager.createAssets();
        manager.printAllAssets();
        manager.printNetWorth();
    }

    private void createAssets() {
        SavingsAccount tomSavingsAccount = new SavingsAccount();
        tomSavingsAccount.setId(1001);
        tomSavingsAccount.setType("Bank Account");
        tomSavingsAccount.setBankName("Citi bank");
        tomSavingsAccount.setAccountNumber(526702);
        tomSavingsAccount.setBalance(15450.00f);
        tomSavingsAccount.setInterestRate(3.0f);
        tomAssets[0] = tomSavingsAccount;
        CheckingAccount iVisionBusinessAccount = new CheckingAccount();
        iVisionBusinessAccount.setId(1002);
        iVisionBusinessAccount.setType("Bank Account");
        iVisionBusinessAccount.setBankName("Bank of America");
        iVisionBusinessAccount.setAccountNumber(24689);
        iVisionBusinessAccount.setBalance(678256.00f);
        iVisionBusinessAccount.setOverdraftLimit(50000.00f);
        tomAssets[1] = iVisionBusinessAccount;
        Stock ibmStocks = new Stock();
        ibmStocks.setId(5001);
        ibmStocks.setType("Security");
        ibmStocks.setTradeExchangeName("NYSE");
        ibmStocks.setSymbol("IBM");
        ibmStocks.setQuantityAtHand(100);
        ibmStocks.setMarketPrice(129.61f);
        tomAssets[2] = ibmStocks;
        Bond aaplBonds = new Bond();
        aaplBonds.setId(6000);
        aaplBonds.setType("Bonds");
        aaplBonds.setTradeExchangeName("NYSE");
        aaplBonds.setName("Apple Inc");
        aaplBonds.setInvestedAmount(25000.00f);
        aaplBonds.setMaturityDate("01/01/2015");
        tomAssets[3] = aaplBonds;
        RealEstate texasEstate = new RealEstate();
        texasEstate.setId(8000);
        texasEstate.setType("Real Estate");
        texasEstate.setName("House in Texas");
        texasEstate.setBuiltUpArea(2250);
        texasEstate.setCurrentMarketRate(950.00f);
        tomAssets[4] = texasEstate;
    }

    private void printAllAssets() {
        String lineSeparator = "-------------------";
        System.out.println("Entire Portfolio");
        for (Asset asset : tomAssets) {
            System.out.println(lineSeparator);
            asset.printDescription();
        }
        System.out.println(lineSeparator);
    }

    private void printNetWorth() {
        float total = 0;
        for (Asset asset : tomAssets) {
            total += asset.getNetWorth();
        }
        System.out.println("Net Worth of Tom's entire portfolio: $" + total);
    }
}