public class Gallery3
{
   public static void main(String[] args)
   {
      final int MAX_WIDTH = 720;
      final int GAP = 10;
      final int PICTURES = 20;

      Picture pic = new Picture("picture1.jpg");
      Picture pic2 = new Picture("picture2.jpg");
      double x = pic.getBounds().getMaxX() + GAP;
      pic2.move(x, 0);
   }
}
