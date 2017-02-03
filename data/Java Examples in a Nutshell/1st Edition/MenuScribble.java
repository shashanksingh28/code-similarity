// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/** An applet with a popup menu or an application with pulldown menus */
public class MenuScribble extends Applet implements ActionListener {
  /**
   * The main() method.  If this program is invoked as an application, this
   * method will create a window and pulldown menu system for it.
   */
  public static void main(String[] args) {
    Frame f = new Frame();                     // Create a window
    MenuScribble applet = new MenuScribble();  // Create the applet panel
    f.add(applet, "Center");                   // Add applet to window
    applet.init();                             // Initialize the applet

    // Create a menubar and tell the frame about it
    MenuBar menubar = new MenuBar();
    f.setMenuBar(menubar);

    // Create three pulldown menus for the menubar
    Menu file = new Menu("File");
    Menu colors = new Menu("Colors");
    Menu help = new Menu("Help");

    // Add the menus to the bar, and treat Help menu specially.
    menubar.add(file);
    menubar.add(colors);
    menubar.add(help);
    menubar.setHelpMenu(help);

    // Add two items, with a keyboard shortcuts to the File menu
    MenuItem clear = new MenuItem("Clear", new MenuShortcut(KeyEvent.VK_C));
    clear.addActionListener(applet);   // Say who's listening for the events
    clear.setActionCommand("clear");   // A detail to go along with the events
    file.add(clear);                   // Add item to menu pane
    MenuItem quit = new MenuItem("Quit", new MenuShortcut(KeyEvent.VK_Q));
    quit.addActionListener(applet);
    quit.setActionCommand("quit");
    file.add(quit);

    // Add items to the other two menus, this time using a  convenience
    // method defined below.  Note use of new anonymous array syntax.
    createMenuItems(colors, applet,
                    new String[] { "Red", "Green", "Blue", "Black" },
                    new String[] { "red", "green", "blue", "black" },
                    new int[] { KeyEvent.VK_R, KeyEvent.VK_G,
                                KeyEvent.VK_B, KeyEvent.VK_L });
    createMenuItems(help, applet,
                    new String[] { "About" }, new String[] {"about"},
                    new int[] { KeyEvent.VK_A });

    // Handle window close requests
    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) { System.exit(0); }
    });

    f.setSize(400, 400);     // Set the size of the window
    f.show();                // Finally, pop the window up.
  }

  /**
   * The init() method.  If the program is invoked as an applet, the browser
   * allocates screen space for it and calls this method to set things up.
   * If running as an applet, this method creates a popup menu and adds
   * it to the applet.
   */
  public void init() {
    // If we are not in a frame (i.e. we are an applet), create a popup menu
    if (!(this.getParent() instanceof Frame)) {
      // Create the popup menu
      popup = new PopupMenu("File");
      // Add items to it using the convenience routine below
      createMenuItems(popup, this,
                      new String[] {"Clear", "Red", "Green", "Blue", "Black"},
                      new String[] {"clear", "red", "green", "blue", "black"},
                      new int[] { KeyEvent.VK_C, KeyEvent.VK_R, KeyEvent.VK_G,
                                    KeyEvent.VK_B, KeyEvent.VK_L });
      // Add the popup menu to the component it will appear over.
      this.add(popup);
    }

    // Define, instantiate and register the Listener objects for scribbling
    this.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        lastx = e.getX(); lasty = e.getY();
      }
    });
    this.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        Graphics g = getGraphics();
        int x = e.getX(), y = e.getY();
        g.setColor(color);               // draw with the specified color
        g.drawLine(lastx, lasty, x, y);
        lastx = x; lasty = y;
      }
    });
  }

  /**
   * This is the convenience routine for adding menu items to a menu pane.
   * It works for pulldown or popup menu panes, since PopupMenu extends Menu.
   */
  protected static void createMenuItems(Menu pane, ActionListener listener,
                                        String[] labels, String[] commands,
                                        int[] shortcuts) {
    for(int i = 0; i < labels.length; i++) {
      MenuItem mi = new MenuItem(labels[i]);
      mi.addActionListener(listener);
      if ((commands != null) && (commands[i] != null))
        mi.setActionCommand(commands[i]);
      if ((shortcuts != null) && (shortcuts[i] != 0))
        mi.setShortcut(new MenuShortcut(shortcuts[i]));
      pane.add(mi);
    }
  }

  /**
   * This method is required to make the popup menu, if any, pop up.  It
   * uses the low-level Java 1.1 event handling mechanism to test all mouse
   * events (except mouse motion events) to see if they are the platform-
   * dependent popup menu trigger.  If so, it calls show() to pop the
   * popup up.  If not, it passes the event to the superclass version of
   * this method so that it is dispatched as usual and can be passed to
   * the listener object registered by the init method for scribbling.
   */
  public void processMouseEvent(MouseEvent e) {
    if ((popup != null) && e.isPopupTrigger())
      popup.show(this, e.getX(), e.getY());
    else super.processMouseEvent(e);
  }

  /**
   * This is the method defined by the ActionListener interface.  All
   * the menu item commands are handled here because the applet was specified
   * as the listener for all menu items.  Note the use of getActionCommand()
   * to determine the command string registered with the individual items.
   */
  public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();
    if (cmd.equals("quit")) System.exit(0);   // Don't do this in an applet
    else if (cmd.equals("clear")) clear();    // defined below
    else if (cmd.equals("about")) /* not yet implemented */ ;
    else if (cmd.equals("red")) color = Color.red;
    else if (cmd.equals("green")) color = Color.green;
    else if (cmd.equals("blue")) color = Color.blue;
    else if (cmd.equals("black")) color = color.black;
  }

  /** Clear the applet area.  Used by actionPerformed() above */
  protected void clear() {
    Graphics g = this.getGraphics();
    g.setColor(this.getBackground());
    g.fillRect(0, 0, this.getSize().width, this.getSize().height);
  }

  // Here are the instance variables for this program
  protected int lastx, lasty;          // Coordinates of last mouse click
  protected Color color = Color.black; // Current drawing color
  protected PopupMenu popup;
}
