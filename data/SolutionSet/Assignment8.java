// Assignment #: 8
//         Name: Daniel D'Souza
//    StudentID: 1207035111
//  Lab Lecture: Tu/Thrs 4:30
//  Description: The Assignment 8 class displays a menu of choices to a user
//               and performs the chosen task. It will keep asking a user to
//               enter the next choice until the choice of 'Q' (Quit) is
//               entered.

import java.io.*;
import java.util.*;

public class Assignment8
 {
  public static void main (String[] args)
   {
     char input1;
     String inputInfo = new String(), inputInfo2 = new String();
     String name = new String(), flightNum = new String(),
                   deptCity = new String();
     boolean operation;
     int operation2;
     String line = new String();
     String passengerInfo = new String(), flightInfo = new String();
     String filename = new String();

     // create a Reservation object. This is used throughout this class.
     Reservation reservation1 = new Reservation();

     try
      {
       // print out the menu
       printMenu();

       // create a BufferedReader object to read input from a keyboard
       InputStreamReader isr = new InputStreamReader (System.in);
       BufferedReader stdin = new BufferedReader (isr);

       do
        {
         System.out.print("What action would you like to perform?\n");
         line = stdin.readLine().trim();  //read a line
         input1 = line.charAt(0);
         input1 = Character.toUpperCase(input1);

         if (line.length() == 1)   //check if a user entered only one character
          {
           switch (input1)
            {
             case 'A':   //Add Passenger
               System.out.print("Please enter the passenger information to add:\n");
               inputInfo = stdin.readLine().trim();
               operation = reservation1.addPassenger(inputInfo);
               if (operation == true)
                System.out.print("passenger added\n");
               else
                System.out.print("passenger exists\n");
               break;
             case 'B':   //Add Flight
               System.out.print("Please enter the flight information to add:\n");
               inputInfo = stdin.readLine().trim();
               operation = reservation1.addFlight(inputInfo);
               if (operation == true)
                 System.out.print("flight added\n");
               else
                 System.out.print("flight exists\n");
               break;
             case 'D':   //Search for Passenger
               System.out.print("Please enter the passengerID of a passenger to search:\n");
               inputInfo = stdin.readLine().trim();
               operation2=reservation1.passengerExists(inputInfo);
               if (operation2 > -1)
                 System.out.print("passenger found\n");
               else
                 System.out.print("passenger not found\n");
               break;
             case 'E':  //Search for Flight
               System.out.print("Please enter the airlines to search:\n");
               name = stdin.readLine().trim();
               System.out.print("Please enter the flight number to search:\n");
               flightNum = stdin.readLine().trim();
               System.out.print("Please enter the flight's departure city to search:\n");
               deptCity = stdin.readLine().trim();
               operation2=reservation1.flightExists(name, flightNum, deptCity);

               if (operation2 > -1)
                 System.out.print("flight found\n");
               else
                 System.out.print("flight not found\n");
               break;
             case 'L':   //List Passengers
               System.out.print(reservation1.listPassengers());
               break;
             case 'M':   //List Flights
               System.out.print(reservation1.listFlights());
               break;
             case 'O':  // Sort Passengers
               reservation1.sortPassengers();
               System.out.print("passengers sorted\n");
               break;
             case 'P':  // Sort Flights
               reservation1.sortFlights();
               System.out.print("flights sorted\n");
               break;
             case 'Q':   //Quit
               break;
             case 'R':  //Remove Passenger
               System.out.print("Please enter the passengerID to remove:\n");
               inputInfo = stdin.readLine().trim();
               operation=reservation1.removePassenger(inputInfo);
               if (operation == true)
                 System.out.print("passenger removed\n");
               else
                 System.out.print("passenger not found\n");
               break;
             case 'S':  //Remove Flight
               System.out.print("Please enter the airlines to remove:\n");
               name = stdin.readLine().trim();
               System.out.print("Please enter the flight number to remove:\n");
               flightNum = stdin.readLine().trim();
               System.out.print("Please enter the flight's departure city to remove:\n");
               deptCity = stdin.readLine().trim();
               operation=reservation1.removeFlight(name, flightNum, deptCity);
               if (operation == true)
                 System.out.print("flight removed\n");
               else
                 System.out.print("flight not found\n");
               break;
             case 'T':  //Close Reservation
               reservation1.closeReservation();
               System.out.print("reservation closed\n");
               break;
             case 'U':  //Write Text to a File
               System.out.print("Please enter the file name to write:\n");
               filename = stdin.readLine().trim();
               FileWriter fw;
               BufferedWriter bw;
               PrintWriter outFile = null;
               try
                {
                 fw = new FileWriter(filename);
                 bw = new BufferedWriter(fw);
                 outFile = new PrintWriter(bw);
                 System.out.print("Please enter a string to write in the file:\n");
                 String input = stdin.readLine();
                 outFile.print(input + "\n");
                 System.out.print(filename + " was written\n");
                }
               catch (IOException exception)
                {
                 System.out.print("An I/O error has occurred\n");
                }
               finally
                {
                 if (outFile != null)
                    outFile.close();
                }
               break;
             case 'V':  //Read Text from a File
               System.out.print("Please enter the file name to read:\n");
               filename = stdin.readLine().trim();
               FileReader fr;
               BufferedReader inFile = null;
               try
                {
                 fr = new FileReader(filename);
                 inFile = new BufferedReader(fr);
                 System.out.print(filename + " was read\n");

                 String output = inFile.readLine();
                 System.out.print("The first line of the file is:\n");
                 System.out.print(output+"\n");
                }
               catch (FileNotFoundException exception)
                {
                 System.out.print(filename + " was not found\n");
                }
               catch (IOException exception)
                {
                 System.out.print("An I/O error has occurred\n");
                }
               finally
                {
                  if (inFile != null)
                    inFile.close();
                }
               break;
             case 'W':  //Serialize Reservation to a File
               System.out.print("Please enter the file name to write:\n");
               filename = stdin.readLine().trim();
               FileOutputStream file;
               ObjectOutputStream outStream = null;
               try
                {
                 file = new FileOutputStream(filename);
                 outStream = new ObjectOutputStream(file);
                 outStream.writeObject(reservation1);
                 System.out.print(filename + " was written\n");
                }
               catch (NotSerializableException exception)
                {
                 System.out.print("The object is not serializable\n");
                 //System.out.print(exception+"\n");
                }
               catch (IOException exception)
                {
                 System.out.print("An I/O error has occurred\n");
                }
               finally
                {
                   if (outStream != null)
                     outStream.close();
                }
               break;
              case 'X':  //Deserialize Reservation from a File
               System.out.print("Please enter the file name to read:\n");
               filename = stdin.readLine().trim();
               FileInputStream file2;
               ObjectInputStream inStream = null;
               try
                {
                 file2 = new FileInputStream(filename);
                 inStream = new ObjectInputStream(file2);
                 reservation1 = (Reservation) inStream.readObject();
                 System.out.print(filename + " was read\n");
                }
               catch (ClassNotFoundException exception)
                {
                 System.out.print("The Class is not found\n");
                }
               catch (FileNotFoundException exception)
                {
                 System.out.print(filename + " was not found\n");
                }
               catch (Exception exception)
                {
                 System.out.print("An I/O error has occurred\n");
                }
               finally
                {
                 if (inStream != null)
                   inStream.close();
                }
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
     catch (IOException exception)
      {
        System.out.print("IO Exception\n");
      }
  }

  /** The method printMenu displays the menu to a user **/
  public static void printMenu()
   {
     System.out.print("Choice\t\tAction\n" +
                      "------\t\t------\n" +
                      "A\t\tAdd Passenger\n" +
                      "B\t\tAdd Flight\n" +
                      "D\t\tSearch for Passenger\n" +
                      "E\t\tSearch for Flight\n" +
                      "L\t\tList Passengers\n" +
                      "M\t\tList Flights\n" +
                      "O\t\tSort Passengers\n" +
                      "P\t\tSort Flights\n" +
                      "Q\t\tQuit\n" +
                      "R\t\tRemove Passenger\n" +
                      "S\t\tRemove Flight\n" +
                      "T\t\tClose Reservation\n" +
                      "U\t\tWrite Text to File\n" +
                      "V\t\tRead Text from File\n" +
                      "W\t\tSerialize Reservation to File\n" +
                      "X\t\tDeserialize Reservation from File\n" +
                      "?\t\tDisplay Help\n\n");
  }
}
