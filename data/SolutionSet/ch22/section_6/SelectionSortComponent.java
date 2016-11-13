import java.awt.Graphics;
import javax.swing.JComponent;

/**
   A component that displays the current state of the selection sort algorithm.
*/
public class SelectionSortComponent extends JComponent
{
   private SelectionSorter sorter;

   /**
      Constructs the component.
   */
   public SelectionSortComponent()
   {
      int[] values = ArrayUtil.randomIntArray(30, 300);
      sorter = new SelectionSorter(values, this);
   }

   public void paintComponent(Graphics g)
   {
      sorter.draw(g);
   }

   /**
      Starts a new animation thread.
   */
   public void startAnimation()
   {
      class AnimationRunnable implements Runnable
      {
         public void run()
         {
            try
            {
               sorter.sort();
            }
            catch (InterruptedException exception)
            {
            }
         }
      }
      
      Runnable r = new AnimationRunnable();
      Thread t = new Thread(r);
      t.start();
   }
}

