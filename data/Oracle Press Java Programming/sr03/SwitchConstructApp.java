
public class SwitchConstructApp {

    public static void main(String[] args) {
        char c = ' ';
        System.out.print("Guess my fruit by entering its first letter: ");
        try {
            c = (char) System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (c) {
            case 'a':
            case 'A':
                System.out.println("Congratulations, you just won an apple!");
                break;
            case 'b':
            case 'B':
                System.out.println("Congratulations, you just won a banana!");
                break;
            case 'c':
            case 'C':
                System.out.println("Congratulations, you just won a cherry!");
                break;
            case 'o':
            case 'O':
                System.out.println("Congratulations, you just won an orange!");
                break;
            case 'p':
            case 'P':
                System.out.println("Congratulations, you just won a pear!");
                break;
            default:
                System.out.println("Please play again! (Hint: a, b, c, o, p)");
        }
    }
}