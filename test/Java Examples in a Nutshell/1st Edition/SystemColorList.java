// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;

/**
 * An applet that displays all of the predefined system colors.
 **/
public class SystemColorList extends Applet {
  String[] color_names = {
    "desktop", "activeCaption", "activeCaptionText", "activeCaptionBorder",
    "inactiveCaption", "inactiveCaptionText", "inactiveCaptionBorder",
    "window", "windowBorder", "windowText", "menu", "menuText", "text",
    "textText", "textHighlight", "textHighlightText", "textInactiveText",
    "control", "controlText", "controlHighlight", "controlLtHighlight",
    "controlShadow", "controlDkShadow", "scrollbar", "info", "infoText"
  };
  SystemColor[] colors = {
    SystemColor.desktop, SystemColor.activeCaption,
    SystemColor.activeCaptionText, SystemColor.activeCaptionBorder,
    SystemColor.inactiveCaption, SystemColor.inactiveCaptionText,
    SystemColor.inactiveCaptionBorder, SystemColor.window,
    SystemColor.windowBorder, SystemColor.windowText,
    SystemColor.menu, SystemColor.menuText, SystemColor.text,
    SystemColor.textText, SystemColor.textHighlight,
    SystemColor.textHighlightText, SystemColor.textInactiveText,
    SystemColor.control, SystemColor.controlText, SystemColor.controlHighlight,
    SystemColor.controlLtHighlight, SystemColor.controlShadow,
    SystemColor.controlDkShadow, SystemColor.scrollbar, SystemColor.info,
    SystemColor.infoText
  };

  public void init() {
    // Use a bunch of Label objects arranged in a grid to display the colors.
    this.setLayout(new GridLayout(0, 3, 5, 5));
    for(int i = 0; i < colors.length; i++) {
      // Create a label object to display a system color and its name
      Label l = new Label(color_names[i], Label.CENTER);
      this.add(l);
      // compute a foreground color to contrast with the background
      Color bg  = colors[i], fg;
      int r = bg.getRed(), g = bg.getGreen(), b = bg.getBlue();
      int avg = (r + g + b) / 3;
      if (avg > 128) fg = Color.black;
      else fg = Color.white;
      // And assign the colors.
      l.setBackground(bg);
      l.setForeground(fg);
    }
  }
}
