import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.stream.IntStream;
import java.util.stream.DoubleStream;
import java.util.stream.Collectors;

public class CommonAlgorithmsRevisited
{
   public static void main(String[] args)
   {
      int n = 10;
      int[] squares = IntStream.range(0, n)
         .map(i -> i * i)
         .toArray();

      System.out.println(Arrays.toString(squares));

      double[] values = { 32, 54, 67.5, 29, 35, 80, 115, 44.5, 100, 65 };
      double total = DoubleStream.of(values).sum();
      double average = DoubleStream.of(values).average().orElse(0);
      double largest = DoubleStream.of(values).max().orElse(Double.MIN_VALUE);
      double smallest = DoubleStream.of(values).min().orElse(Double.MAX_VALUE);
      System.out.println("total: " + total);
      System.out.println("average: " + average);
      System.out.println("largest: " + largest);
      System.out.println("smallest: " + smallest);

      String str = "How much wood could a woodchuck chuck?";
      long spaces = str.codePoints()
         .filter(ch -> ch == ' ')
         .count();
      System.out.println("spaces: " + spaces);

      String result = DoubleStream.of(values)
         .mapToObj(v -> "" + v)
         .collect(Collectors.joining(" | "));
      System.out.println(result);

      OptionalDouble result2 = DoubleStream.of(values)
         .filter(v -> v > 100)
         .findFirst();
      System.out.println(result2);

      n = values.length;
      int pos = IntStream.range(0, n)
         .filter(i -> values[i] == 100)
         .findFirst()
         .orElse(-1);
      System.out.println("pos: " + pos);
   }
}
