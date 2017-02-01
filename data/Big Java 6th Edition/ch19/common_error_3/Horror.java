import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Horror
{
   public static void main(String[] args) throws IOException
   {
      Scanner in = new Scanner(new File("../words.txt"));
      List<String> wordList = new ArrayList<>();
      while (in.hasNext()) { wordList.add(in.next()); }

      long count = wordList.stream()
         .filter(w -> w.length() > 10)
         .count();

      System.out.println("count: " + count);

      List<String> longWords = new ArrayList<>();

      // The following code will probably throw an exception
      wordList.stream().parallel()
         .forEach(w -> 
            {
               if (w.length() > 10) { longWords.add(w); }
            });

      // If the program made it until here, the size will
      // probably be wrong
      System.out.println("longWords.size(): " + longWords.size());
   }
}
