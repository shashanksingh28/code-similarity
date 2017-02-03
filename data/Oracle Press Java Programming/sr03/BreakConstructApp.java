
public class BreakConstructApp {

    public static void main(String[] args) {
        int count = 0;
        while (true) {
            int number = (int) (Math.random() * 100);
            count++;
            System.out.print(number + " ");
            if (number == 0) {
                System.out.println("\nThis time, there were " + (count - 1)
                        + " random numbers generated before 0 was generated");
                break;
            }
        }
    }
}