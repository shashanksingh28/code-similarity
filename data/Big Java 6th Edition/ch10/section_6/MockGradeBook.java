import java.util.ArrayList;

public class MockGradeBook implements IGradeBook
{
   private ArrayList<Double> scores;

   public MockGradeBook() { scores = new ArrayList<Double>(); }

   public void addScore(int studentId, double score) 
   {
      // Ignore studentId
      scores.add(score);
   } 

   public double getAverageScore(int studentId) 
   {
      double total = 0;
      for (double x : scores) { total = total + x; }
      return total / scores.size();
   }

   public void save(String filename)
   {
      // Do nothing
   }

   public void load(String filename)
   {
      // Add sample scores
      scores.add(1.0);
      scores.add(7.0);
      scores.add(2.0);
      scores.add(9.0);
   }
}
