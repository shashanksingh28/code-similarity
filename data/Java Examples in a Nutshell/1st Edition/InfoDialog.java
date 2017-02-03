// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.awt.*;

public class InfoDialog extends Dialog {
  protected Button button;          // The okay button of the dialog
  protected MultiLineLabel label;   // The message displayed by the dialog

  public InfoDialog(Frame parent, String title, String message) {
    // Create a non-modal dialog with the specified title and parent
    super(parent, title, false);

    // Create and use a BorderLayout manager with 15 pixel spacing
    this.setLayout(new BorderLayout(15, 15));

    // Create the message component and add it to the window
    // MultiLineLabel is a custom component defined later in this chapter
    label = new MultiLineLabel(message, 20, 20);
    this.add("Center", label);

    // Create an Okay button in a Panel; add the Panel to the window
    // Use a FlowLayout to center the button in the panel and give it margins.
    // Note the nested use of containers and layout managers.
    button = new Button("Okay");
    Panel p = new Panel();
    p.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
    p.add(button);
    this.add("South", p);
    // Set the dialog size to the preferred size of its components
    this.pack();
  }

  // Pop down the window when the button is clicked.  Note 1.0 event model
  public boolean action(Event e, Object arg) {
    if (e.target == button) {
      this.hide();      // Pop the dialog down
      this.dispose();   // Destroy it.  Cannot be shown again after disposed
      return true;
    }
    else return false;
  }

  /**
   * A main method that demonstrates how to use this class, and allows testing
   */
  public static void main(String[] args) {
    // Create, size, and show a frame because dialogs require a frame parent.
    Frame f = new Frame("InfoDialog Test");
    f.resize(100, 100);  // Use setSize() in Java 1.1
    f.show();

    // Create an instance of InfoDialog, with title and message specified
    InfoDialog d = new InfoDialog(f, "InfoDialog Test",
                                  "This demo was written by David Flanagan\n" +
                                  "Copyright (c) 1997 O'Reilly & Associates");

    // And pop it up.  It will pop itself down automatically.
    d.show();
  }
}
