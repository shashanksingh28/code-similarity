// Assignment #: 8
//         Name: Soln
//    StudentID: 000-00-0000
//      Lecture: 2
//  Description: The CityTime class descrives a departure or an arrival
//               information of a flight including a city, time, and date.
//               It also provides their accessor, mutator methods,
//               and toString method.

public class CityTime implements Comparable, java.io.Serializable
{
	private String city;
	private String time;
	private String date;

 /************************************************************************
 Constructor method to initialize intance variables.
 ************************************************************************/
 public CityTime()
  {
   city = new String("?");
   time= new String("?");
   date = new String("?");
  }

 /************************************************************************
 Accessor method:
 This method returns a city of departure or arrival.
 ************************************************************************/
 public String getCity()
  {
   return city;
  }

 /************************************************************************
 Accessor method:
 This method returns time of departure or arrival.
 ************************************************************************/
 public String getTime()
  {
   return time;
  }

 /************************************************************************
 Accessor method:
 This method returns a date of departure or arrival.
 ************************************************************************/
 public String getDate()
  {
   return date;
  }

 /************************************************************************
  Modifier method:
  This method sets the city of departure or arrival.
 ************************************************************************/
 public void setCity(String place)
  {
   city = place;
  }

 /************************************************************************
  Modifier method:
  This method sets the time of departure or arrival.
 ************************************************************************/
 public void setTime(String tt)
  {
   time = tt;
  }

 /************************************************************************
  Modifier method:
  This method sets the date of departure or arrival.
 ************************************************************************/
 public void setDate(String dd)
  {
   date = dd;
  }

 /*****************************************************************************
 This method return a string containing the attribute information in  departure or arrival.
 *****************************************************************************/
 public String toString()
  {
   String result;

   result = city + "," + date + "," + time;

   return result;
  }

  /***************************************************************************
  This method defines how two objects of CityTime are compared.
  If their cities are same, it compares their date.
  If theri cities are different, their order is determined by the order
  of their cities' lexical order.
  If their dates are same, it compares time. You can use simple string
  comparison for this.
  ***************************************************************************/
  public int compareTo(Object other)
    {
      if (city.compareTo(((CityTime) other).city) == 0)
        {
         if (date.compareTo(((CityTime) other).date) == 0)
            return (time.compareTo(((CityTime) other).time));
         else
            return (date.compareTo(((CityTime) other).date));
        }
      else
        return (city.compareTo(((CityTime) other).city));
   }


} //end of CityTime class

