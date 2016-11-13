import java.util.Scanner;

/**
   This program computes a final score for a series of quiz scores: the sum after dropping 
   the lowest score. The program adapts the algorithm for computing the minimum.

   Note that this program is simpler than the one in How To 7.1 since
   we need not worry about the possibility of having too many scores.
*/
public class ScoreAnalyzer
{
   public static void main(String[] args)
   {
      Student fred = new Student();
      System.out.println("Please enter values, Q to quit:");
      Scanner in = new Scanner(System.in);
      while (in.hasNextDouble())
      {  
         fred.addScore(in.nextDouble());
      }

      int pos = fred.minimumPosition();
      if (pos == -1)
      {
         System.out.println("At least one score is required.");
      }
      else
      {
         fred.removeScore(pos);
         double total = fred.sum();
         System.out.println("Final score: " + total);
      }
   }
}

