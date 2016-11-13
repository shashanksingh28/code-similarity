public class Gallery4
{
   public static void main(String[] args)
   {
      final int MAX_WIDTH = 720;
      final int GAP = 10;
      final int PICTURES = 20;

      Picture pic = new Picture("picture1.jpg");
      for (int i = 2; i <= PICTURES; i++)
      {
         Picture previous = pic;
         pic = new Picture("picture" + i + ".jpg");
         double x = previous.getBounds().getMaxX() + GAP;
         pic.move(x, 0);
      }
   }
}
