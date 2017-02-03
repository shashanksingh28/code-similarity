
public class ArithmeticOperators {

    public static void main(String[] args) {
        int m = 20, n = 12, result;
        result = m + n;
        System.out.println("Addition: " + m + " + " + n + " = " + result);
        result = m - n;
        System.out.println("Subtraction: " + m + " - " + n + " = " + result);
        result = m * n;
        System.out.println("Multiplication: " + m + " * " + n + " = " + result);
        result = m / n;
        System.out.println("Integer Division: " + m + " / " + n + " = " + result);
        result = m % n;
        System.out.println("Remainder: " + m + " % " + n + " = " + result);
        result = -m;
        System.out.println("Unary: " + " -" + m + " = " + result);
    }
}