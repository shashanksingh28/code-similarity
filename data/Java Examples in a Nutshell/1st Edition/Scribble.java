// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;

/**
 * This applet lets the user scribble with the mouse.  It demonstrates
 * the Java 1.0 event model.
 **/
public class Scribble extends Applet {
  private int last_x = 0, last_y = 0;  // Fields to store a point in.

  // Called when the user clicks.
  public boolean mouseDown(Event e, int x, int y) {
    last_x = x; last_y = y;            // Remember the location of the click.
    return true;
  }

  // Called when the mouse moves with the button down
  public boolean mouseDrag(Event e, int x, int y)  {
    Graphics g = getGraphics();        // Get a Graphics to draw with.
    g.drawLine(last_x, last_y, x, y);  // Draw a line from last point to this.
    last_x = x; last_y = y;            // And update the saved location.
    return true;
  }
}
