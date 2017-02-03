
class Outer {

    private int a = 20;

    public void someMethod(final int b) {
        class Inner {

            int c = 30;

            public void innerMethod() {
                System.out.println("Formal parameter (B): " + b);
                System.out.println("Outer Class variable (A): " + a);
                System.out.println("Inner Class variable (C): " + c);
            }
        }
        new Inner().innerMethod();
    }
}

public class InnerClassWithinMethodExample {

    public static void main(String[] args) {
        Outer outer = new Outer();
        outer.someMethod(10);
    }
}