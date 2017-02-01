import java.util.Arrays;
import java.util.Comparator;

public class ComparatorDemo
{
   public static void main(String[] args)
   {
      String[] words = { "Mary", "had", "a", "little", "lamb" };
      Arrays.sort(words, (v, w) -> v.length() - w.length());
      System.out.println(Arrays.toString(words));

      words = new String[] { "Its", "fleece", "was", "white", "as", "snow" };
      Arrays.sort(words, Comparator.comparing(w -> w.length()));
      System.out.println(Arrays.toString(words));

      Country[] countries = new Country[3];
      countries[0] = new Country("Uruguay", 176220);
      countries[1] = new Country("Thailand", 513120);
      countries[2] = new Country("Belgium", 30510);

      Arrays.sort(countries, Comparator.comparing(c -> c.getArea()));
      System.out.println(Arrays.toString(countries));
   }
}
