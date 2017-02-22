// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;

public class FlowLayoutExample extends Applet {
  public void init() {
    // Create and specify the layout manager for the applet container.
    // Leave 10 pixels of horizontal and vertical space between components.
    // Left justify rows.
    this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
    String spaces = "";  // Used to make the buttons different sizes
    for(int i = 1; i <= 9; i++) {
      this.add(new Button("Button #" + i + spaces));
      spaces += " ";
    }
  }
}
