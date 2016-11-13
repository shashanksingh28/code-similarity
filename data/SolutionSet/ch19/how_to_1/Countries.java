import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class Countries
{
   public static void main(String[] args) throws IOException
   {
      try (Stream<String> lines = Files.lines(Paths.get("../population.txt"))) {
         Stream<Country> countries = 
            lines.map(line -> Country.parse(line));
         List<Country> countryList = countries.collect(Collectors.toList());

         Stream<Country> africanCountries = countryList.stream()
            .filter(c -> c.getContinent().equals("Africa"));

         System.out.println("African countries: "
            + africanCountries.collect(Collectors.toList()));
      
         Stream<String> continents = countryList.stream()
            .map(c -> c.getContinent())
            .distinct();
      
         System.out.println("Continents: "
            + continents.collect(Collectors.toList()));
      
         Stream<String> continentsWithPopulousCountries = countryList.stream()
            .filter(c -> c.getPopulation() >= 100_000_000)
            .map(c -> c.getContinent())
            .distinct();

         System.out.println("Continents with populous countries: "
            + continentsWithPopulousCountries.collect(Collectors.toList()));

         Optional<Country> aPopulousCountry = countryList.stream()
            .filter(country -> country.getPopulation() > 100_000_000)
            .findAny();
      
         aPopulousCountry.ifPresent(
            c -> System.out.println("A populous country: " + c));
      
         List<Country> mostPopulous = countryList.stream()
            .sorted((c, d) -> Double.compare(d.getPopulation(), c.getPopulation()))
            .limit(10)
            .collect(Collectors.toList());

         System.out.println("Ten most populous countries: "
            + mostPopulous);

         double average = countryList.stream()
            .mapToInt(country -> country.getPopulation())
            .average()
            .orElse(0);

         System.out.println("Average population of all countries: "
            + average);

         Map<String, List<Country>> countriesByContinent = countryList.stream()
            .collect(
               Collectors.groupingBy(c -> c.getContinent()));

         System.out.println("Countries by continent: "
            + countriesByContinent);

         Map<String, Double> averagePopulationByContinent = countryList.stream()
            .collect(
               Collectors.groupingBy(
                  c -> c.getContinent(),
                  Collectors.averagingInt(c -> c.getPopulation())));

         System.out.println("Average population by continent: "
            + averagePopulationByContinent);
      }
   }
}

      
