
public class IncDecOperators {

    public static void main(String[] args) {
        int m = 10, n = 10;
        System.out.println("Using increment operator:");
        System.out.println("Pre-increment: m = ++n");
        System.out.println("Initial value of n: " + n);
        m = ++n;
        System.out.println("Value of m now: " + m);
        System.out.println("Value of n now: " + n);
        m = 10;
        n = 10;
        System.out.println("Post-increment: m = n++");
        System.out.println("Initial value of n: " + n);
        m = n++;
        System.out.println("Value of m now: " + m);
        System.out.println("Value of n now: " + n);
        m = 10;
        n = 10;
        System.out.println("\nUsing decrement operator:");
        System.out.println("Pre-decrement: m = --n");
        System.out.println("Initial value of n: " + n);
        m = --n;
        System.out.println("Value of m now: " + m);
        System.out.println("Value of n now: " + n);
        m = 10;
        n = 10;
        System.out.println("Post-decrement: m = n--");
        System.out.println("Initial value of n: " + n);
        m = n--;
        System.out.println("Value of m now: " + m);
        System.out.println("Value of n now: " + n);
    }
}