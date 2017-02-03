
public class LogicalOperators {

    public static void main(String[] args) {
        int m = 5, n = 2;
        System.out.print("(m == n)&&(m == m) returns ");
        System.out.println((m == n) && (m == m));
        System.out.print("(m == n)||(m == m)) returns ");
        System.out.println((m == n) || (m == m));
        System.out.print("!(m == m) returns ");
        System.out.println(!(m == m));
    }
}