// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/** 
 * A simple applet that uses anonymous inner classes to implement 
 * the Java 1.1 event handling model 
 **/
public class Scribble4 extends Applet {
  int last_x, last_y;

  public void init() {
    // Define, instantiate and register a MouseListener object
    this.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        last_x = e.getX();
        last_y = e.getY();
      }
    });

    // Define, instantiate and register a MouseMotionListener object
    this.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        Graphics g = getGraphics();
        int x = e.getX(), y = e.getY();
        g.setColor(Color.black);
        g.drawLine(last_x, last_y, x, y);
        last_x = x; last_y = y;
      }
    });

    // Create a clear button
    Button b = new Button("Clear");
    // Define, instantiate, and register a listener to handle button presses
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {  // clear the scribble
        Graphics g = getGraphics();
        g.setColor(getBackground());
        g.fillRect(0, 0, getSize().width, getSize().height);
      }
    });
    // And add the button to the applet
    this.add(b);
  }
}
