// Assignment #: 4
// Name: Your name
// StudentID: 000-00-0000
// Lecture: 1
// Section: A
// Description: Assignment 4 class displays a menu of choices to a user
//        and performs the chosen task. It will keep asking a user to
//        enter the next choice until the choice of 'Q' (Quit) is entered.

import java.io.*;
import java.util.*;

public class Assignment4
 {
  public static void main (String[] args)
   {
       // local variables, can be accessed anywhere from the main method
       char input1 = 'Z';
       String inputInfo;
       String brandName;
       double price;
       int memory;
       String cpuType;
       int cpuSpeed;
       String line = new String();

       // instantiate a Computer object
       Computer computer1 = new Computer();

       printMenu();

       //Create a Scanner object to read user input
       Scanner scan = new Scanner(System.in);


       do  // will ask for user input
        {
         System.out.println("What action would you like to perform?");
         line = scan.nextLine();

         if (line.length() == 1)
          {
           input1 = line.charAt(0);
           input1 = Character.toUpperCase(input1);
           
           // matches one of the case statement
           switch (input1)
            {
             case 'A':   //Add Computer
               System.out.print("Please enter the computer information:\n");
               System.out.print("Enter a brand name:\n");
               brandName = scan.nextLine();
               computer1.setBrandName(brandName);
               
               System.out.print("Enter a computer price:\n");
               price = Double.parseDouble(scan.nextLine());
               computer1.setPrice(price);

               System.out.print("Enter a computer memory:\n");
               memory = Integer.parseInt(scan.nextLine());
               computer1.setMemory(memory);

               System.out.print("Enter a cpu type:\n");
               cpuType = scan.nextLine();
               System.out.print("Enter a cpu speed:\n");
               cpuSpeed = Integer.parseInt(scan.nextLine());
               computer1.setCPU(cpuType, cpuSpeed);
               break;
             case 'D':   //Display computer
               System.out.print(computer1);
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
        } while (input1 != 'Q' || line.length() != 1);
   }


  /** The method printMenu displays the menu to a user**/
  public static void printMenu()
   {
     System.out.print("Choice\t\tAction\n" +
                        "------\t\t------\n" +
                        "A\t\tAdd Computer\n" +
                        "D\t\tDisplay Computer\n" +
                        "Q\t\tQuit\n" +
                        "?\t\tDisplay Help\n\n");
  }
}