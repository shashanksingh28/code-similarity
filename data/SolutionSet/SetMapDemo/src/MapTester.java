import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
public class MapTester {

	public static void main(String[] args) {
		
		 Map<String, Color> favoriteColors = new HashMap<String, Color>();
		 
		 favoriteColors.put("Juliet", Color.pink);
		 favoriteColors.put("Romeo", Color.green);
		 favoriteColors.put("Sharon", Color.blue);
		 favoriteColors.put("Eve", Color.pink);
		 
		 Set<String> keySet = favoriteColors.keySet();
		
		 for (String key : keySet)
		 {
			 Color value = favoriteColors.get(key);
			 System.out.println(key + "->" + value);
		 }
		 
		 /*
		 System.out.println();
		 
		 //TreeMap
		 Map<String, Color> fColorTreMap = new TreeMap<String, Color>();
		 fColorTreMap.put("Juliet", Color.pink);
		 fColorTreMap.put("Romeo", Color.green);
		 fColorTreMap.put("Sharon", Color.blue);
		 fColorTreMap.put("Eve", Color.pink);
		 
		 Set<String> keySet2 = fColorTreMap.keySet();
		 System.out.println("sorted!");
		 for (String key : keySet2)
		 {
			 Color value = fColorTreMap.get(key);
			 System.out.println(key + "->" + value);
		 }
		 
		 */
	}

}
