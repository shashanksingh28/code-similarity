// Assignment #: Arizona State University Assignment #5
//         Name: To be completed
//    StudentID: 000-00-0000
//      Lecture: 1
//      Section: A
//  Description: The Assignment 5 class displays a menu of choices
//               (add a product, compute the total cost, search a product, list product,
//               quit, display menu) to a user.
//               Then it performs the chosen task. It will keep asking a user to
//               enter the next choice until the choice of 'Q' (Quit) is
//               entered.

import java.io.*;         //to use InputStreamReader and BufferedReader
import java.util.*;       //to use ArrayList

public class Assignment5
{
   public static void main (String[] args)
   {
      char input1;
      String inputInfo = new String();
      String line = new String();
      boolean found;

     // ArrayList object is used to store drink objects
      ArrayList<Product> productList = new ArrayList<Product>();

      try
      {
         printMenu();     // print out menu

       // create a BufferedReader object to read input from a keyboard
         InputStreamReader isr = new InputStreamReader (System.in);
         BufferedReader stdin = new BufferedReader (isr);

         do
         {
            System.out.print("What action would you like to perform?\n");
            line = stdin.readLine().trim();
            input1 = line.charAt(0);
            input1 = Character.toUpperCase(input1);

            if (line.length() == 1)
            {
               switch (input1)
               {
                  case 'A':   //Add Product
                     System.out.print("Please enter a product information to add:\n");
                     inputInfo = stdin.readLine().trim();
                  /***********************************************************************************
                  ***  ADD your code here to create an object of one of child classes of Product class
                  ***  and add it to the productList
                  ***********************************************************************************/
                     productList.add(ProductParser.parseStringToProduct(inputInfo));

                     break;

                  case 'C':   //Compute Total Costs
                  /***********************************************************************************
                  ***  ADD your code here to compute the total cost for all products in the productList.
                  ***********************************************************************************/
                	 for(Product p: productList){
                		 
                		 p.computeTotalCost();
                	 }
                	 System.out.println("total costs computed");
                	 
                     break;

                  case 'S':   //Search for a product
                     System.out.print("Please enter a productId to search:\n");
                     inputInfo = stdin.readLine().trim();
                  /***********************************************************************************
                  ***  ADD your code here to search a given productId. If found, set "found" true,
                  ***  and set "found" false otherwise.
                  ***********************************************************************************/
                     	 	found = false;                     
                     	
                     			for(Product p: productList){
                     				if ( p.getProductId().equalsIgnoreCase(inputInfo)){
                     					found = true;
                     					
                     				}
                     			}
                     		
                     		if(found){
                     			System.out.print("product found\n");
                     			
                     		}else
                     			System.out.print("product not found\n");

                     		
                     break;

                  case 'L':   //List Products
                  /***********************************************************************************
                  ***  ADD your code here to print out all Product objects in the productList.
                  ***  If there is no product in the list, print "no product\n"
                  ***********************************************************************************/
                	  if(productList.size()>0){
                		  String output ="";
                		  for(Product p: productList){
                      		
                			  output += p.toString()+"\n";
                    		  
                    	  }
                		  System.out.print(output);
                	  }else{
                		  System.out.print("no product\n");
                	  }
                	  
                	  
                	  
                	  
                	  
                     break;

                  case 'Q':   //Quit
                     break;
                  case '?':   //Display Menu
                     printMenu();
                     break;
                  default:
                     System.out.print("Unknown action\n");
                     break;
               }
            }
            else
            {
               System.out.print("Unknown action\n");
            }
         } while (input1 != 'Q'); // stop the loop when Q is read
      }
      catch (IOException exception)
      {
         System.out.println("IO Exception");
      }
   }

  /** The method printMenu displays the menu to a use **/
   public static void printMenu()
   {
      System.out.print("Choice\t\tAction\n" +
                      "------\t\t------\n" +
                      "A\t\tAdd Product\n" +
                      "C\t\tCompute Total Costs\n" +
                      "S\t\tSearch for Product\n" +
                      "L\t\tList Products\n" +
                      "Q\t\tQuit\n" +
                      "?\t\tDisplay Help\n\n");
   }
}


