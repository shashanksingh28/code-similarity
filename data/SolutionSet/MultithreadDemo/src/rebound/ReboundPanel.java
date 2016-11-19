package rebound;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ReboundPanel extends JPanel
{
   private final int DIAMETER = 35;
   private final int DELAY = 20;

   private Timer timer;
   private int x, y;
   private double moveX, moveY;

   //-----------------------------------------------------------------
   //  Sets up the applet, including the timer for the animation.
   //-----------------------------------------------------------------
   public ReboundPanel ()
   {
      timer = new Timer(DELAY, new ReboundListener());
      timer.start();
      x = 0;
      y = 40;
      moveX = 3;
      moveY = 3;

      setBackground (Color.black);
      setPreferredSize (new Dimension(300, 100));
   }

   // accessor method of timer to be used in the applet class.
   public Timer getTimer()
    {
       return timer;
    }
   //  Draws the circle in the current location.
   public void paintComponent (Graphics page)
   {
      super.paintComponent (page);
      page.setColor(Color.yellow);
      page.fillOval(x,y,DIAMETER, DIAMETER);
   }

   //  Represents the action listener for the timer.
   private class ReboundListener implements ActionListener
   {
      //  Updates the position of the image and possibly the direction
      //  of movement whenever the timer fires an action event.
      public void actionPerformed (ActionEvent event)
      {
         x += moveX;
         y += moveY;

         //if the circle hits the applet’s boundary, 
         //it changes its direction.
         if (x <= 0 || x >= getSize().getWidth()-DIAMETER)
            moveX = moveX * -1;

         if (y <= 0 || y >= getSize().getHeight()-DIAMETER)
            moveY = moveY * -1  ;

         repaint();
      }
   }
}
