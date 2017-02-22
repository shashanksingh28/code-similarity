// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

/**
 * This program prints out the first 20 numbers in the Fibonacci sequence.
 * Each number is formed by adding together the previous two numbers in the
 * sequence, starting with the numbers 0 and 1.
 **/
public class Fibonacci {
  public static void main(String[] args) {
    int current, prev = 1, prevprev = 0;// Initialize some variables
    for(int i = 0; i < 20; i++) {       // Loop exactly 20 times
      current = prev + prevprev;        // Next number is sum of previous two
      System.out.print(current + " ");  // Print it out
      prevprev = prev;                  // First previous becomes 2nd previous
      prev = current;                   // And current number becomes previous
    }
    System.out.println();               // Terminate the line, and flush output
  }
}
