// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

// Import some other classes we'll use in this example.
// Once we import a class, we don't have to type its full name.
import java.math.BigInteger;  // Import BigInteger from java.math package
import java.util.*;  // Import all classes (including Vector) from java.util

/**
 * This version of the program uses arbitrary precision integers, so it does
 * not have an upper-bound on the values it can compute.  It uses a Vector
 * object to cache computed values instead of a fixed-size array.  A Vector
 * is like an array, but can grow to any size.  The factorial() method is
 * declared "synchronized" so that it can be safely used in multi-threaded
 * programs.  Look up java.math.BigInteger and java.util.Vector while 
 * studying this class.
 **/
public class Factorial4 {
  protected static Vector table = new Vector();       // create cache
  static { table.addElement(BigInteger.valueOf(1)); } // initialize 1st element

  /** The factorial() method, using BigIntegers cached in a Vector */
  public static synchronized BigInteger factorial(int x) {
    if (x < 0) throw new IllegalArgumentException("x must be non-negative.");
    for(int size = table.size(); size <= x; size++) {
      BigInteger lastfact = (BigInteger)table.elementAt(size-1);
      BigInteger nextfact = lastfact.multiply(BigInteger.valueOf(size));
      table.addElement(nextfact);
    }
    return (BigInteger) table.elementAt(x);
  }

  /**
   * A simple main() method that we can use as a standalone test program
   * for our factorial() method.  
   **/
  public static void main(String[] args) {
    for(int i = 1; i <= 50; i++) System.out.println(i + "! = " + factorial(i));
  }
}
