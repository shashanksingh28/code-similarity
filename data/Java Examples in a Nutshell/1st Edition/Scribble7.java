// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/** A simple applet that uses low-level event handling under Java 1.1 */
public class Scribble7 extends Applet {
  private int lastx, lasty;

  /** Specify the event types we care about, and ask for keyboard focus */
  public void init() {
    this.enableEvents(AWTEvent.MOUSE_EVENT_MASK |
                      AWTEvent.MOUSE_MOTION_EVENT_MASK |
                      AWTEvent.KEY_EVENT_MASK);
    this.requestFocus();  // Ask for keyboard focus so we get key events
  }

  /**
   * Called when an event arrives.  Do the right thing based on the event
   * type.  Pass unhandled events to the superclass for possible processing
   */
  public void processEvent(AWTEvent e) {
    MouseEvent me;
    Graphics g;
    switch(e.getID()) {
    case MouseEvent.MOUSE_PRESSED:
      me = (MouseEvent)e;
      lastx = me.getX(); lasty = me.getY();
      break;
    case MouseEvent.MOUSE_DRAGGED:
      me = (MouseEvent)e;
      int x = me.getX(), y = me.getY();
      g = this.getGraphics();
      g.drawLine(lastx, lasty, x, y);
      lastx = x; lasty = y;
      break;
    case KeyEvent.KEY_TYPED:
      if (((KeyEvent)e).getKeyChar() == 'c') {
        g = this.getGraphics();
        g.setColor(this.getBackground());
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
      }
      else super.processEvent(e);
      break;
    default: super.processEvent(e); break;
    }
  }
}
