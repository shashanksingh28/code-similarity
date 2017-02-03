// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;

/**
 * An applet that demonstrates nested container and components
 * It creates the hierarchy shown below, and uses different colors to
 * distinguish the different nesting levels of the containers
 *
 *   applet---panel1----button1
 *        |       |---panel2----button2
 *        |       |        |----panel3----button3
 *        |       |------panel4----button4
 *        |                   |----button5
 *        |---button6
 */
public class Containers extends Applet {
  public void init() {
    this.setBackground(Color.white);             // The applet is white
    this.setFont(new Font("Dialog", Font.BOLD, 24));

    Panel p1 = new Panel();
    p1.setBackground(new Color(200, 200, 200)); // Panel1 is darker than applet
    this.add(p1);                   // Panel 1 is contained in applet
    p1.add(new Button("#1"));       // Button 1 is contained in Panel 1

    Panel p2 = new Panel();
    p2.setBackground(new Color(150, 150, 150)); // Panel2 is darker than Panel1
    p1.add(p2);                     // Panel 2 is contained in Panel 1
    p2.add(new Button("#2"));       // Button 2 is contained in Panel 2

    Panel p3 = new Panel();
    p3.setBackground(new Color(100, 100, 100)); // Panel3 is darker than Panel2
    p2.add(p3);                     // Panel 3 is contained in Panel 2
    p3.add(new Button("#3"));       // Button 3 is contained in Panel 3

    Panel p4 = new Panel();
    p4.setBackground(new Color(150, 150, 150)); // Panel4 is darker than Panel1
    p1.add(p4);                     // Panel4 is contained in Panel 1
    p4.add(new Button("#4"));       // Button4 is contained in Panel4
    p4.add(new Button("#5"));       // Button5 is contained in Panel4

    this.add(new Button("#6"));     // Button6 is contained in applet
  }
}
