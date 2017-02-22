// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;
import java.awt.image.*;

/** An applet that display an image, and a filtered version of the image */
public class GrayImage extends Applet {
  Image orig, gray;  // the original and grayed-out versions of the image

  /**
   * Load the image.  Create a new image that is a grayer version of it, using
   * a FilteredImageSource, ImageProducer and a the GrayFilter class, below.
   */
  public void init() {
    orig = this.getImage(this.getDocumentBase(), "cover.gif");
    ImageFilter filter = new GrayFilter();
    ImageProducer producer = new FilteredImageSource(orig.getSource(), filter);
    gray = this.createImage(producer);
  }

  /** Display the original image and gray version side-by-side */
  public void paint(Graphics g) {
    g.drawImage(orig, 25, 25, this);
    g.drawImage(gray, 200, 25, this);
  }
}
/** Filter an image by computing a weighted average of its colors with gray */
class GrayFilter extends RGBImageFilter {
  public GrayFilter() { canFilterIndexColorModel = true; }
  public int filterRGB(int x, int y, int rgb) {
    int a = rgb & 0xff000000;
    int r = (((rgb & 0xff0000) + 0x1800000)/3) & 0xff0000;
    int g = (((rgb & 0x00ff00) + 0x018000)/3) & 0x00ff00;
    int b = (((rgb & 0x0000ff) + 0x000180)/3) & 0x0000ff;
    return a | r | g | b;
  }
}
