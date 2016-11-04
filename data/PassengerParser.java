// Assignment #: 8
//         Name: Soln
//    StudentID: 000-00-0000
//      Lecture: 
//  Description: This is a utility class that takes a string containing // a passenger's information and parse it to create a passenger object.

public class PassengerParser {

 public static Passenger parseStringToPassenger(String lineToParse)
  {
   String temp1 = new String();
   String temp2 = new String();
   String temp3 = new String();
   String temp4 = new String();
   String name = new String();

   Passenger passenger = new Passenger();

   try
    {
     String[] tokens = lineToParse.split(":");

     if (tokens[0].length() > 0)
      name = tokens[0];

     if (tokens[1].length() > 0)
      {
       temp3 = tokens[1];
       passenger.setPassengerID(temp3.trim());
      }

     if (tokens.length == 3 && tokens[2].length() > 0)
      {
       temp4 = tokens[2];
       passenger.setBookingNum( Integer.parseInt(temp4.trim()));
      }


     name=name.trim();

     String[] tokens2 = name.split(",");

     if (tokens2[0].length() > 0)
      {
       temp1 = tokens2[0];
       passenger.setLastName(temp1.trim());
      }

     if (tokens2.length == 2 && tokens2[1].length() > 0)
      {
       temp2 = tokens2[1];
       passenger.setFirstName(temp2.trim());
      }
     return passenger;
    }
   catch(NumberFormatException exception)
    {
     System.out.print("Invalid String\n");
     return passenger;
    }
  }

}

