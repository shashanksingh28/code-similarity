
import java.util.*;

public class SortableTeam {

    public static void main(String[] args) {
        SortedSet<Player> ageSortedTeam = new TreeSet<>();
        ageSortedTeam.add(new Player("John", 21));
        ageSortedTeam.add(new Player("Sam", 20));
        ageSortedTeam.add(new Player("Anthony", 18));
        ageSortedTeam.add(new Player("Bill", 19));
        ageSortedTeam.add(new Player("Jack", 22));
        System.out.println("Team - by age");
        printSet(ageSortedTeam);
        System.out.println("-------------------");
        SortedSet<Player> nameSortedTeam =
                new TreeSet<>(new Comparator<Player>() {

            public int compare(Player a, Player b) {
                return a.getName().compareTo(b.getName());
            }
        });
        nameSortedTeam.addAll(ageSortedTeam);
        System.out.println("Team - alphabetical");
        printSet(nameSortedTeam);
        System.out.println("-------------------");
    }

    static void printSet(Set set) {
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Player player = (Player) iterator.next();
            System.out.println(player.getName() + " - Age:"
                    + player.getAge());
        }
    }

    private static class Player implements Comparable<Player> {

        private String name;
        private int age;

        public Player(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return this.name;
        }

        public int compareTo(Player other) {
            return age - other.age;
        }
    }
}