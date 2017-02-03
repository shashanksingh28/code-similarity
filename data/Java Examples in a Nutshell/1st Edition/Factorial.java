// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

/**
 * This class doesn't define a main() method, so it isn't a program by itself.
 * It does define a useful method that we can use in other programs, though.
 **/
public class Factorial {
  /** Compute and return x!, the factorial of x */
  public static int factorial(int x) {
    int fact = 1;
    for(int i = 2; i <= x; i++)    // loop
      fact *= i;                   // shorthand for: fact = fact * i;
    return fact;
  }
}
