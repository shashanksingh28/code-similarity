
import java.io.*;

public class SwitchUsingStringsApp {

    public static void main(String[] args) {
        String strInput = null;
        System.out.print("Guess my fruit by entering its first letter: ");
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));
            strInput = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        strInput = strInput.toLowerCase();
        switch (strInput) {
            case "a":
                System.out.println("Congratulations, you just won an apple!");
                break;
            case "b":
                System.out.println("Congratulations, you just won a banana!");
                break;
            case "c":
                System.out.println("Congratulations, you just won a cherry!");
                break;
            case "o":
                System.out.println("Congratulations, you just won an orange!");
                break;
            case "p":
                System.out.println("Congratulations, you just won a pear!");
                break;
            default:
                System.out.println("Please play again! (Hint: a, b, c, o, p)");
        }
    }
}