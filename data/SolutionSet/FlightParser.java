// Assignment #: 
//         Name: Soln
//    StudentID: 000-00-0000
//  Description: The FlightParser class contains a static
//               method parseStringToFlight that takes a string
//               containing a flight's information and
//               create a flight object from it.

public class FlightParser {

 public static Flight parseStringToFlight(String lineToParse)
  {
   String airlines = new String("?");
   String flightNum = new String("?");
   String temp3 = new String();
   String temp4 = new String();
   String row = new String();
   String column = new String();
   String city1 = new String("?");
   String city2 = new String("?");
   String time1 = new String("?");
   String time2 = new String("?");
   String date1 = new String("?");
   String date2 = new String("?");
   int rowNum = 1;
   int columnNum = 1;

     String[] tokens = lineToParse.split("/");

     //get the airlines
     if (tokens[0].length() > 0)
      {
       airlines = tokens[0].trim();
      }

     //get the flight number
     if (tokens[1].length() > 0)
      {
       flightNum = tokens[1].trim();
      }

     //get the departure information inluding city, time, and date
     if (tokens[2].length() > 0)
      {
       temp3 = tokens[2].trim();
       String[] tokens2 = temp3.split(",");

       if (tokens2[0].length() > 0)
         city1 = tokens2[0].trim();
       if (tokens2[1].length() > 0)
         time1 = tokens2[1].trim();
       if (tokens2.length == 3 && tokens2[2].length() > 0)
         date1 = tokens2[2].trim();

      }

     //get the arrival information including city, time, and date
     if (tokens.length == 4 && tokens[3].length() > 0)
      {
       temp4 = tokens[3].trim();
       String[] tokens3 = temp4.split(",");

       if (tokens3[0].length() > 0)
         city2 = tokens3[0].trim();
       if (tokens3[1].length() > 0)
         time2 = tokens3[1].trim();
       if (tokens3.length == 3 && tokens3[2].length() > 0)
         date2 = tokens3[2].trim();
      }

     //create a Flight object using extracted information.
     Flight flight = new Flight();
     flight.setAirlines(airlines);
     flight.setFlightNum(flightNum);
     flight.setDeparture(city1, time1, date1);
     flight.setArrival(city2, time2, date2);

     return flight;
  } //end of parseStringToFlight

} //end of FlightParser class