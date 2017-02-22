// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

/**
 * This class shows a recursive method to compute factorials.  This method
 * calls itself repeatedly based on the formula: n! = n * (n-1)!
 **/
public class Factorial2 {
  public static long factorial(long x) {
    if (x == 1) return 1;
    else return x * factorial(x-1);
  }
}
