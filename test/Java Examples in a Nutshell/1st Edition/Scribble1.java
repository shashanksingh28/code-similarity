// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*; 
import java.awt.*;

/** A simple applet that uses the Java 1.0 event handling model */
public class Scribble1 extends Applet {
  private int lastx, lasty;    // remember last mouse coordinates
  Button clear_button;         // the Clear button
  Graphics g;                  // A Graphics object for drawing

  /** Initialize the button and the Graphics object */
  public void init() {
    clear_button = new Button("Clear");
    this.add(clear_button);
    g = this.getGraphics();
  }
  /** Respond to mouse clicks */
  public boolean mouseDown(Event e, int x, int y) {
    lastx = x; lasty = y;
    return true;
  }
  /** Respond to mouse drags */
  public boolean mouseDrag(Event e, int x, int y) {
    g.setColor(Color.black);
    g.drawLine(lastx, lasty, x, y);
    lastx = x; lasty = y;
    return true;
  }
  /** Respond to key presses */
  public boolean keyDown(Event e, int key) {
    if ((e.id == Event.KEY_PRESS) && (key == 'c')) {
      clear();
      return true;
    }
    else return false;
  }
  /** Respond to Button clicks */
  public boolean action(Event e, Object arg) {
    if (e.target == clear_button) {
      clear();
      return true;
    }
    else return false;
  }
  /** convenience method to erase the scribble */
  public void clear() {
    g.setColor(this.getBackground());
    g.fillRect(0, 0, bounds().width, bounds().height);
  }
}
