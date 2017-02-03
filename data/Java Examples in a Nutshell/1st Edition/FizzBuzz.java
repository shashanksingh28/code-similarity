// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

/**
 * This program plays the game "Fizzbuzz".  It counts to 100, replacing each
 * multiple of 5 with the word "fizz", each multiple of 7 with the word "buzz",
 * and each multiple of both with the word "fizzbuzz".  It uses the modulo
 * operator (%) to determine if a number is divisible by another.
 **/
public class FizzBuzz {                      // Everything in Java is a class
  public static void main(String[] args) {   // Every program must have main()
    for(int i = 1; i <= 100; i++) {                    // count from 1 to 100
      if (((i % 5) == 0) && ((i % 7) == 0))            // A multiple of both?
        System.out.print("fizzbuzz");    
      else if ((i % 5) == 0) System.out.print("fizz"); // else a multiple of 5?
      else if ((i % 7) == 0) System.out.print("buzz"); // else a multiple of 7?
      else System.out.print(i);                        // else just print it
      System.out.print(" "); 
    }
    System.out.println();
  }
}
