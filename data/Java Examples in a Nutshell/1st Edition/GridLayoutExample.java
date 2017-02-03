// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;

public class GridLayoutExample extends Applet {
  public void init() {
    // Create and specify a layout manager for this applet.
    // Layout components into a grid three columns wide, with the number
    // of rows to depend on the number of components.  Leave 10 pixels
    // of horizontal and vertical space between components
    this.setLayout(new GridLayout(0, 3, 10, 10));
    for(int i = 1; i <= 9; i++)
      this.add(new Button("Button #" + i));
  }
}
