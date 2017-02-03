
public class IfConditionApp {

    public static void main(String[] args) {
        char c = ' ';
        System.out.print("Guess my fruit by entering its first letter: ");
        try {
            c = (char) System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (c == 'a' || c == 'A') {
            System.out.println("Congratulations, you just won an apple!");
        }
        if (c != 'a' && c != 'A') {
            System.out.println("Please play again! (Hint: My fruit is green)");
        }
    }
}