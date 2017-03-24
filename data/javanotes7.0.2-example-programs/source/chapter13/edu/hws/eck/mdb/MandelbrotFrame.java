package edu.hws.eck.mdb;

import java.awt.*;
import javax.swing.*;

/**
 * A Frame that holds a MandelbrotPanel and a menu bar appropriate
 * for that panel.
 */
public class MandelbrotFrame extends JFrame {
    
    /* On July 20, 2014, stuff related to running as an applet was removed. */
    
    private MandelbrotPanel panel;
    
    
    /**
     * Create the frame, containing a MandelbrotPanel and a menu bar
     * of type Menus.  The frame's upper left corner is positioned
     * at (30,50).  It is not made visible.
     */
    public MandelbrotFrame() {
        super(I18n.tr("frame.title"));
        panel = new MandelbrotPanel();
        setContentPane(panel);
        setJMenuBar(new Menus(panel,this));
        pack();
        setLocation(30,50);
    }
    

    /**
     * Returns the MandelbrotPanel that is displayed in this frame.
     * Note that getMandelbrotPanel().getDisplay() returns the panel's
     * MandelbrotDisplay.
     */
    public MandelbrotPanel getMandelbrotPanel() {
        return panel;
    }
    
    
    /**
     * This can be called to adjust the size/shape of the frame so that
     * it is completely visible on the screen.  If it extends beyond the
     * screen on the right or bottom, it is moved.  Then, if the upper
     * left corner is off the screen, the window is resized.
     */
    public void adjustToScreenIfNecessary() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point corner = getLocation();  // Upper left corner of window.
        Dimension size = getSize();    // Size of window.
        boolean changed = false;  // Does size or position have to be changed?
        if (corner.x + size.width > screenSize.width) {
                // Move corner left to bring right edge of window onto screen.
            corner.x = screenSize.width - size.width - 5; 
            if (corner.x < 5) { 
                   // Original width was too wide for the screen, so size the window to fit the screen.
               corner.x = 5;
               size.width = screenSize.width - 10;
            }
            changed = true;
        }
        if (corner.y + size.height > screenSize.height) {
               // Move corner up to bring bottom edge of window onto screen.
            corner.y = screenSize.height - size.height - 10;  
            if (corner.y < 40) { 
                   // Original height was too big for the screen, so size the window to fit the screen.
               corner.y = 40;
               size.height = screenSize.height - 50;
            }
            changed = true;
        }
        if (changed) {
            setBounds(corner.x, corner.y, size.width, size.height);
        }
    }

}
