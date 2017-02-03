
public class DynamicInvoker {

    public static void main(String[] args) {
        DynamicInvoker app = new DynamicInvoker();
        app.printGreeting("Jonny", 5);
        System.out.println("\nDynamic invocation of printGreeting method");
        try {
            app.getClass().getMethod("printGreeting", new Class[]{
                        Class.forName("java.lang.String"), Integer.TYPE}).
                    invoke(app, new Object[]{"Sanjay", new Integer(3)});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void printGreeting(String name, int numberOfTimes) {
        for (int i = 0; i < numberOfTimes; i++) {
            System.out.println("Hello " + name);
        }
    }
}