
class Outer {

    private int size = 10;

    public class Inner {

        private int size = 20;

        public void someMethod(int size) {
            System.out.println("Method parameter (size): " + size);
            System.out.println("Inner size: " + this.size);
            System.out.println("Outer size: " + Outer.this.size);
        }
    }
}

public class ShadowedVariableExample {

    public static void main(String[] args) {
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();
        inner.someMethod(5);
    }
}