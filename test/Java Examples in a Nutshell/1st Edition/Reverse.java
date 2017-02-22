// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

/**
 * This program echos the command-line arguments backwards.
 **/
public class Reverse {
  public static void main(String[] args) {
    // Loop backwards through the array of arguments
    for(int i = args.length-1; i >= 0; i--) {
      // Loop backwards through the characters in each argument
      for(int j=args[i].length()-1; j>=0; j--) {
        // Print out character j of argument i.
        System.out.print(args[i].charAt(j));
      }
      System.out.print(" ");  // add a space at the end of each argument
    }
    System.out.println();     // and terminate the line when we're done.
  }
}
