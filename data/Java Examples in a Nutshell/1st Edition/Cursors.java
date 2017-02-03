// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;

/** 
 * An applet that uses each of the predefined cursor types in a bunch 
 * of buttons.
 **/
public class Cursors extends Applet {
  int[] cursor_types = {     // Constants for the 14 predefined types
    Cursor.DEFAULT_CURSOR, Cursor.CROSSHAIR_CURSOR, Cursor.TEXT_CURSOR,
    Cursor.WAIT_CURSOR, Cursor.HAND_CURSOR, Cursor.MOVE_CURSOR,
    Cursor.N_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR, Cursor.E_RESIZE_CURSOR,
    Cursor.W_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR, Cursor.NW_RESIZE_CURSOR,
    Cursor.SE_RESIZE_CURSOR, Cursor.SW_RESIZE_CURSOR
  };
  String[] cursor_names = {  // The cursor names as strings
    "DEFAULT_CURSOR", "CROSSHAIR_CURSOR", "TEXT_CURSOR", "WAIT_CURSOR",
    "HAND_CURSOR", "MOVE_CURSOR", "N_RESIZE_CURSOR", "S_RESIZE_CURSOR",
    "E_RESIZE_CURSOR", "W_RESIZE_CURSOR", "NE_RESIZE_CURSOR",
    "NW_RESIZE_CURSOR", "SE_RESIZE_CURSOR", "SW_RESIZE_CURSOR"
  };

  /** Create a grid of buttons each using a different cursor */
  public void init() {
    this.setLayout(new GridLayout(0, 2, 5, 5));
    for(int i = 0; i < cursor_types.length; i++) {
      Button b = new Button(cursor_names[i]);
      // This is how we obtain a Cursor object and set it on a Component
      b.setCursor(Cursor.getPredefinedCursor(cursor_types[i]));
      this.add(b);
    }
  }
}
