
import java.util.*;

public class DistinctWordSet {

    public static void main(String[] args) {
        int count = 0;
        Set<String> words = new HashSet<>();
        Scanner in = new Scanner(System.in);
        String str;
        while (!(str = in.nextLine()).equals("")) {
            count++;
            words.add(str);
        }
        System.out.println(". . .");
        System.out.println("Total number of words entered: " + count);
        System.out.println("Distinct words: " + words.size());
        System.out.println(". . .");
        Iterator<String> iterator = words.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}