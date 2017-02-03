
public class ConditionalOperator {

    public static void main(String args[]) {
        int m = 10, n = 20, max;
        max = (m > n) ? m : n;
        System.out.println("max of " + m + " and " + n + ": " + max);
        n = 5;
        max = (m > n) ? m : n;
        System.out.println("max of " + m + " and " + n + ": " + max);
    }
}