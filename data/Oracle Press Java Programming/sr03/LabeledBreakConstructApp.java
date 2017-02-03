
public class LabeledBreakConstructApp {

    public static void main(String[] args) {
        int n = 0;
        System.out.println("List of Prime numbers");
        OuterLoop:
        for (int i = 2;; i++) {
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    continue OuterLoop;
                }
            }
            System.out.print(i + " ");
            n++;
            if (n == 10) {
                System.out.println();
                break OuterLoop;
            }
        }
    }
}