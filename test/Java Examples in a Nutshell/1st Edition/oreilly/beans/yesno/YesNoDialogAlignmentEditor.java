// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

package oreilly.beans.yesno;
import java.beans.*;
import java.awt.*;

public class YesNoDialogAlignmentEditor extends PropertyEditorSupport {
  // These two methods allow the property to be edited in a dropdown list.
  // Return the list of value names for the enumerated type.
  public String[] getTags() {
    return new String[] { "left", "center", "right" };
  }

  // Convert each of those value names into the actual value.
  public void setAsText(String s) {
    if (s.equals("left")) setValue(new Integer(YesNoDialog.LEFT));
    else if (s.equals("center")) setValue(new Integer(YesNoDialog.CENTER));
    else if (s.equals("right")) setValue(new Integer(YesNoDialog.RIGHT));
    else throw new IllegalArgumentException(s);
  }

  // This is an important method for code generation.
  public String getJavaInitializationString() {
    switch(((Number)getValue()).intValue()) {
    default:
    case YesNoDialog.LEFT:   return "oreilly.beans.yesno.YesNoDialog.LEFT";
    case YesNoDialog.CENTER: return "oreilly.beans.yesno.YesNoDialog.CENTER";
    case YesNoDialog.RIGHT:  return "oreilly.beans.yesno.YesNoDialog.RIGHT";
    }
  }
}
