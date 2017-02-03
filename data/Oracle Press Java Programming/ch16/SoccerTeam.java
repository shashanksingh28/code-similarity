
import java.util.*;

public class SoccerTeam {

    public static void main(String[] args) {
        List<String> maleTeam = new LinkedList<>();
        maleTeam.add("John");
        maleTeam.add("Tom");
        maleTeam.add("Sam");
        maleTeam.add("Vijay");
        maleTeam.add("Anthony");
        System.out.println("Male Team: " + maleTeam);
        List<String> femaleTeam = new LinkedList<>();
        femaleTeam.add("Catherine");
        femaleTeam.add("Mary");
        femaleTeam.add("Shilpa");
        femaleTeam.add("Jane");
        femaleTeam.add("Anita");
        System.out.println("Female Team: " + femaleTeam);
        ListIterator<String> maleListIterator =
                maleTeam.listIterator();
        Iterator<String> femaleListIterator = femaleTeam.iterator();
        while (femaleListIterator.hasNext()) {
            if (maleListIterator.hasNext()) {
                maleListIterator.next();
            }
            maleListIterator.add(femaleListIterator.next());
        }
        System.out.println("Mixed Team: " + maleTeam);
        List<String> disqualify = new LinkedList<>();
        disqualify.add("Sam");
        disqualify.add("Tom");
        disqualify.add("Shilpa");
        maleTeam.removeAll(disqualify);
        System.out.println("Qualified Team: " + maleTeam);
    }
}