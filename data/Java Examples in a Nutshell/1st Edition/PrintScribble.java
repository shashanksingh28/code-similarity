// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/** An application that can print the user's scribbles */
public class PrintScribble extends Frame {
  private short last_x = 0, last_y = 0;              // last click posistion
  private Vector lines = new Vector(256,256);        // store the scribble
  private Properties printprefs = new Properties();  // store user preferences

  public PrintScribble() {
    super("PrintScribble");

    // Add a print button.
    this.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
    Button b = new Button("Print");
    this.add(b);

    // Call the print() method when the button is clicked.
    // Note anonymous class.
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) { print(); }
    });

    // Exit when the user closes the window.
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) { System.exit(0); }
    });

    // Register other event types we're interested in -- for scribbling
    enableEvents(AWTEvent.MOUSE_EVENT_MASK |
                 AWTEvent.MOUSE_MOTION_EVENT_MASK);

    // Set our initial size and pop the window up.
    this.setSize(400, 400);
    this.show();
  }

  /** Redraw (or print) the scribble based on stored lines */
  public void paint(Graphics g)
  {
    for(int i = 0; i < lines.size(); i++) {
      Line l = (Line)lines.elementAt(i);
      g.drawLine(l.x1, l.y1, l.x2, l.y2);
    }
  }

  /** Print out the scribble */
  void print() {
    // Obtain a PrintJob and a Graphics object to use with it
    Toolkit toolkit = this.getToolkit();
    PrintJob job = toolkit.getPrintJob(this, "PrintScribble", printprefs);
    if (job == null) return; // If the user clicked Cancel in the print dialog
    Graphics g = job.getGraphics();

    // Give the output a larger top and left margin.  Otherwise it will
    // be scrunched up in the upper-left corner of the page.
    g.translate(100, 100);

    // Draw a border around the output area.
    Dimension size = this.getSize();
    g.drawRect(-1, -1, size.width+1, size.height+1);

    // Set a clipping region so our scribbles don't go outside the border
    // On-screen this happens automatically, but not on paper.
    g.setClip(0, 0, size.width, size.height);

    // Print this component and all components it contains
    this.printAll(g); // Use print() if you don't want the button to show

    // Finish up.
    g.dispose();      // End the page
    job.end();        // End the job
  }

  /** Called when the user clicks */
  public void processMouseEvent(MouseEvent e)
  {
    if (e.getID() == MouseEvent.MOUSE_PRESSED) {
      last_x = (short)e.getX();                  // remember click position
      last_y = (short)e.getY();
    }
    else super.processMouseEvent(e);
  }

  /** Called when the the user drags the mouse: does the scribbling */
  public void processMouseMotionEvent(MouseEvent e)
  {
    if (e.getID() == MouseEvent.MOUSE_DRAGGED) {
      Graphics g = getGraphics();
      g.drawLine(last_x, last_y, e.getX(), e.getY());  // draw the line
      lines.addElement(new Line(last_x, last_y,        // and save the line
                                (short) e.getX(), (short)e.getY()));
      last_x = (short) e.getX();  last_y = (short) e.getY();
    }
    else super.processMouseMotionEvent(e);
  }

  /** The main method.  Create a PrintScribble() object and away we go! */
  public static void main(String[] args)
  {
    PrintScribble s = new PrintScribble();
  }

  /** This nested toplevel helper class stores the coordinates
   *  of one line of the scribble. */
  class Line {
    public short x1, y1, x2, y2;
    public Line(short x1, short y1, short x2, short y2) {
      this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
    }
  }
}
