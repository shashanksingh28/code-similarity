package rebound;

import javax.swing.*;

public class Rebound extends JApplet
{
   private ReboundPanel panel;
   
   public void init()
    {
      panel = new ReboundPanel();
      getContentPane().add (panel);
    }

   //  Starts the animation when the applet is started.
   public void start ()
   {
      panel.getTimer().start();
   }

   //  Stops the animation when the applet is stopped.
   public void stop ()
   {
      panel.getTimer().stop();
   }
}
