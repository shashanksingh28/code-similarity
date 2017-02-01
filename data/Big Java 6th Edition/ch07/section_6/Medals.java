/**
   This program prints a table of medal winner counts with row totals.
*/
public class Medals
{
   public static void main(String[] args)
   {
      final int COUNTRIES = 8;
      final int MEDALS = 3;

      String[] countries =
         {
            "Canada",
            "Italy",
            "Germany",
            "Japan",
            "Kazakhstan",
            "Russia",
            "South Korea",
            "United States"
         };

      int[][] counts =
         {
            { 0, 3, 0 },
            { 0, 0, 1 },
            { 0, 0, 1 },
            { 1, 0, 0 },
            { 0, 0, 1 },
            { 3, 1, 1 },
            { 0, 1, 0 },
            { 1, 0, 1 }
         };
      
      System.out.println("        Country    Gold  Silver  Bronze   Total");
      
      // Print countries, counts, and row totals
      for (int i = 0; i < COUNTRIES; i++)
      {
         // Process the ith row
         System.out.printf("%15s", countries[i]);

         int total = 0; 

         // Print each row element and update the row total
         for (int j = 0; j < MEDALS; j++)
         {
            System.out.printf("%8d", counts[i][j]);
            total = total + counts[i][j];
         }
         
         // Display the row total and print a new line
         System.out.printf("%8d\n", total);
      }
   }
}
