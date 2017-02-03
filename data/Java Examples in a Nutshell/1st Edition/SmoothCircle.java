// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;

/** 
 * An applet that displays a simple animation using double-buffering 
 * and clipping
 **/
public class SmoothCircle extends Applet implements Runnable {
  int x = 150, y = 100, r=50;      // Position and radius of the circle
  int dx = 8, dy = 5;              // Trajectory of circle
  Dimension size;                  // The size of the applet
  Image buffer;                    // The off-screen image for double-buffering
  Graphics bufferGraphics;         // A Graphics object for the buffer
  Thread animator;                 // Thread that performs the animation
  boolean please_stop;             // A flag asking animation thread to stop

  /** Set up an off-screen Image for double-buffering */
  public void init() {
    size = this.size();
    buffer = this.createImage(size.width, size.height);
    bufferGraphics = buffer.getGraphics();
  }

  /** Draw the circle at its current position, using double-buffering */
  public void paint(Graphics g) {
    // Draw into the off-screen buffer.
    // Note, we could do even better clipping by setting the clip rectangle
    // of bufferGraphics to be the same as that of g.
    // In Java 1.1:  bufferGraphics.setClip(g.getClip());
    bufferGraphics.setColor(this.getBackground());
    bufferGraphics.fillRect(0, 0, size.width, size.height); // clear the buffer
    bufferGraphics.setColor(Color.red);
    bufferGraphics.fillOval(x-r, y-r, r*2, r*2);            // draw the circle

    // Then copy the off-screen buffer onto the screen
    g.drawImage(buffer, 0, 0, this);
  }

  /** Don't clear the screen; just call paint() immediately
  *   It is important to override this method like this for double-buffering */
  public void update(Graphics g) { paint(g); }

  /** The body of the animation thread */
  public void run() {
    while(!please_stop) {
      // Bounce the circle if we've hit an edge.
      if ((x - r + dx < 0) || (x + r + dx > size.width)) dx = -dx;
      if ((y - r + dy < 0) || (y + r + dy > size.height)) dy = -dy;

      // Move the circle.
      x += dx;  y += dy;

      // Ask the browser to call our paint() method to redraw the circle
      // at its new position.  Tell repaint what portion of the applet needs
      // be redrawn: the rectangle containing the old circle and the
      // the rectangle containing the new circle.  These two redraw requests
      // will be merged into a single call to paint()
      repaint(x-r-dx, y-r-dy, 2*r, 2*r);   // repaint old position of circle
      repaint(x-r, y-r, 2*r, 2*r);         // repaint new position of circle

      // Now pause 1/10th of a second before drawing the circle again.
      try { Thread.sleep(100); } catch (InterruptedException e) { ; }
    }
    animator = null;
  }

  /** Start the animation thread */
  public void start() {
    if (animator == null) {
      please_stop = false;
      animator = new Thread(this);
      animator.start();
    }
  }

  /** Stop the animation thread */
  public void stop() { please_stop = true; }

  /** Allow the user to start and stop the animation by clicking */
  public boolean mouseDown(Event e, int x, int y) {
    if (animator != null) please_stop = true;  // if running request a stop
    else start();                              // otherwise start it.
    return true;
  }
}
