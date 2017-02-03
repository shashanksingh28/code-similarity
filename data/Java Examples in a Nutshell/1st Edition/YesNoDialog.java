// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.awt.*;
import java.awt.event.*;

public class YesNoDialog extends Dialog {
  public YesNoDialog(Frame parent, String title, String message,
                     String yes_label, String no_label, String cancel_label)
  {
    // Create a modal dialog with the specified title and parent
    super(parent, title, true);

    // Specify a LayoutManager for it
    this.setLayout(new BorderLayout(15, 15));

    // Put the message label in the middle of the window.
    // Note: MultiLineLabel is a custom component defined later in the chapter
    this.add("Center", new MultiLineLabel(message, 20, 20));

    // Create an action listener for use by the buttons of the dialog.
    // When a button is pressed, this listener first closes the dialog box.
    // Then, it passes the event on to the listeners registered for the
    // dialog box, after changing the event source from the individual button
    // to the dialog box itself.  Since events are immutable, it must create
    // a new ActionEvent object in order to change the source.  It passes
    // this new event to the listeners by calling actionPerformed() on the
    // AWTEventMulticaster object stored in the listeners field (see below).
    ActionListener listener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        YesNoDialog.this.dispose();   // pop down dialog
        if (listeners != null)        // notify all registered listeners
          listeners.actionPerformed(new ActionEvent(YesNoDialog.this,
                                                    e.getID(),
                                                    e.getActionCommand()));
      }
    };

    // Create a panel for the dialog buttons and put it at the bottom
    // of the dialog.  Specify a FlowLayout layout manager for it.
    Panel buttonbox = new Panel();
    buttonbox.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 15));
    this.add("South", buttonbox);

    // Create each specified button, specifying the action listener
    // and action command for each, and adding them to the buttonbox
    if (yes_label != null) {              // if a label was specified...
      Button yes = new Button(yes_label);     //   create button
      yes.setActionCommand("yes");            //   set action command
      yes.addActionListener(listener);        //   set listener
      buttonbox.add(yes);                     //   add button to the panel
    }
    if (no_label != null) {
      Button no = new Button(no_label);
      no.setActionCommand("no");
      no.addActionListener(listener);
      buttonbox.add(no);
    }
    if (cancel_label != null) {
      Button cancel = new Button(cancel_label);
      cancel.setActionCommand("cancel");
      cancel.addActionListener(listener);
      buttonbox.add(cancel);
    }

    // Finally, set the dialog to its preferred size.
    this.pack();
  }

  /** This field will hold a list of registered ActionListeners, thanks
   *  to the magic of AWTEventMulticaster */
  protected ActionListener listeners = null;

  /** Register an action listener to be notified when a button is pressed
   *  AWTEventMulticaster makes this easy. */
  public void addActionListener(ActionListener l) {
    listeners = AWTEventMulticaster.add(listeners, l);
  }

  /** Remove an action listener from our list of interested listeners */
  public void removeActionListener(ActionListener l) {
    listeners = AWTEventMulticaster.remove(listeners, l);
  }

  /**
   * A main method that demonstrates how to use this class, and allows testing
   */
  public static void main(String[] args) {
    // Create, size, and show a frame because dialogs require a frame parent.
    Frame f = new Frame("InfoDialog Test");
    f.setSize(100, 100);
    f.show();

    // Create an instance of InfoDialog, with title and message specified
    YesNoDialog d =
      new YesNoDialog(f, "YesNoDialog Test",
                      "There are unsaved files.\n" +
                      "Do you want to save them before quitting?",
                      "Yes, save and quit",
                      "No, quit without saving",
                      "Cancel; don't quit");

    // Register an action listener for the dialog.  This one just prints
    // the results out to the console.
    d.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println("The user pressed: " + e.getActionCommand());
      }
    });

    // Now pop the dialog up.  It will pop itself down automatically.
    d.show();
  }
}
