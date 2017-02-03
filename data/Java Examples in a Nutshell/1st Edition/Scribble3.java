// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/** 
 * A simple applet that uses external classes to implement 
 * the Java 1.1 event handling model 
 **/
public class Scribble3 extends Applet {
  int last_x;
  int last_y;

  public void init() {
    MouseListener ml = new MyMouseListener(this);
    MouseMotionListener mml = new MyMouseMotionListener(this);

    // Tell this component what MouseListener and MouseMotionListener
    // objects to notify when mouse and mouse motion events occur.
    this.addMouseListener(ml);
    this.addMouseMotionListener(mml);
  }
}

class MyMouseListener extends MouseAdapter {
  private Scribble3 scribble;
  public MyMouseListener(Scribble3 s) { scribble = s; }
  public void mousePressed(MouseEvent e)  {
    scribble.last_x = e.getX();
    scribble.last_y = e.getY();
  }
}

class MyMouseMotionListener extends MouseMotionAdapter {
  private Scribble3 scribble;
  public MyMouseMotionListener(Scribble3 s) { scribble = s; }
  public void mouseDragged(MouseEvent e) {
    Graphics g = scribble.getGraphics();
    int x = e.getX(), y = e.getY();
    g.drawLine(scribble.last_x, scribble.last_y, x, y);
    scribble.last_x = x; scribble.last_y = y;
  }
}
