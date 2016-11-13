public class Gallery5
{
   public static void main(String[] args)
   {
      final int MAX_WIDTH = 720;
      final int GAP = 10;
      final int PICTURES = 20;

      Picture pic = new Picture("picture1.jpg");
      double maxY = 0; 
      
      for (int i = 2; i <= PICTURES; i++)
      {
         maxY = Math.max(maxY, pic.getBounds().getMaxY());
         Picture previous = pic;
         pic = new Picture("picture" + i + ".jpg");
         double x = previous.getBounds().getMaxX() + GAP;
         if (x + pic.getBounds().getWidth() < MAX_WIDTH)
         {
            pic.move(x, previous.getBounds().getY());
         }
         else
         {
            pic.move(0, maxY + GAP);
            return;
         }
      }
   }
}
