// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

package oreilly.beans.yesno;
import java.beans.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class is a custom editor for the message property of the
 * YesNoDialog bean.  It is necessary because the default editor for
 * properties of type String does not allow multi-line strings
 * to be entered.
 */
public class YesNoDialogMessageEditor implements PropertyEditor {
  protected String value;  // The value we will be editing.

  public void setValue(Object o) {  value = (String) o; }
  public Object getValue() { return value; }
  public void setAsText(String s) { value = s; }
  public String getAsText() { return value; }
  public String[] getTags() { return null; }  // not enumerated; no tags

  // Say that we allow custom editing.
  public boolean supportsCustomEditor() { return true; }

  // Return the custom editor.  This just creates and returns a TextArea
  // to edit the multi-line text.  But it also registers a listener on the
  // text area to update the value as the user types and to fire the
  // property change events that property editors are required to fire.
  public Component getCustomEditor() {
    final TextArea t = new TextArea(value);
    t.setSize(300, 150); // TextArea doesn't have a preferred size, so set one
    t.addTextListener(new TextListener() {
      public void textValueChanged(TextEvent e) {
        value = t.getText();
        listeners.firePropertyChange(null, null, null);
      }
    });
    return t;
  }

  // Visual display of the value, for use with the custom editor.
  // Just print some instructions and hope they fit in the in the box.
  // This could be more sophisticated.
  public boolean isPaintable() { return true; }
  public void paintValue(Graphics g, Rectangle r) {
    g.setClip(r);
    g.drawString("Click to edit...", r.x+5, r.y+15);
  }

  // Important method for code generators.  Note that it
  // ought to add any necessary escape sequences.
  public String getJavaInitializationString() { return "\"" + value + "\""; }

  // This code uses the PropertyChangeSupport class to maintain a list of
  // listeners interested in the edits we make to the value.
  protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);
  public void addPropertyChangeListener(PropertyChangeListener l) {
    listeners.addPropertyChangeListener(l);
  }
  public void removePropertyChangeListener(PropertyChangeListener l) {
    listeners.removePropertyChangeListener(l);
  }
}
