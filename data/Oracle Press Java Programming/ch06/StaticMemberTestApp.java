
class StaticMemberTestApp {

    private static int i;
    private int j;

    public static void staticMethod() {
// do something
    }

    public void nonStaticMethod() {
// do something else
    }

    public static void main(String[] args) {
        i = 5;
        j = 10; // this does not compile
        staticMethod();
        nonStaticMethod(); // this does not compile
    }
}