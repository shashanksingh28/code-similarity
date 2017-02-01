import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
   This program shows how a more efficient algorithm can greatly speed up
   the task of finding the most frequent element in an array.
*/
public class MostFrequent
{
   public static void main(String[] args)
   {
      ArrayList<Integer> values = new ArrayList<Integer>();
      int k = 300;
      // Adds one times 1, two times 2, three times 3, ... , k times k
      for (int i = 1; i <= k; i++)
      {
         for (int j = 1; j <= i; j++)
         {
            values.add(i);
         }
      }
      // This method shuffles the array list randomly
      Collections.shuffle(values);

      StopWatch timer = new StopWatch();
      int[] a = new int[values.size()];

      // Copies the values into an array and runs the first version
      // of the algorithm
      for (int i = 0; i < a.length; i++) { a[i] = values.get(i); }
      timer.start();
      int result = mostFrequent1(a);
      timer.stop();
      System.out.println(result);
      System.out.println("Expected: " + k);      
      System.out.println("Elapsed time: " 
            + timer.getElapsedTime() + " milliseconds");

      // Copies the same values and runs the second version     
      for (int i = 0; i < a.length; i++) { a[i] = values.get(i); }
      timer.reset();
      timer.start();
      result = mostFrequent2(a);
      timer.stop();
      System.out.println(result);
      System.out.println("Expected: " + k);      
      System.out.println("Elapsed time: " 
            + timer.getElapsedTime() + " milliseconds");
   }

   /**
      Returns the most frequently occurring value in an array.
      @param a an array
      @return the most frequently occurring value in a
   */
   public static int mostFrequent1(int[] a)
   {
      int[] counts = new int[a.length];
      for (int i = 0; i < a.length; i++) // O(n*n)
      {
         counts[i] = count(a, a[i]); // O(n) in each iteration
      }
      
      int highestFrequency = max(counts); // O(n)
      int highestFrequencyIndex = search(counts, highestFrequency); // O(n)
      return a[highestFrequencyIndex];      
   }

   /**
      Returns the most frequently occurring value in an array.
      @param a an array
      @return the most frequently occurring value in a
   */
   public static int mostFrequent2(int[] a)
   {
      Arrays.sort(a); // O(n log(n))
      int[] counts = new int[a.length];

      int count = 0;
      for (int i = 0; i < a.length; i++) // O(n)
      {
         count++;
         if (i == a.length - 1 || a[i] != a[i + 1])
         {
            counts[i] = count;
            count = 0;
         }
      }

      int highestFrequency = max(counts); // O(n)
      int highestFrequencyIndex = search(counts, highestFrequency); // O(n)
      return a[highestFrequencyIndex];
   }

   /**
      Counts how often a value occurs in an array.
      @param a the array
      @param value the value to count
      @return the number of occurrences of value in a
   */
   public static int count(int[] a, int value)
   {
      int count = 0;
      for (int i = 0; i < a.length; i++)
      {
         if (a[i] == value) { count++; }
      }
      return count;
   }

   /**
      Computes the largest value of an array.
      @param a the array
      @return the largest value in a
   */
   public static int max(int[] values)
   {
      int largest = values[0];
      for (int i = 1; i < values.length; i++)
      {
         if (values[i] > largest)
         {
            largest = values[i];
         }
      }
      return largest;
   }

   /**
      Finds a value in an array, using the linear search 
      algorithm.
      @param a the array to search
      @param value the value to find
      @return the index at which the value occurs, or -1
      if it does not occur in the array
   */
   public static int search(int[] a, int value)
   {  
      for (int i = 0; i < a.length; i++)
      {  
         if (a[i] == value) { return i; }
      }
      return -1;
   }
}
