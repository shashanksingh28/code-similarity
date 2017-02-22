// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class CardLayoutExample extends Applet {
  public void init() {
    // Create a layout manager, and save a reference to it for future use.
    // This CardLayout leaves 10 pixels margins around the component.
    final CardLayout cardlayout = new CardLayout(10, 10);
    // Specify the layout manager for the applet
    this.setLayout(cardlayout);
    for(int i = 1; i <= 9; i++) {
      Button b = new Button("Button #" + i);
      ActionListener listener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {  // When button is clicked
          cardlayout.next(CardLayoutExample.this);    // display the next one.
        }
      };
      b.addActionListener(listener);
      this.add("Button" + i, b);   // Specify a name for each component
    }
  }
}
