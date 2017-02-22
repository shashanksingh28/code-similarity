// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/** An applet that can also run as a standalone application */
public class StandaloneScribble extends Applet {
  /**
   * The main() method.  If this program is invoked as an application, this
   * method will create the necessary window, add the applet to it, and
   * call init(), below.  Note that Frame uses a PanelLayout by default.
   */
  public static void main(String[] args) {
    Frame f = new Frame();                     // Create a window
    Applet a = new StandaloneScribble();       // Create the applet panel
    f.add(a, "Center");                        // Add applet to window
    a.init();                                  // Initialize the applet
    f.setSize(400, 400);                       // Set the size of the window
    f.show();                                  // Make the window visible
    f.addWindowListener(new WindowAdapter() {  // Handle window close requests
      public void windowClosing(WindowEvent e) { System.exit(0); }
    });
  }

  /**
   * The init() method.  If the program is invoked as an applet, the browser
   * allocates screen space for it and calls this method to set things up
   */
  public void init() {
    // Define, instantiate and register a MouseListener object
    this.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        lastx = e.getX();
        lasty = e.getY();
      }
    });

    // Define, instantiate and register a MouseMotionListener object
    this.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        Graphics g = getGraphics();
        int x = e.getX(), y = e.getY();
        g.setColor(Color.black);
        g.drawLine(lastx, lasty, x, y);
        lastx = x; lasty = y;
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

  protected int lastx, lasty;  // Coordinates of last mouse click
}
