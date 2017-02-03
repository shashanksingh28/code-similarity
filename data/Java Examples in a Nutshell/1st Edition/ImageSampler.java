// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;

/** An applet that demonstrates image scaling, cropping, and flipping */
public class ImageSampler extends Applet {
  Image i;

  /** Load the image */
  public void init() {  i = getImage(this.getDocumentBase(), "tiger.gif"); }

  /** Display the image in a variety of ways */
  public void paint(Graphics g) {
    g.drawString("Original image:", 20, 20);     // Display original image
    g.drawImage(i, 110, 10, this);               // Old version of drawImage()

    g.drawString("Scaled Images:", 20, 120);     // Display scaled images
    g.drawImage(i, 20, 130, 40, 150, 0, 0, 100, 100, this);  // New version
    g.drawImage(i, 60, 130, 100, 170, 0, 0, 100, 100, this);
    g.drawImage(i, 120, 130, 200, 210, 0, 0, 100, 100, this);
    g.drawImage(i, 220, 80, 370, 230, 0, 0, 100, 100, this);

    g.drawString("Cropped Images:", 20, 250);    // Display cropped images
    g.drawImage(i, 20, 260, 70, 310, 0, 0, 50, 50, this);
    g.drawImage(i, 80, 260, 130, 310, 25, 25, 75, 75, this);
    g.drawImage(i, 140, 260, 190, 310, 50, 50, 100, 100, this);

    g.drawString("Flipped Images:", 20, 330);    // Display flipped images
    g.drawImage(i, 20, 340, 120, 440, 100, 0, 0, 100, this);
    g.drawImage(i, 130, 340, 230, 440, 0, 100, 100, 0, this);
    g.drawImage(i, 240, 340, 340, 440, 100, 100, 0, 0, this);

    g.drawString("Scaled, Cropped, and Flipped:", 20, 460);  // Do all three
    g.drawImage(i, 20, 470, 170, 550, 90, 70, 10, 20, this);
  }
}
