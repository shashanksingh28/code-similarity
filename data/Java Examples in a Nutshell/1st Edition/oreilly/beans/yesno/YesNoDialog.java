// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

package oreilly.beans.yesno;      // Put this bean in its own private package.
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class YesNoDialog {
  // Properties of the bean.
  protected String message, title;
  protected String yesLabel, noLabel, cancelLabel;
  protected int alignment;
  protected Font font = new Font("Serif", Font.PLAIN, 12);
  protected Color background = SystemColor.control;
  protected Color foreground = SystemColor.controlText;

  // Constants for the alignment property.
  public static final int LEFT = MultiLineLabel.LEFT;
  public static final int RIGHT = MultiLineLabel.RIGHT;
  public static final int CENTER = MultiLineLabel.CENTER;

  // Methods to query all of the bean properties.
  public String getMessage() { return message; }
  public String getTitle() { return title; }
  public String getYesLabel() { return yesLabel; }
  public String getNoLabel() { return noLabel; }
  public String getCancelLabel() { return cancelLabel; }
  public int getAlignment() { return alignment; }
  public Font getFont() { return font; }
  public Color getBackground() { return background; }
  public Color getForeground() { return foreground; }

  // Methods to set all of the bean properties.
  public void setMessage(String m) { message = m; }
  public void setTitle(String t) { title=t; }
  public void setYesLabel(String l) { yesLabel = l; }
  public void setNoLabel(String l) { noLabel = l; }
  public void setCancelLabel(String l) { cancelLabel = l; }
  public void setAlignment(int a) { alignment = a; }
  public void setFont(Font f) { font = f; }
  public void setBackground(Color bg) { background = bg; }
  public void setForeground(Color fg) { foreground = fg; }

  /** This field holds a list of registered ActionListeners.
   *  Vector is internally synchronized to prevent race conditions */
  protected Vector listeners = new Vector();

  /** Register an action listener to be notified when a button is pressed */
  public void addAnswerListener(AnswerListener l) {
    listeners.addElement(l);
  }

  /** Remove an Answer listener from our list of interested listeners */
  public void removeAnswerListener(AnswerListener l) {
    listeners.removeElement(l);
  }

  /** Send an event to all registered listeners */
  public void fireEvent(AnswerEvent e) {
    // Make a copy of the list and fire the events using that copy.
    // This means that listeners can be added or removed from the original
    // list in response to this event.  We ought to be able to just use an
    // enumeration for the vector, but that doesn't copy the list internally.
    Vector list = (Vector) listeners.clone();
    for(int i = 0; i < list.size(); i++) {
      AnswerListener listener = (AnswerListener)list.elementAt(i);
      switch(e.getID()) {
      case AnswerEvent.YES: listener.yes(e); break;
      case AnswerEvent.NO:  listener.no(e); break;
      case AnswerEvent.CANCEL: listener.cancel(e); break;
      }
    }
  }

  /** The no-argument bean constructor, with default property values */
  public YesNoDialog() {
    this("Question", "Your\nMessage\nHere", "Yes", "No", "Cancel", LEFT);
  }

  /** A constructor for programmers using this class "by hand" */
  public YesNoDialog(String title, String message,
                     String yesLabel, String noLabel, String cancelLabel,
                     int alignment) {
    this.title = title;
    this.message = message;
    this.yesLabel = yesLabel;
    this.noLabel = noLabel;
    this.cancelLabel = cancelLabel;
    this.alignment = alignment;
  }

  /** This method makes the bean display the dialog box */
  public void display() {
    // Create a frame with the specified title.  It would be nice to
    // use a Dialog, but that needs to be passed a Frame argument, and
    // the BDK beanbox tool only seems to work with no-argument methods.
    final Frame frame = new Frame(title);

    // Specify a LayoutManager for it.
    frame.setLayout(new BorderLayout(15, 15));

    // Specify font and colors, if any are specified.
    if (font != null) frame.setFont(font);
    if (background != null) frame.setBackground(background);
    if (foreground != null) frame.setForeground(foreground);

    // Put the message label in the middle of the window.
    frame.add("Center", new MultiLineLabel(message, 20, 20, alignment));

    // Create an action listener for use by the buttons of the dialog.
    // When a button is pressed, this listener first closes the dialog box.
    // Then, it creates an AnswerEvent object that corresponds to the
    // button that was pressed, and send that new event to all registered
    // listeners, using the fireEvent() method defined above.
    ActionListener listener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.dispose();     // pop down window
        if (listeners != null) {      // notify any registered listeners
          String cmd = e.getActionCommand();
          int type;
          if (cmd.equals("yes")) type = AnswerEvent.YES;
          else if (cmd.equals("no")) type = AnswerEvent.NO;

          else type = AnswerEvent.CANCEL;
          fireEvent(new AnswerEvent(YesNoDialog.this, type));
        }
      }
    };

    // Create a panel for the dialog buttons and put it at the bottom
    // of the dialog.  Specify a FlowLayout layout manager for it.
    Panel buttonbox = new Panel();
    buttonbox.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 15));
    frame.add("South", buttonbox);

    // Create each specified button, specifying the action listener
    // and action command for each, and adding them to the buttonbox
    if ((yesLabel != null) && (yesLabel.length() > 0)) {
      Button yes = new Button(yesLabel);        // Create button.
      yes.setActionCommand("yes");              // Set action command.
      yes.addActionListener(listener);          // Set listener.
      buttonbox.add(yes);                       // Add button to the panel.
    }
    if ((noLabel != null) && (noLabel.length() > 0)) {
      Button no = new Button(noLabel);
      no.setActionCommand("no");
      no.addActionListener(listener);
      buttonbox.add(no);
    }
    if ((cancelLabel != null) && (cancelLabel.length() > 0)) {
      Button cancel = new Button(cancelLabel);
      cancel.setActionCommand("cancel");
      cancel.addActionListener(listener);
      buttonbox.add(cancel);
    }

    // Finally, set the dialog to its preferred size and display it.
    frame.pack();
    frame.show();
  }

  /**
   * A main method that demonstrates how to use this class, and allows testing
   */
  public static void main(String[] args) {
    // Create an instance of InfoDialog, with title and message specified:
    YesNoDialog d =
      new YesNoDialog("YesNoDialog Test",
                      "There are unsaved files.\n" +
                      "Do you want to save them before quitting?",
                      "Yes, save and quit",
                      "No, quit without saving",
                      "Cancel; don't quit",
                      YesNoDialog.CENTER);
    // Register an action listener for the dialog.  This one just prints
    // the results out to the console.
    d.addAnswerListener(new AnswerListener() {
      public void yes(AnswerEvent e) { System.out.println("Yes"); }
      public void no(AnswerEvent e) { System.out.println("No"); }
      public void cancel(AnswerEvent e) { System.out.println("Cancel"); }
    });

    // Now pop the dialog up.  It will pop itself down automatically.
    d.display();
  }
}
