
import java.util.*;

public class FamousQuotes {

    private static ArrayList listOfFamousQuotes;
    private static ArrayList<String> listOfFamousQuotesTypechecked;

    public static void main(String[] args) {
        FamousQuotes app = new FamousQuotes();
        System.out.println("Without using generics\n");
        app.buildList();
        app.printList();
        System.out.println();
        System.out.println("With generic classes\n");
        app.buildCheckedList();
        app.printCheckedList();
        System.out.println("\nNon-generics version of expurgate\n");
        String strAuthor = "Winston Churchill";
        System.out.println("After removing quotes by " + strAuthor);
        app.expurgate(listOfFamousQuotes, "Winston Churchill");
        app.printList();
        System.out.println("\nGenerics version of expurgate\n");
        System.out.println("After removing quotes by " + strAuthor);
        app.expurgateCheckedList(listOfFamousQuotesTypechecked, strAuthor);
        app.printCheckedList();
    }

    void buildList() {
        listOfFamousQuotes = new ArrayList();
        listOfFamousQuotes.add(
                "Where there is love there is life - Mahatma Gandhi");
        listOfFamousQuotes.add(
                "A joke is a very serious thing - Winston Churchill");
        listOfFamousQuotes.add(
                "In the end, everything is a gag - Charlie Chaplin");
// listOfFamousQuotes.add(100); // add this to generate runtime error
    }

    void buildCheckedList() {
        listOfFamousQuotesTypechecked = new ArrayList<String>();
        listOfFamousQuotesTypechecked.add(
                "Where there is love there is life - Mahatma Gandhi");
        listOfFamousQuotesTypechecked.add(
                "A joke is a very serious thing - Winston Churchill");
        listOfFamousQuotesTypechecked.add(
                "In the end, everything is a gag - Charlie Chaplin");
    }

    void printList() {
        Iterator listIterator = listOfFamousQuotes.iterator();
        while (listIterator.hasNext()) {
            String quote = (String) listIterator.next();
            System.out.println(quote);
        }
    }

    void printCheckedList() {
        Iterator<String> quoteIterator =
                listOfFamousQuotesTypechecked.iterator();
        while (quoteIterator.hasNext()) {
            String quote = quoteIterator.next();
            System.out.println(quote);
        }
    }

    void expurgate(Collection c, String strAuthor) {
        for (Iterator i = c.iterator(); i.hasNext();) {
            if (((String) i.next()).contains(strAuthor)) {
                i.remove();
            }
        }
    }

    void expurgateCheckedList(Collection<String> c, String strAuthor) {
        for (Iterator<String> i = c.iterator(); i.hasNext();) {
            if (i.next().contains(strAuthor)) {
                i.remove();
            }
        }
    }
}