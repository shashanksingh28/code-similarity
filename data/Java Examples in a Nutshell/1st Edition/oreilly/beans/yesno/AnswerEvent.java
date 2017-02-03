// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

package oreilly.beans.yesno;

public class AnswerEvent extends java.util.EventObject {
  protected int id;
  public static final int YES = 0, NO = 1, CANCEL = 2;
  public AnswerEvent(Object source, int id) {
    super(source);
    this.id = id;
  }
  public int getID() { return id; }
}
