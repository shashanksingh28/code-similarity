
public class DoWhileConstructApp {

    public static void main(String[] args) {
        char c;
        try {
            do {
                System.out.println("Enter your choice (A for Apple, "
                        + "B for Banana, C for Cherry, Q to quit): ");
                c = (char) System.in.read();
                switch (c) {
                    case 'a':
                    case 'A':
                        System.out.println("Okay, have your apple");
                        break;
                    case 'b':
                    case 'B':
                        System.out.println("Okay, have your banana");
                        break;
                    case 'c':
                    case 'C':
                        System.out.println("Okay, have your cherry");
                        break;
                    default:
                        System.out.println("You did not select the fruit");
                }
                System.in.read();
                System.in.read();
            } while ((c != 'q') && (c != 'Q'));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}