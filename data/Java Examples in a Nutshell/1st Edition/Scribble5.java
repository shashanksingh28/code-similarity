// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/** The application class.  Processes high-level commands sent by GUI */
public class Scribble5 {
  /** main entry point.  Just create an instance of this application class */
  public static void main(String[] args) { new Scribble5(); }

  /** Application constructor:  create an instance of our GUI class */
  public Scribble5() { window = new ScribbleGUI(this); }
  protected Frame window;

  /** This is the application method that processes commands sent by the GUI */
  public void doCommand(String command) {
    if (command.equals("clear")) {          // clear the GUI window
      // It would be more modular to include this functionality in the GUI
      // class itself.  But for demonstration purposes, we do it here.
      Graphics g = window.getGraphics();
      g.setColor(window.getBackground());
      g.fillRect(0, 0, window.getSize().width, window.getSize().height);
    }
    else if (command.equals("print")) {}    // not yet implemented
    else if (command.equals("quit")) {      // quit the application
      window.dispose();                         // close the GUI
      System.exit(0);                           // and exit.
    }
  }
}

/** This class implements the GUI for our application */
class ScribbleGUI extends Frame {
  int lastx, lasty;   // remember last mouse click
  Scribble5 app;      // A reference to the application, to send commands to.

  /**
   * The GUI constructor does all the work of creating the GUI and setting
   * up event listeners.  Note the use of local and anonymous classes.
   */
  public ScribbleGUI(Scribble5 application) {
    super("Scribble");        // Create the window
    app = application;        // Remember the application reference

    // Create three buttons
    Button clear = new Button("Clear");
    Button print = new Button("Print");
    Button quit = new Button("Quit");

    // Set a LayoutManager, and add the buttons to the window.
    this.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
    this.add(clear); this.add(print);  this.add(quit);

    // Here's a local class used for action listeners for the buttons
    class ScribbleActionListener implements ActionListener {
      private String command;
      public ScribbleActionListener(String cmd) { command = cmd; }
      public void actionPerformed(ActionEvent e) { app.doCommand(command); }
    }

    // Define action listener adapters that connect the  buttons to the app
    clear.addActionListener(new ScribbleActionListener("clear"));
    print.addActionListener(new ScribbleActionListener("print"));
    quit.addActionListener(new ScribbleActionListener("quit"));

    // Handle the window close request similarly
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) { app.doCommand("quit"); }
    });

    // High-level action events are passed to the application, but we
    // still handle scribbling right here.  Register a MouseListener object.
    this.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        lastx = e.getX(); lasty = e.getY();
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

    // Finally, set the size of the window, and pop it up
    this.setSize(400, 400);
    this.show();
  }
}
