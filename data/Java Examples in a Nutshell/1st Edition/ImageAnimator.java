// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.applet.*;
import java.awt.*;
import java.net.*;
import java.util.*;

/**
 * This applet displays an image animation.  It uses the MediaTracker class
 * to load the images and verify that there are no errors.
 */
public class ImageAnimator extends Applet implements Runnable {
  protected int num_frames;                // Number of frames in animation
  protected Image[] frames;                // The frames themselves
  protected int framenum;                  // Current frame number
  protected MediaTracker tracker;          // Tracker class to wait for images
  protected Thread animator_thread = null; // The thread for animation

  /** Read the basename and num_frames parameters.
   *  Then read in the images, using the specified base name.
   *  For example, if basename is images/anim, read images/anim0,
   *  images/anim1, etc.  These are relative to the current document URL.
   */
  public void init() {
    String basename = this.getParameter("basename");
    try { num_frames = Integer.parseInt(this.getParameter("num_frames")); }
    catch (NumberFormatException e) { num_frames = 0; }

    // getImage() creates an Image object from a URL specification,
    // but it doesn't actually load the images; that is done  asynchronously.
    // Store all the images in a MediaTracker so we can block until
    // they have all loaded.  This method must return promptly, so we don't
    // wait for them to load here.
    tracker = new MediaTracker(this);
    frames = new Image[num_frames];
    for(int i = 0; i < num_frames; i++) {
      frames[i] = this.getImage(this.getDocumentBase(), basename+i);
      tracker.addImage(frames[i], i);  // Add image to tracker, assigning an ID
    }
  }
  /** Draw the current frame of the animation */
  public void paint(Graphics g) { g.drawImage(frames[framenum], 0, 0, this); }

  /** Don't clear the screen before calling paint() */
  public void update(Graphics g) { paint(g); }

  /** Create the animation thread and start it running */
  public void start() {
    if (animator_thread == null) {
      animator_thread = new Thread(this);
      animator_thread.start();
    }
  }
  /** Stop the animation thread */
  public void stop() {
    if ((animator_thread != null) && animator_thread.isAlive())
      animator_thread.stop();
    animator_thread = null;
  }

  /** This is the body of the thread--the method that does the animation. */
  public void run() {
    // First, wait until all images have loaded completely.
    for (int i = 0; i < num_frames; i++) {
      this.showStatus("Loading frame: " + i);
      // Block until the specified image is loaded.  The ID argument is the
      // one we passed to addImage().
      try { tracker.waitForID(i); } catch (InterruptedException e) {;}
      // Check for errors loading it.  Display an error message if necessary
      if (tracker.isErrorID(i)) {
        this.showStatus("Error loading frame " + i + "; quitting.");
        return;
      }
    }
    this.showStatus("Loading frames: done.");  // Done loading all frames

    // Now do the animation: increment the framenumber, redraw, pause
    while(true) {
      if (++framenum >= frames.length) framenum = 0;
      repaint();
      try { Thread.sleep(200); } catch (InterruptedException e) { ; }
    }
  }
}
