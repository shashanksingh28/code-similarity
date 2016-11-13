import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ComparatorDemo
{
   public static void main(String[] args) throws IOException
   {
      Comparator<String> comp = Comparator.comparing(t -> t.length());
      String[] words = "how much wood could a wood chuck chuck".split(" ");
      Arrays.sort(words, comp);
      System.out.println(Arrays.toString(words));

      Arrays.sort(words, Comparator.comparing(String::length));
      System.out.println(Arrays.toString(words));

      try (Stream<String> lines = Files.lines(Paths.get("../population.txt")))
      {
         List<Country> result = lines
            .map(line -> Country.parse(line))
            .sorted(Comparator
               .comparing(Country::getContinent)
               .thenComparing(Country::getName))
            .collect(Collectors.toList());
         System.out.println(result);
      }
   }
}
