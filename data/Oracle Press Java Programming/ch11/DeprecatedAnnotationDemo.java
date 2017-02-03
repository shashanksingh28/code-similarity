
public class DeprecatedAnnotationDemo {

    public static void main(String[] args) {
        MyTestClass testObject = new MyTestClass();
        testObject.doSomething();
        testObject.doSomethingNew("Bowling");
    }
}

class MyTestClass {

    @Deprecated
    public void doSomething() {
    }

    public void doSomethingNew(String SomeFun) {
    }
}