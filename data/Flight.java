// Assignment #: 8
//         Name: Soln
//    StudentID: 000-00-0000
//      Lecture: 
//  Description: The Flight class describes attributes in a flight
//               and provides accessor and mutator methods for each
//               instance variables as well as toString method.

public class Flight implements Comparable, java.io.Serializable  {
  private String airlines;
  private String flightNum;
  private CityTime departure;
  private CityTime arrival;

  /************************************************************************
   Constructor method to initialize each variable.
  ************************************************************************/
  public Flight()
   {
    airlines = new String("?");
    flightNum = new String("?");
    departure = new CityTime();
    arrival = new CityTime();
   }

  /************************************************************************
  Accessor method:
  This method returns the airlines name of a flight
  ************************************************************************/
  public String getAirlines()
   {
    return airlines;
   }

  /************************************************************************
  Accessor method:
  This method returns the flight number of a flight
  ************************************************************************/
  public String getFlightNum()
   {
    return flightNum;
   }

  /************************************************************************
  Accessor method:
  This method returns the departure schedule of a flight
  ************************************************************************/
  public CityTime getDeparture()
   {
    return departure;
   }

  /************************************************************************
  Accessor method:
  This method returns the arrival schedule of a flight
  ************************************************************************/
  public CityTime getArrival()
   {
    return arrival;
   }

  /************************************************************************
  Mutator method:
  This method sets the airlines name of a flight
  ************************************************************************/
  public void setAirlines(String aname)
   {
    airlines = aname;
   }

  /************************************************************************
  Mutator method:
  This method sets the flight number of a flight
  ************************************************************************/
  public void setFlightNum(String fNum)
   {
    flightNum = fNum;
   }

  /************************************************************************
  Mutator method:
  This method sets the departure schedule of a flight
  ************************************************************************/
  public void setDeparture(String city, String time, String date)
   {
    departure.setCity(city);
    departure.setDate(date);
    departure.setTime(time);
   }

  /************************************************************************
  Mutator method:
  This method sets the arrival schedule of a flight
  ************************************************************************/
  public void setArrival(String city, String time, String date)
   {
    arrival.setCity(city);
    arrival.setDate(date);
    arrival.setTime(time);
   }

  /************************************************************************
  This method returns a String containing attribute(variable) values
  of a flight.
  ************************************************************************/
  public String toString()
   {
    String result = "\nAirlines:\t" + airlines + "\n" +
            "Flight number:\t" + flightNum + "\n" +
            "Departure:\t" + departure + "\n" +
            "Arrival:\t" + arrival + "\n";

    result += "\n";

    return result;
   }

  /********************************************************************
  This method defines how two objects of Flight should be compared.
  If their airlines names are same, it compares their flight number.
  If their airlines names are different, their order is determined
  by their course names alphabetical order.
  If their flight numbers are same, it compares their departure schedule.
  If their departure schedules are same, it compare their arrival
  schedule.
  ********************************************************************/
  public int compareTo(Object other)
   {
    int result;

    if (airlines.compareTo(((Flight) other).airlines) == 0)
      if (flightNum.compareTo(((Flight) other).flightNum) == 0)
        {
         if (departure.compareTo(((Flight) other).departure) == 0)
          {
           result  = arrival.compareTo(((Flight) other).arrival);
          }
         else
          {
            result = departure.compareTo(((Flight) other).departure);
          }
        }
      else
        {
         result = flightNum.compareTo(((Flight) other).flightNum);
        }
    else
      result = airlines.compareTo(((Flight) other).airlines);

    return result;
   }


} //end of Flight class
