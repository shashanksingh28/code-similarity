// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;

/**
 * This program demonstrates how to add string cut-and-paste capabilities
 * to an application.
 **/
public class StringCutAndPaste extends Frame implements ActionListener
{
  /**
   * The main method creates a frame, arranges to handle its closing,
   * packs it and pops it up.
   **/
  public static void main(String[] args) {
    Frame f = new StringCutAndPaste();
    f.addWindowListener(new WindowAdapter() { 
      public void windowClosing(WindowEvent e) { System.exit(0); }
    });
    f.pack();
    f.show();
  }

  /** The text field that holds the text that is cut or pasted */
  TextField field;

  /**
   * The constructor builds a very simple test GUI, and registers this object
   * as the ActionListener for the buttons 
   **/
  public StringCutAndPaste() {
    this.setFont(new Font("SansSerif", Font.PLAIN, 14));  // Use a nice font

    // Set up the Cut button
    Button cut = new Button("Cut");                       
    cut.addActionListener(this);
    cut.setActionCommand("cut");
    this.add(cut, "West");

    // Set up the Paste button
    Button paste = new Button("Paste");
    paste.addActionListener(this);
    paste.setActionCommand("paste");
    this.add(paste, "East");

    // Set up the text field that they both operate on
    field = new TextField();
    this.add(field, "North");
  }

  /**
   * Clicking on one of the buttons invokes this method, which in turn
   * invokes either the cut() or the paste() method
   **/
  public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();
    if (cmd.equals("cut")) cut();
    else if (cmd.equals("paste")) paste();
  }

  /**
   * This method takes the current contents of the text field, creates a
   * StringSelection object to represent that string, and puts the 
   * StringSelection onto the clipboard
   **/
  public void cut() {
    // Get the currently displayed value
    String s = field.getText();                  

    // Create a StringSelection object to represent it.
    // This is a big convenience, because StringSelection implements both
    // the Transferable interface and the ClipboardOwner.  We don't have
    // to deal with either of them.
    StringSelection ss = new StringSelection(s); 

    // Now set the StringSelection object as the contents of the clipboard
    // Also set it as the owner of the clipboard.
    this.getToolkit().getSystemClipboard().setContents(ss, ss);
  }

  /**
   * This method does the reverse.  It gets the contents of the clipboard,
   * then asks for them to be converted to a string, then displays the
   * string.
   **/
  public void paste() {
    // Get the clipboard
    Clipboard c = this.getToolkit().getSystemClipboard();

    // Get the contents of the clipboard, as a Transferable object
    Transferable t = c.getContents(this);

    // Ask for the transferable data in string form, using the predefined
    // string DataFlavor.  Then display that string in the field.
    try { 
      String s = (String) t.getTransferData(DataFlavor.stringFlavor);
      field.setText(s); 
    }
    // If anything goes wrong with the transfer, just beep and do nothing.
    catch (Exception e) { 
      this.getToolkit().beep();
      return;
    }
  }
}
