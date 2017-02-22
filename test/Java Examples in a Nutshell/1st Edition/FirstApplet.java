// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;   // Don't forget this import statement!
import java.awt.*;      // Or this one for the graphics!

/** This applet just says "Hello World! */
public class FirstApplet extends Applet {
  // This method displays the applet.
  // The Graphics class is how you do all drawing in Java.
  public void paint(Graphics g) {
    g.drawString("Hello World", 25, 50);
  }
}
