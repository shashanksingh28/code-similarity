// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.io.*; // Import all classes in java.io package.  Saves typing.

/**
 * This program displays factorials as the user enters values interactively
 **/
public class FactQuoter {
  public static void main(String[] args) throws IOException {
    // This is how we set things up to read lines of text from the user.
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    // Loop forever
    for(;;) {
      // Display a prompt to the user
      System.out.print("FactQuoter> ");
      // Read a line from the user
      String line = in.readLine();
      // If we reach the end-of-file, or if the user types "quit", then quit
      if ((line == null) || line.equals("quit")) break;
      // Try to parse the user's input, and compute and print the factorial
      try { 
        int x = Integer.parseInt(line);
        System.out.println(x + "! = " + Factorial4.factorial(x)); 
      }
      // If anything goes wrong, display a generic error message
      catch(Exception e) { System.out.println("Invalid Input"); }
    }
  }
}
