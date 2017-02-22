// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.awt.*;               
import java.awt.event.*;
import java.awt.datatransfer.*;  // Clipboard, Transferable, DataFlavor, etc.
import java.util.Vector;         // To store the scribble in

/** 
 * This class demonstrates how to implement cut-and-paste of data 
 * other than strings.  It is a variant of the Scribble program we've
 * seen so much.  Only about a third of this code is directly cut-and-paste
 * code.  The rest is support code to make this an interesting example
 **/
public class ScribbleCutAndPaste extends Frame {
  /** A very simple main() method for our program. */
  public static void main(String[] args) { new ScribbleCutAndPaste(); }

  /** 
   * Remember # of open windows so we can quit when the last one is closed 
   * We support multiple windows so that we can cut-and-paste among them.
   **/
  protected static int num_windows = 0;

  /** Create a Frame, Menu, and ScrollPane for the scribble component */
  public ScribbleCutAndPaste() {
    super("ScribbleCutAndPaste");            // Create the window
    num_windows++;                           // Count it

    // Create scribble area and add to the frame
    ScribblePanel scribble = new ScribblePanel(this, 400, 300); 
    this.add(scribble, "Center");

    // Set up a menubar
    MenuBar menubar = new MenuBar();         // Create menubar
    this.setMenuBar(menubar);                // Add it to the frame
    Menu file = new Menu("File");            // Create a File menu
    menubar.add(file);                       // Add to menubar

    // Create three menu items, with menu shortcuts, and add to the menu
    MenuItem n, c, q;
    file.add(n = new MenuItem("New Window", new MenuShortcut(KeyEvent.VK_N)));
    file.add(c = new MenuItem("Close Window",new MenuShortcut(KeyEvent.VK_W)));
    file.addSeparator();
    file.add(q = new MenuItem("Quit", new MenuShortcut(KeyEvent.VK_Q)));

    // Create and register action listener objects for the three menu items
    n.addActionListener(new ActionListener() {     // Open a new window
      public void actionPerformed(ActionEvent e) { new ScribbleCutAndPaste(); }
    });
    c.addActionListener(new ActionListener() {     // Close this window
      public void actionPerformed(ActionEvent e) { close(); }
    });
    q.addActionListener(new ActionListener() {     // Quit the program
      public void actionPerformed(ActionEvent e) { System.exit(0); }
    });

    // Another event listener, this one to handle window close requests
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) { close(); }
    });

    // Set the window size and pop it up
    this.pack();
    this.show();
  }

  /** Close a window.  If this is the last open window, just quit. */
  void close() {
    if (--num_windows == 0) System.exit(0);
    else this.dispose();
  }

  /**
   * This class is a custom component that supports scribbling.  It also has
   * a popup menu that provides access to cut-and-paste facilities.
   **/
  static class ScribblePanel extends Canvas implements ActionListener {
    protected short last_x, last_y;                // Coordinates of last click
    protected Vector lines = new Vector(256,256);  // Store the scribbles
    protected int width, height;                   // The preferred size
    protected PopupMenu popup;                     // The popup menu
    protected Frame frame;                         // The frame we are within
    
    /** This constructor requires a Frame and a desired size */
    public ScribblePanel(Frame frame, int width, int height) {
      this.frame = frame;
      this.width = width;
      this.height = height;
      
      // We handle scribbling with low-level events, so we must specify
      // which events we are interested in.
      this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
      this.enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);

      // Create the popup menu.
      String[] labels = new String[] {   "Clear", "Cut", "Copy", "Paste" };
      String[] commands = new String[] { "clear", "cut", "copy", "paste" };
      popup = new PopupMenu();                   // Create the menu
      for(int i = 0; i < labels.length; i++) {
        MenuItem mi = new MenuItem(labels[i]);   // Create a menu item 
        mi.setActionCommand(commands[i]);        // Set its action command
        mi.addActionListener(this);              // And its action listener
        popup.add(mi);                           // Add item to the popup menu
      }
      // Finally, register the popup menu with the component it appears over
      this.add(popup);
    }
    
    /** 
     * Specifies how big the component would like to be.  It always returns the
     * preferred size passed to the ScribblePanel() constructor 
     **/
    public Dimension getPreferredSize() {return new Dimension(width, height);}
    
    /** This is the ActionListener method invoked by the popup menu items */
    public void actionPerformed(ActionEvent event) {
      String command = event.getActionCommand();
      if (command.equals("clear")) clear();
      else if (command.equals("cut")) cut();
      else if (command.equals("copy")) copy();
      else if (command.equals("paste")) paste();
    }
    
    /** Draw all the saved lines of the scribble */
    public void paint(Graphics g) {
      for(int i = 0; i < lines.size(); i++) {
        Line l = (Line)lines.elementAt(i);
        g.drawLine(l.x1, l.y1, l.x2, l.y2);
      }
    }
    
    /** 
     * This is the low-level event-handling method called on mouse events 
     * that do not involve mouse motion.  It handles posting the popup menu
     * and also initiates scribbles
     **/
    public void processMouseEvent(MouseEvent e) {
      if (e.isPopupTrigger())                               // If popup trigger,
        popup.show(this, e.getX(), e.getY());               // Pop up the menu
      else if (e.getID() == MouseEvent.MOUSE_PRESSED) {     // Otherwise
        last_x = (short)e.getX(); last_y = (short)e.getY(); // Save position
      }
      else super.processMouseEvent(e);  // Pass other event types on
    }
    
    /**
     * This method is called for mouse motion events.  It adds a line to the
     * scribble, both on the screen and in the saved representation
     **/
    public void processMouseMotionEvent(MouseEvent e) {
      if (e.getID() == MouseEvent.MOUSE_DRAGGED) {
        Graphics g = getGraphics();                      // Object to draw with
        g.drawLine(last_x, last_y, e.getX(), e.getY());  // Draw this line
        lines.addElement(new Line(last_x, last_y,        // And save it, too.
                                  (short) e.getX(), (short)e.getY()));
        last_x = (short) e.getX();  // Remember current mouse coordinates
        last_y = (short) e.getY(); 
      }
      else super.processMouseMotionEvent(e);  // Important!
    }
    
    /** Clear the scribble.  Invoked by popup menu */
    void clear() {
      lines.removeAllElements();   // Throw out the saved scribble
      repaint();                   // And redraw everything.
    }
    
    /** 
     * The DataFlavor used for our particular type of cut-and-paste data.
     * This one will transfer data in the form of a serialized Vector object.
     * Note that in Java 1.1.1, this works intra-application, but not between
     * applications.  Java 1.1.1 inter-application data transfer is limited to
     * the pre-defined string and text data flavors.
     **/
    public static final DataFlavor dataFlavor =
        new DataFlavor(Vector.class, "ScribbleVectorOfLines");
    
    /** 
     * Copy the current scribble and store it in a SimpleSelection object
     * (defined below).  Then put that object on the clipboard for pasting.
     **/
    public void copy() {
      // Get system clipboard
      Clipboard c = this.getToolkit().getSystemClipboard();
      // Copy and save the scribble in a Transferable object
      SimpleSelection s = new SimpleSelection(lines.clone(), dataFlavor);
      // Put that object on the clipboard
      c.setContents(s, s);
    }
    
    /** Cut is just like a copy, except we erase the scribble afterwards */
    public void cut() { copy(); clear();  }
    
    /** 
     * Ask for the Transferable contents of the system clipboard.
     * Then ask that Transferable object for the scribble data it represents.  
     * If either step fails, beep!
     **/
    public void paste() {
      Clipboard c = this.getToolkit().getSystemClipboard(); // Get clipboard
      Transferable t = c.getContents(this);                 // Get its contents
      if (t == null) {              // If there is nothing to paste, beep
        this.getToolkit().beep();
        return;
      }
      try { 
        // Ask for clipboard contents to be converted to our data flavor.
        // This will throw an exception if our flavor is not supported.
        Vector newlines = (Vector) t.getTransferData(dataFlavor); 
        // Add all those pasted lines to our scribble.
        for(int i = 0; i < newlines.size(); i++)
          lines.addElement(newlines.elementAt(i));
        // And redraw the whole thing
        repaint();  
      }
      catch (UnsupportedFlavorException e) { 
        this.getToolkit().beep();   // If clipboard has some other type of data
      }
      catch (Exception e) {          
        this.getToolkit().beep();   // Or if anything else goes wrong...
      }
    }
    
    /**
     * This nested class implements the Transferable and ClipboardOwner 
     * interfaces used in data transfer.  It is a simple class that remembers
     * a selected object and makes it available in only one specified flavor.
     * It would be useful for transferring other types of data, too.
     **/
    static class SimpleSelection implements Transferable, ClipboardOwner {
      protected Object selection;    // The data to be transferred
      protected DataFlavor flavor;   // The one data flavor supported

      /** The constructor.  Just initialize some fields */
      public SimpleSelection(Object selection, DataFlavor flavor) { 
        this.selection = selection;  // Specify data
        this.flavor = flavor;        // Specify flavor
      }
      
      /** Return the list of supported flavors.  Just one in this case */
      public DataFlavor[] getTransferDataFlavors() { 
        return new DataFlavor[] { flavor };
      }

      /** Check whether we support a specified flavor */
      public boolean isDataFlavorSupported(DataFlavor f) {
        return f.equals(flavor);
      }

      /** If the flavor is right, transfer the data (i.e. return it) */
      public Object getTransferData(DataFlavor f) 
           throws UnsupportedFlavorException {
             if (f.equals(flavor)) return selection;
             else throw new UnsupportedFlavorException(f);
      }
      
      /** 
       * This is the ClipboardOwner method.  Called when the data is no
       * longer on the clipboard.  In this case, we don't need to do much. 
       **/
      public void lostOwnership(Clipboard c, Transferable t) { 
        selection = null; 
      }
    }
    
    /** 
     * A class to store the coordinates of one scribbled line. 
     * The complete scribble is stored as a Vector of these objects 
     **/
    static class Line {
      public short x1, y1, x2, y2;
      public Line(short x1, short y1, short x2, short y2) {
        this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
      }
    }
  }
}
