// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;

/** An applet that displays a simple animation */
public class BouncingCircle extends Applet implements Animation {
  int x = 150, y = 50, r=50;    // position and radius of the circle
  int dx = 11, dy = 7;          // trajectory of circle

  /** A timer for animation: call our animate() method ever 100 
   *  milliseconds.  Creates a new thread. */
  AnimationTimer timer = new AnimationTimer(this, 100);

  /** Draw the circle at its current position */
  public void paint(Graphics g) {
    g.setColor(Color.red);
    g.fillOval(x-r, y-r, r*2, r*2);
  }

  /** Move and bounce the circle and request a redraw.
   *  The timer calls this method periodically. */
  public void animate() {
    // Bounce if we've hit an edge.
    if ((x - r + dx < 0) || (x + r + dx > bounds().width)) dx = -dx;
    if ((y - r + dy < 0) || (y + r + dy > bounds().height)) dy = -dy;
    // Move the circle.
    x += dx;  y += dy;
    // Ask the browser to call our paint() method to draw the circle
    // at its new position.
    repaint();
  }

  /** Start the timer when the browser starts the applet */
  public void start() { timer.start_animation(); }

  /** Pause the timer when browser pauses the applet */
  public void stop() { timer.pause_animation(); }
}

/** This interface for objects that can be animated by an AnimationTimer */
interface Animation { public void animate(); }

/** The thread class that periodically calls the animate() method */
class AnimationTimer extends Thread {
  Animation animation;  // The animation object we're serving as timer for
  int delay;            // How many milliseconds between "animation frames"

  public AnimationTimer(Animation animation, int delay) {
    this.animation = animation;
    this.delay = delay;
  }

  public void start_animation() {
    if (isAlive()) super.resume();
    else start();
  }
  public void pause_animation() { suspend(); }

  /** Loop forever, calling animate(), and then pausing the specified time. */
  public void run() {
    for(;;) {
      animation.animate();
      try { Thread.sleep(delay); } catch (InterruptedException e) { ; }
    }
  }
}
