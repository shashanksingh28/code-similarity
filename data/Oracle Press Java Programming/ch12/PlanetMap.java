
import java.util.*;

public class PlanetMap {

    public static void main(String args[]) {
        HashMap<Integer, String> mapOFPlanets =
                new HashMap<Integer, String>();
        mapOFPlanets.put(1, "Mercury");
        mapOFPlanets.put(2, "Venus");
        mapOFPlanets.put(3, "Earth");
        mapOFPlanets.put(4, "Mars");
        mapOFPlanets.put(5, "Jupiter");
        mapOFPlanets.put(6, "Saturn");
        mapOFPlanets.put(7, "Uranus");
        mapOFPlanets.put(8, "Neptune");
        System.out.println("Enter the desired position: ");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        System.out.printf("Solar system position %d is taken by %s%n",
                i, mapOFPlanets.get(i));
    }
}