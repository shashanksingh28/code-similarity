
public class AssignmentOperators {

    public static void main(String[] args) {
        int m = 10, n = 5;
        System.out.printf("m = %d, n = %d %nAfter (m += n) operation "
                + "m becomes %d%n%n", m, n, (m += n));
        m = 10;
        n = 5;
        System.out.printf("m = %d, n = %d %nAfter (m -= n) operation "
                + "m becomes %d%n%n", m, n, (m -= n));
        m = 10;
        n = 5;
        System.out.printf("m = %d, n = %d %nAfter (m *= n) operation "
                + "m becomes %d%n%n", m, n, (m *= n));
        m = 10;
        n = 5;
        System.out.printf("m = %d, n = %d %nAfter (m /= n) operation "
                + "m becomes %d%n%n", m, n, (m /= n));
        m = 13;
        n = 5;
        System.out.printf("m = %d, n = %d %nAfter (m %%= n) operation "
                + "m becomes %d%n%n", m, n, (m %= n));
    }
}