// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;

/**
 * A version of the Scribble applet that reads two applet parameters
 * to set the foreground and background colors.  It also returns
 * information about itself when queried.
 **/
public class ColorScribble extends Scribble {
  // Read in two color parameters and set the colors.
  public void init() {
    super.init();
    Color foreground = getColorParameter("foreground");
    Color background = getColorParameter("background");
    if (foreground != null) this.setForeground(foreground);
    if (background != null) this.setBackground(background);
  }

  // Read the specified parameter.  Interpret it as a hexadecimal
  // number of the form RRGGBB and convert it to a color.
  protected Color getColorParameter(String name) {
    String value = this.getParameter(name);
    try { return new Color(Integer.parseInt(value, 16)); }
    catch (Exception e) { return null; }
  }

  // Return information suitable for display in an About dialog box.
  public String getAppletInfo() {
    return "ColorScribble v. 0.02.  Written by David Flanagan.";
  }

  // Return info about the supported parameters.  Web browsers and applet
  // viewers should display this information, and may also allow users to
  // set the parameter values.
  public String[][] getParameterInfo() { return info; }

  // Here's the information that getParameterInfo() returns.
  // It is an array of arrays of strings describing each parameter.
  // Format: parameter name, parameter type, parameter description
  private String[][] info = {
    {"foreground", "hexadecimal color value", "foreground color"},
    {"background", "hexadecimal color value", "background color"}
  };
}
