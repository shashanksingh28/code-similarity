// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

/** This class demonstrates how you might use the Rect class */
public class RectTest {
  public static void main(String[] args) {
    Rect r1 = new Rect(1, 1, 4, 4);    // Create Rect objects
    Rect r2 = new Rect(2, 3, 5, 6);
    Rect u = r1.union(r2);             // Invoke Rect methods
    Rect i = r2.intersection(r1);

    if (u.isInside(r2.x1, r2.y1))      // Use Rect fields and invoke a method
      System.out.println("(" + r2.x1 + "," + r2.y1 + ") is inside the union");

    // These string concatenations implicitly call the Rect.toString() method
    System.out.println(r1 + " union " + r2 + " = " + u);
    System.out.println(r1 + " intersect " + r2 + " = " + i);
  }
}
