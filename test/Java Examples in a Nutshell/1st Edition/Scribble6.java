// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/** A simple applet that uses low-level event handling under Java 1.1 */
public class Scribble6 extends Applet {
  private int lastx, lasty;

  /** Tell the system we're interested in mouse events, mouse motion events,
   *  and keyboard events.  This is a required or events won't be sent.
   */
  public void init() {
    this.enableEvents(AWTEvent.MOUSE_EVENT_MASK |
                      AWTEvent.MOUSE_MOTION_EVENT_MASK |
                      AWTEvent.KEY_EVENT_MASK);
    this.requestFocus();  // Ask for keyboard focus so we get key events
  }

  /** Invoked when a mouse event of some type occurs */
  public void processMouseEvent(MouseEvent e) {
    if (e.getID() == MouseEvent.MOUSE_PRESSED) {  // check the event type
      lastx = e.getX(); lasty = e.getY();
    }
    else super.processMouseEvent(e); // pass unhandled events to our superclass
  }

  /** Invoked when a mouse motion event occurs */
  public void processMouseMotionEvent(MouseEvent e) {
    if (e.getID() == MouseEvent.MOUSE_DRAGGED) {  // check type
      int x = e.getX(), y = e.getY();
      Graphics g = this.getGraphics();
      g.drawLine(lastx, lasty, x, y);
      lastx = x; lasty = y;
    }
    else super.processMouseMotionEvent(e);
  }

  /** Called on key events:  clear the screen when 'c' is typed */
  public void processKeyEvent(KeyEvent e) {
    if ((e.getID() == KeyEvent.KEY_TYPED) && (e.getKeyChar() == 'c')) {
      Graphics g = this.getGraphics();
      g.setColor(this.getBackground());
      g.fillRect(0, 0, this.getSize().width, this.getSize().height);
    }
    else super.processKeyEvent(e);  // pass unhandled events to our superclass
  }
}
