
public class ContinueConstructApp {

    public static void main(String[] args) {
        int count = 0;
        while (true) {
            int number = (int) (Math.random() * 100);
            count++;
            if (number != 9) {
                continue;
            } else {
                System.out.println("\nNumber 9 found after "
                        + count + " iterations");
                break;
            }
        }
    }
}