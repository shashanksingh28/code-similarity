
public class StocksEODParser {

    private static String trade = "IBM,09/09/2009,87,100,80,95,1567823";

    public static void main(String[] args) {
// retrieving a substring
        String dateField = trade.substring(4, 14);
        System.out.println("Substring field date equals " + dateField);
// locating a character sequence
        if (trade.contains("09/09/2009")) {
            System.out.println("This is a trade on 09/09/2009");
        }
// replacing a character
        String str = trade.replace(',', ':');
        System.out.println("After replacing delimiter: " + str);
// replacing a character sequence
        str = trade.replace("100", "101");
        System.out.println("After replacing trade price 100: " + str);
        System.out.println("Splitting string into its fields");
        String[] fields = trade.split(",");
        for (String strFields : fields) {
            System.out.println("\t" + strFields);
        }
        float hilowDifference =
                Float.parseFloat(fields[3]) - Float.parseFloat(fields[4]);
        str = String.valueOf(hilowDifference);
        System.out.println("Difference in Hi to Low price: $" + str);
        System.out.println(String.format(
                "Formatted HiLow Difference: $%.02f", hilowDifference));
    }
}