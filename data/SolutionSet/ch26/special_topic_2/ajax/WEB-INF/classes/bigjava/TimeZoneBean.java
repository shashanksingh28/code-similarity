package bigjava;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
   This bean formats the local time of day for a given city.
*/
@ManagedBean
@SessionScoped
public class TimeZoneBean
{
   private DateFormat timeFormatter;
   private ArrayList<String> cities;
   private String cityToAdd;
   private String cityToRemove;

   /**
      Initializes the formatter.
   */
   public TimeZoneBean()
   {
      timeFormatter = DateFormat.getTimeInstance();
      cities = new ArrayList<>();
   }

   /**
      Setter for cityToAdd property.
      @param city the city to add to the list of cities
   */
   public void setCityToAdd(String city)
   {      
      cityToAdd = city;
   }
   
   /**
      Getter for cityToAdd property.
      @return the city to add to the list of cities
   */
   public String getCityToAdd()
   {
      return cityToAdd;
   }
   
   /**
      Setter for the cityToRemove property.
      @param city the city to remove from the list of cities
   */
   public void setCityToRemove(String city) 
   {      
      cityToRemove = city;
   }

   /**
      Getter for the cityToRemove property.
      @return the city to remove from the list of cities
   */
   public String getCityToRemove() 
   {      
      return cityToRemove;
   }
   
   /**
      Read-only citiesAndTimes property.
      @return a map containing the cities and formatted times
   */
   public Map<String, String> getCitiesAndTimes()
   {
      Date time = new Date();
      Map<String, String> result = new TreeMap<>();
      for (int i = 0; i < cities.size(); i++)
      {
         String city = cities.get(i);
         String label = city + ": ";
         TimeZone zone = getTimeZone(city);
         if (zone != null)
         {
            timeFormatter.setTimeZone(zone);
            String timeString = timeFormatter.format(time);
            label = label + timeString;            
         }
         else 
         {
            label = label + "unavailable";            
         }
         result.put(label, city);
      }

      return result;
   }

   /**
      Action for adding a city.
      @return null
   */
   public String addCity()
   {
      TimeZone zone = getTimeZone(cityToAdd);      
      if (zone == null) { return null; }
      cities.add(cityToAdd);
      cityToRemove = cityToAdd;
      cityToAdd = "";
      return null;
   }

   /**
      Action for removing a city.
      @return null
   */
   public String removeCity()
   {
      cities.remove(cityToRemove);
      return null;
   }

   /**
      Looks up the time zone for a city.
      @param aCity the city for which to find the time zone
      @return the time zone or null if no match is found
   */
   private static TimeZone getTimeZone(String aCity)
   {
      String[] ids = TimeZone.getAvailableIDs();
      for (int i = 0; i < ids.length; i++)
      {
         if (timeZoneIDmatch(ids[i], aCity))
         {
            return TimeZone.getTimeZone(ids[i]);
         }
      }
      return null;
   }

   /**
      Checks whether a time zone ID matches a city.
      @param id the time zone ID (e.g. "America/Los_Angeles")
      @param aCity the city to match (e.g. "Los Angeles")
      @return true if the ID and city match
   */
   private static boolean timeZoneIDmatch(String id, String aCity)
   {
      String idCity = id.substring(id.indexOf('/') + 1);
      return idCity.replace('_', ' ').equals(aCity);
   }
}
