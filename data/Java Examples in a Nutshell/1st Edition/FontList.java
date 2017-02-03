// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;

/**
 * An applet that displays the standard fonts and styles.
 **/
public class FontList extends Applet
{
  // The available font families
  String[] families = {"Serif",         // "TimesRoman" in Java 1.0
                       "SansSerif",     // "Helvetica" in Java 1.0
                       "Monospaced",    // "Courier" in Java 1.0
                       "Dialog",        // unchanged
                       "DialogInput" }; // unchanged

  // The available font styles
  int[] styles = {Font.PLAIN, Font.ITALIC, Font.BOLD, Font.ITALIC+Font.BOLD};
  String[] stylenames = {"Plain", "Italic", "Bold", "Bold Italic"};

  public void paint(Graphics g) {
    for(int family=0; family < families.length; family++) { // for each family
      for(int style = 0; style < styles.length; style++) {    // for each style
        Font f = new Font(families[family], styles[style], 16); // create font
        String s = families[family] + " " + stylenames[style];  // create name
        g.setFont(f);                                           // set font
        g.drawString(s, 10, (family*4 + style + 1) * 20);       // display name
      }
    }
  }
}
