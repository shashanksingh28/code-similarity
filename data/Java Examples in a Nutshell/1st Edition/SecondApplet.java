// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;

/**
 * This applet spices up "Hello World" with graphics, colors, and a font.
 **/
public class SecondApplet extends Applet {
  static final String message = "Hello World";
  private Font font;

  // One-time initialization for the applet
  // Note: no constructor defined.
  public void init() {
    font = new Font("Helvetica", Font.BOLD, 48);
  }

  // Draw the applet whenever necessary.  Do some fancy graphics.
  public void paint(Graphics g) {
    // The pink oval
    g.setColor(Color.pink);
    g.fillOval(10, 10, 330, 100);

    // The red outline. Java doesn't support wide lines, so we
    // try to simulate a 4-pixel wide line by drawing four ovals.
    g.setColor(Color.red);
    g.drawOval(10,10, 330, 100);
    g.drawOval(9, 9, 332, 102);
    g.drawOval(8, 8, 334, 104);
    g.drawOval(7, 7, 336, 106);

    // The text
    g.setColor(Color.black);
    g.setFont(font);
    g.drawString(message, 40, 75);
  }
}
