// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

/**
 * This class computes factorials and caches the results in a table for reuse.
 * 20! is as high as we can go using the long data type, so check the argument
 * passed and "throw an exception" if it is too big or too small.
 **/
public class Factorial3 {
  // Create an array to cache values 0! through 20!.
  static long[] table = new long[21];
  // A "static initializer": initialize the first value in the array
  static { table[0] = 1; }  // factorial of 0 is 1.
  // Remember the highest initialized value in the array
  static int last = 0;

  public static long factorial(int x) throws IllegalArgumentException {
    // Check if x is too big or too small.  Throw an exception if so.
    if (x >= table.length)   // ".length" returns length of any array
      throw new IllegalArgumentException("Overflow; x is too large.");
    if (x < 0) throw new IllegalArgumentException("x must be non-negative.");

    // Compute and cache any values that are not yet cached.
    while(last < x) {
      table[last + 1] = table[last] * (last + 1);
      last++;
    }
    // Now return the cached factorial of x.
    return table[x];
  }
}
