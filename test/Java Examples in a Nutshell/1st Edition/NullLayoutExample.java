// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;

public class NullLayoutExample extends Applet {
  public void init() {
    // Get rid of the default layout manager.
    // We'll arrange the components ourself.
    this.setLayout(null);
    for(int i = 1; i <= 9; i++) {
      Button b = new Button("Button #" + i);
      b.setBounds(i*26, i*18, 100, 25); // use reshape() in Java 1.0
      this.add(b);
    }
  }
  public Dimension getPreferredSize() { return new Dimension(350, 225); }
}
