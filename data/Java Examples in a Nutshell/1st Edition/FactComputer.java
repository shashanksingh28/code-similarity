// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

/**
 * This program computes and displays the factorial of a number specified
 * on the command line.  It handles possible user input errors with try/catch.
 **/
public class FactComputer {
  public static void main(String[] args) {
    // Try to compute a factorial.  If something goes wrong, handle it below.
    try {
      int x = Integer.parseInt(args[0]);
      System.out.println(x + "! = " + Factorial4.factorial(x));
    }
    // The user forgot to specify an argument.  Thrown if args[0] is undefined.
    catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("You must specify an argument");
      System.out.println("Usage: java FactComputer <number>");
    }
    // The argument is not a number.  Thrown by Integer.parseInt().
    catch (NumberFormatException e) {
      System.out.println("The argument you specify must be an integer");
    }
    // The argument is < 0.  Thrown by Factorial4.factorial()
    catch (IllegalArgumentException e) {
      // Display the message sent by the factorial() method:
      System.out.println("Bad argument: " + e.getMessage());
    }
  }
}
