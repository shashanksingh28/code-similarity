
public class DynamicOddsGenerator {

    private final static int SIZE = 25;
    private int[] arrayOfInts = new int[SIZE];

    public DynamicOddsGenerator() {
        for (int i = 0; i < SIZE; i++) {
            arrayOfInts[i] = (int) (Math.random() * SIZE);
        }
    }

    public void printOdds() {
        InnerOddsIterator iterator = this.new InnerOddsIterator();
        while (iterator.hasNext()) {
            int returnValue = iterator.getNext();
            if (returnValue != -1) {
                System.out.print(returnValue + " ");
            }
        }
        System.out.println();
    }
//inner class implements the Iterator pattern
    private class InnerOddsIterator {

        private int next = 0;

        public boolean hasNext() {
            return (next <= SIZE - 1);
        }

        public int getNext() {
            int retValue = arrayOfInts[next++];
            if (retValue % 2 == 1) {
                return retValue;
            }
            return -1;
        }
    }

    public static void main(String s[]) {
        DynamicOddsGenerator numbers = new DynamicOddsGenerator();
        numbers.printOdds();
    }
}