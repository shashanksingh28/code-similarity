// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;

public class BorderLayoutExample extends Applet {
  String[] borders = {"North", "East", "South", "West", "Center"};
  public void init() {
    // Create and specify a BorderLayout layout manager that leaves
    // 10 pixels of horizontal and vertical space between components
    this.setLayout(new BorderLayout(10, 10));
    for(int i = 0; i < 5; i++) {
      // Swap the order of these arguments in Java 1.1
      this.add(borders[i], new Button(borders[i]));
    }
  }
}
