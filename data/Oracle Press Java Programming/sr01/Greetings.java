/*
 * Greetings.java
 */

public class Greetings {

    public static void main(String args[]) {
        try {
            byte buffer[] = new byte[100];
            System.out.print("Enter your name: ");
            System.in.read(buffer);
            System.out.println("Hello " + new String(buffer));
        } catch (Exception e) {
// catch the exception as good practice
// I will discuss exception-handling in chapter 08.
        }
    }
}