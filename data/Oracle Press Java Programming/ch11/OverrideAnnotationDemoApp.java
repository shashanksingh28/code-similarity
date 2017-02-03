
public class OverrideAnnotationDemoApp {

    public static void main(String[] args) {
        Cat c = new Cat();
        c.saySomething();
    }
}

class Animal {

    void saySomething() {
        System.out.println("Animal talking");
    }
}

class Cat extends Animal {

    @Override
    void saySomething() {
        System.out.println("meow... meow");
    }
}