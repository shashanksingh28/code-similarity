import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.json.*;

// Download https://java.net/projects/jsonp/downloads
// Compile with java -cp .:path/to/javax.json-ri-1.0/lib/javax.json-1.0.jar Freebase.java
// Run with java -cp  .:path/to/javax.json-ri-1.0/lib/javax.json-1.0.jar Freebase `cat apikey`

public class Freebase 
{
   public static void main(String[] args) throws Exception
   {
      String apiKey = args[0];
      String urlString = "https://www.googleapis.com/freebase/v1/mqlread";
      String query = "[{"
         + "  \"type\": \"/film/film\","
         + "  \"name\": null,"
         + "  \"initial_release_date\": null,"
         + "  \"directed_by\": [],"
         + "  \"produced_by\": [],"
         + "  \"starring\": [{"
         + "    \"actor\": []"
         + "  }],"
         + "  \"country\": [{"
         + "    \"id\": \"/en/united_states\""
         + "  }],"
         + "  \"genre\": [{"
         + "    \"id\": \"/en/drama\""
         + "  }]"
         + "}]";
      String cursor = null;
      boolean done = false;
      while (!done)
      {
         String request = "?query=" + URLEncoder.encode(query, "UTF-8") + "&key=" + apiKey + "&cursor" + (cursor == null ? "" : "=" + URLEncoder.encode(cursor, "UTF-8"));
         URL url = new URL(urlString + request);
         try (InputStream in = url.openStream()) 
         {
            JsonReader reader = Json.createReader(in);
            JsonObject contents = reader.readObject();
            JsonArray result = contents.getJsonArray("result");
            for (int k = 0; k < result.size(); k++)
            {
               printFilm(result.getJsonObject(k));
            }
            done = result.size() < 100;
            if (!done) { cursor = contents.getString("cursor"); }
         }
      }
   }
   
   public static StringBuilder format(String title, List<JsonValue> array) 
   {
      StringBuilder result = new StringBuilder();
      result.append(title);
      result.append(": ");
      for (int i = 0; i < array.size(); i++) 
      {
         if (i > 0) { result.append(", "); }
         result.append(array.get(i).toString().replace(",", " "));
      }
      return result;
   }

   public static void printFilm(JsonObject film)
   {
      System.out.println("Name: " + film.getString("name"));
      System.out.println("Year: " + film.get("initial_release_date").toString().replace("\"", "").substring(0, 4));      
      System.out.println(format("Directed by", film.getJsonArray("directed_by")));
      System.out.println(format("Produced by", film.getJsonArray("produced_by")));
      List<JsonValue> actors = new ArrayList<>();
      JsonArray jactors = film.getJsonArray("starring");
      for (int i = 0; i < jactors.size(); i++)
      {
         actors.addAll(jactors.getJsonObject(i).getJsonArray("actor"));
      }
      System.out.println(format("Actors", actors));
   }      
}
