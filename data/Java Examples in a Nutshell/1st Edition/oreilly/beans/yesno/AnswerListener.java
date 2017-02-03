// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

package oreilly.beans.yesno;

public interface AnswerListener extends java.util.EventListener {
  public void yes(AnswerEvent e);
  public void no(AnswerEvent e);
  public void cancel(AnswerEvent e);
}
