
class Outer {

    private int counter = 0;

    public class Inner {

        public void someMethod() {
            counter++;
        }
    }

    public int getCount() {
        return counter;
    }
}

public class InnerClassExample {

    public static void main(String[] args) {
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();
        inner.someMethod();
        System.out.println("Counter: " + outer.getCount());
        inner.someMethod();
        System.out.println("Counter: " + outer.getCount());
    }
}