
public class FibonacciGenerator {

    public static void main(String[] args) {
        generate(3);
    }

    public static int generate(int n) {
        Throwable t = new Throwable();
        StackTraceElement[] frames = t.getStackTrace();
        for (StackTraceElement frame : frames) {
            System.out.println("Calling: " + frame.getMethodName());
        }
        if (n <= 2) {
            return 1;
        } else {
            return generate(n - 1) + generate(n - 2);
        }
    }
}