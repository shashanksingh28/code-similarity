
public class RelationalOperators {

    public static void main(String[] args) {
        int m = 0, n = 0;
        System.out.println(
                "Enter two characters without a space and hit return");
        try {
            m = System.in.read();
            n = System.in.read();
        } catch (Exception ex) {
// provide an exception handler here
        }
        System.out.println("Character " + (char) m + " has ASCII value = " + m);
        System.out.println("Character " + (char) n + " has ASCII value = " + n);
        System.out.printf("(%c > %c)returns ", m, n);
        System.out.println(m > n);
        System.out.printf("(%c < %c)returns ", m, n);
        System.out.println(m < n);
        System.out.printf("(%c >= %c)returns ", m, n);
        System.out.println(m >= n);
        System.out.printf("(%c <= %c)returns ", m, n);
        System.out.println(m <= n);
        System.out.printf("(%c != %c)returns ", m, n);
        System.out.println(m != n);
        System.out.printf("(%c == %c)returns ", m, n);
        System.out.println(m == n);
    }
}