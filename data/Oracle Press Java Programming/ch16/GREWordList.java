
import java.util.*;

public class GREWordList {

    static Map<String, Word> wordList = new HashMap<>();

    public static void main(String[] args) {
        wordList.put("Abate", new Word("verb", "subside",
                "alienate,increase,extend,amplify,continue,enlarge"));
        wordList.put("Abeyance", new Word("noun",
                "suspended action", "continuance"));
        wordList.put("Abscond", new Word("verb",
                "depart secretly and hide",
                "appear,emerge, show,stay,remain"));
        wordList.put("Abstemious", new Word("adj",
                "sparing in eating and drinking",
                "intemperate,glutonous"));
        wordList.put("Admonish", new Word("verb", "warn, reprove",
                "acclaim,commend,praise,compliment,countenance"));
        printList();
// Look up a value
        System.out.println("\nValue for abscond " + wordList.get("Abscond"));
// Modify an entry
        wordList.put("Abate", new Word("verb", "subside,moderate",
                "alienate,increase,extend,amplify,continue,enlarge"));
// Remove entry
        wordList.remove("Abstemious");
        System.out.print("\nAfter modifications:");
        printList();
    }

    static private void printList() {
        System.out.println("\nAll Entries:");
        for (Map.Entry<String, Word> entry : wordList.entrySet()) {
            String key = entry.getKey();
            Word value = entry.getValue();
            System.out.println("key=" + key + ", value=" + value);
        }
    }

    private static class Word {

        private String type;
        private String synonym;
        private String antonym;

        public String getAntonym() {
            return antonym;
        }

        public String getSynonym() {
            return synonym;
        }

        public String getType() {
            return type;
        }

        public Word(String type, String synonym, String antonym) {
            this.type = type;
            this.synonym = synonym;
            this.antonym = antonym;
        }

        @Override
        public String toString() {
            return "[" + type + "; " + synonym + "; " + antonym + "]";
        }
    }
}