// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

/**
 * This class defines methods for computing pseudo-random numbers, and it defines
 * the state variable that needs to be maintained for use by those methods.
 **/
public class Randomizer {
  // Carefully chosen constants from the book "Numerical Recipes in C".
  // All "static final" fields are constants.
  static final int m = 233280;
  static final int a = 9301;
  static final int c = 49297;

  // The state variable maintained by each Randomizer instance
  long seed = 1;
  
  /** 
   * The constructor for the Randomizer() class.  It must be passed some
   * arbitrary initial value or "seed" for its pseudo-randomness.
   **/
  public Randomizer(long seed) { this.seed = seed; }

  /**
   * This method computes a pseudo-random number between 0 and 1 using a very
   * simple algorithm.  Math.random() and java.util.Random are actually a lot
   * better at computing randomness.
   **/
  public float randomFloat() {
    seed = (seed * a + c) % m;
    return (float)seed/(float)m;
  }

  /** 
   * This method computes a pseudo-random integer between 0 and specified
   * maximum.  It uses randomFloat() above.
   **/
  public int randomInt(int max) {
    return Math.round(max * randomFloat());
  }

  /**
   * This nested class is a simple test program: it prints 10 random integers.
   * Note how the Randomizer object is seeded using the current time.
   **/
  public static class Test {
    public static void main(String[] args) {
      Randomizer r = new Randomizer(new java.util.Date().getTime());
      for(int i = 0; i < 10; i++) System.out.println(r.randomInt(100));
    }
  }
}
