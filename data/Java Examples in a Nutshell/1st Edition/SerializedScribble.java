// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.awt.*;               // ScrollPane, PopupMenu, MenuShortcut, etc.
import java.awt.event.*;         // New event model.
import java.io.*;                // Object serialization streams.
import java.util.zip.*;          // Data compression/decompression streams.
import java.util.Vector;         // To store the scribble in.

/**
 * This class demonstrates the use of object serialization to provide
 * a file format for saving application state.  It saves a user's scribbles
 * as a compressed, serialized Vector of Line objects.
 **/
public class SerializedScribble extends Frame {
  /** A very simple main() method for our program. */
  public static void main(String[] args) { new SerializedScribble(); }

  /** Create a Frame, Menu, and Scribble component */
  public SerializedScribble() {
    super("SerialziedScribble");             // Create the window.

    final Scribble scribble;
    scribble = new Scribble(this, 300, 300); // Create a bigger scribble area.
    this.add(scribble, "Center");            // Add it to the ScrollPane.

    MenuBar menubar = new MenuBar();         // Create a menubar.
    this.setMenuBar(menubar);                // Add it to the frame.
    Menu file = new Menu("File");            // Create a File menu.
    menubar.add(file);                       // Add to menubar.

    // Create three menu items, with menu shortcuts, and add to the menu.
    MenuItem load, save, quit;
    file.add(load = new MenuItem("Load"));
    file.add(save = new MenuItem("Save"));
    file.addSeparator();                     // Put a separator in the menu
    file.add(quit = new MenuItem("Quit"));

    // Create and register action listener objects for the three menu items.
    load.addActionListener(new ActionListener() {     // Open a new window
      public void actionPerformed(ActionEvent e) { scribble.load(); }
    });
    save.addActionListener(new ActionListener() {     // Close this window.
      public void actionPerformed(ActionEvent e) { scribble.save(); }
    });
    quit.addActionListener(new ActionListener() {     // Quit the program.
      public void actionPerformed(ActionEvent e) { System.exit(0); }
    });

    // Another event listener, this one to handle window close requests.
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) { System.exit(0); }
    });

    // Set the window size and pop it up.
    this.pack();
    this.show();
  }

  /**
   * This class is a custom component that supports scribbling.  Note that 
   * it extends Component rather than Canvas, making it "lightweight."
   **/
  static class Scribble extends Component {
    protected short last_x, last_y;               // Coordinates of last click.
    protected Vector lines = new Vector(256,256); // Store the scribbles.
    protected int width, height;                  // The preferred size.
    protected Frame frame;                        // The frame we are within.
    
    /** This constructor requires a Frame and a desired size */
    public Scribble(Frame frame, int width, int height) {
      this.frame = frame;
      this.width = width;
      this.height = height;
      
      // We handle scribbling with low-level events, so we must specify
      // which events we are interested in.
      this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
      this.enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);
    }
    
    /** 
     * Specifies big the component would like to be.  It always returns the
     * preferred size passed to the Scribble() constructor 
     **/
    public Dimension getPreferredSize() {return new Dimension(width, height);}
    
    /** Draw all the saved lines of the scribble */
    public void paint(Graphics g) {
      for(int i = 0; i < lines.size(); i++) {
        Line l = (Line)lines.elementAt(i);
        g.drawLine(l.x1, l.y1, l.x2, l.y2);
      }
    }
    
    /**
     * This is the low-level event-handling method called on mouse events
     * that do not involve mouse motion.
     **/
    public void processMouseEvent(MouseEvent e) {
      if (e.getID() == MouseEvent.MOUSE_PRESSED) {
        last_x = (short)e.getX(); last_y = (short)e.getY(); // Save position.
      }
      else super.processMouseEvent(e);  // Pass other event types on.
    }
    
    /**
     * This method is called for mouse motion events.  It adds a line to the
     * scribble, on screen, and in the saved representation
     **/
    public void processMouseMotionEvent(MouseEvent e) {
      if (e.getID() == MouseEvent.MOUSE_DRAGGED) {
        Graphics g = getGraphics();                     // Object to draw with.
        g.drawLine(last_x, last_y, e.getX(), e.getY()); // Draw this line
        lines.addElement(new Line(last_x, last_y,       // and save it, too.
                                  (short) e.getX(), (short)e.getY()));
        last_x = (short) e.getX();  // Remember current mouse coordinates.
        last_y = (short) e.getY();
      }
      else super.processMouseMotionEvent(e);  // Important!
    }
    
    /**
     * Prompt the user for a filename, and save the scribble in that file.
     * Serialize the vector of lines with an ObjectOutputStream.
     * Compress the serialized objects with a GZIPOutputStream.
     * Write the compressed, serialized data to a file with a FileOutputStream.
     * Don't forget to flush and close the stream.
     **/
    public void save() {
      // Create a file dialog to query the user for a filename.
      FileDialog f = new FileDialog(frame, "Save Scribble", FileDialog.SAVE);
      f.show();                        // Display the dialog and block.
      String filename = f.getFile();   // Get the user's response
      if (filename != null) {          // If user didn't click "Cancel".
        try {
          // Create the necessary output streams to save the scribble.
          FileOutputStream fos = new FileOutputStream(filename);// Save to file
          GZIPOutputStream gzos = new GZIPOutputStream(fos);    // Compressed
          ObjectOutputStream out = new ObjectOutputStream(gzos);// Save objects
          out.writeObject(lines);      // Write the entire Vector of scribbles
          out.flush();                 // Always flush the output.
          out.close();                 // And close the stream.
        }
        // Print out exceptions.  We should really display them in a dialog...
        catch (IOException e) { System.out.println(e); }
      }
    }
    
    /**
     * Prompt for a filename, and load a scribble from that file.
     * Read compressed, serialized data with a FileInputStream.
     * Uncompress that data with a GZIPInputStream.
     * Deserialize the vector of lines with a ObjectInputStream.
     * Replace current data with new data, and redraw everything.
     **/
    public void load() {
      // Create a file dialog to query the user for a filename.
      FileDialog f = new FileDialog(frame, "Load Scribble", FileDialog.LOAD);
      f.show();                         // Display the dialog and block.
      String filename = f.getFile();    // Get the user's response
      if (filename != null) {           // If user didn't click "Cancel".
        try {
          // Create necessary input streams
          FileInputStream fis = new FileInputStream(filename);// Read from file
          GZIPInputStream gzis = new GZIPInputStream(fis);    // Uncompress
          ObjectInputStream in = new ObjectInputStream(gzis); // Read objects
          // Read in an object.  It should be a vector of scribbles
          Vector newlines = (Vector)in.readObject();
          in.close();                    // Close the stream.
          lines = newlines;              // Set the Vector of lines.
          repaint();                     // And redisplay the scribble.
        }
        // Print out exceptions.  We should really display them in a dialog...
        catch (Exception e) { System.out.println(e); }
      }
    }
    
    /** 
     * A class to store the coordinates of one scribbled line.
     * The complete scribble is stored as a Vector of these objects 
     **/
    static class Line implements Serializable {
      public short x1, y1, x2, y2;
      public Line(short x1, short y1, short x2, short y2) {
        this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
      }
    }
  }
}
